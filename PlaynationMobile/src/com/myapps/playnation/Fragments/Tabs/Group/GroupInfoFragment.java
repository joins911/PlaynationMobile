package com.myapps.playnation.Fragments.Tabs.Group;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class GroupInfoFragment extends Fragment {
	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;
	private View mView;

	private void initGroup() {
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
