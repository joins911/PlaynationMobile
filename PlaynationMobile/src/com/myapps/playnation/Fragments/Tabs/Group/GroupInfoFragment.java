package com.myapps.playnation.Fragments.Tabs.Group;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.DialogSendCommentFragment;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.MainActivity;

public class GroupInfoFragment extends Fragment {
	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;
	private View mView;
	private DataConnector con;

	private void initGroup() {
		con = DataConnector.getInst();
		TextView txtNewsLeader = (TextView) mView
				.findViewById(R.id.txtgameinfoType);
		TextView txtNewsType1 = (TextView) mView
				.findViewById(R.id.txtgameinfoPlatforms);
		TextView txtNewsType2 = (TextView) mView
				.findViewById(R.id.txtgameinfoPlayers);
		TextView txtNewsMembers = (TextView) mView
				.findViewById(R.id.txtgameinfoReleased);
		TextView txtNewsCreated = (TextView) mView
				.findViewById(R.id.newsGroupCreated);
		Bundle args = getArguments();
		txtNewsTitle = (WebView) mView.findViewById(R.id.webview);
		txtNewsText = (WebView) mView.findViewById(R.id.webview2);
		setupWebView(txtNewsText);
		txtNewsTitle.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		txtNewsTitle
				.loadData(args.getString(Keys.GROUPNAME), "text/html", null);
		// txtNewsText.setText(args.getString(Keys.NEWSCOLNEWSTEXT));
		// txtNewsText.setFocusable(true);
		txtNewsText.loadData(args.getString(Keys.GROUPDESC), "text/html", null);

		// newsImage.setImageResource(args.getString(Keys.NEWSCOLIMAGE));

		txtNewsType1.setText(args.getString(Keys.GROUPTYPE));
		txtNewsType2.setText(args.getString(Keys.GROUPTYPE2));
		txtNewsMembers.setText(args.getString(Keys.GroupMemberCount)
				+ getResources().getString(R.string.Members));
		String ID_CREATOR;

		ID_CREATOR = args.getString(Keys.GruopCreatorName);
		txtNewsLeader.setText(ID_CREATOR);
		mView.setFocusable(false);
		txtNewsCreated.setText(args.getString(Keys.GROUPDATE));

		final String id_groupcreator = args.getString(Keys.ID_GROUP);
		TextView tx = (TextView) mView.findViewById(R.id.btnAddGroup);

		String isLiked = args.getString(Keys.GameIsLiked);
		String isMember = args.getString(Keys.isMember);
		if (isMember.equals("1"))
			tx.setVisibility(View.GONE);

		tx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment dialog = new DialogSendCommentFragment();
				dialog.show(getChildFragmentManager(), "Group");
				Bundle argss = new Bundle();
				argss.putString(Keys.ID_PLAYER, Configurations.CurrentPlayerID);
				argss.putString(Keys.functionAnotherID, id_groupcreator);
				argss.putString(Keys.functionAction,
						Keys.POSTFUNCOMMANTSendPerson);
				argss.putString(Keys.functionPhpName, "groupFunction.php");
				dialog.setArguments(argss);
			}
		});

		TextView txlike = (TextView) mView.findViewById(R.id.btnGroupLike);
		if (isLiked.equals("1")) {
			txlike.setText(getActivity().getResources().getString(
					R.string.btnUnlike));
		} else {
			txlike.setText(getActivity().getResources().getString(
					R.string.btnLike));
		}

		txlike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				con.functionQuery(Configurations.CurrentPlayerID, id_groupcreator,
						"groupFunction.php", Keys.POSTFUNCOMMANTLike, "");
			}
		});
		if (MainActivity.configs.getApplicationState() != 0) {
			tx.setVisibility(View.GONE);
			txlike.setVisibility(View.GONE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mView = inflater
				.inflate(R.layout.fragment_group_info, container, false);
		initGroup();
		return mView;
	}

	public void setupWebView(WebView mView) {
		mView.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		mView.getSettings().setLoadWithOverviewMode(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
