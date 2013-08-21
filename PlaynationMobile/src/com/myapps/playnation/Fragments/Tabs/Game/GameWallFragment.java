package com.myapps.playnation.Fragments.Tabs.Game;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.UserComment;
import com.myapps.playnation.Fragments.WallFragment;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Workers.LoadCommentsTask;

public class GameWallFragment extends Fragment implements WallFragment {
	private DataConnector con;
	private EditText commentText;
	private CommExpListAdapter expAdapter;
	private ExpandableListView expList;
	private View footer;
	private ArrayList<UserComment> mListData;
	private AsyncTask mCommentsTask;

	@SuppressLint("ResourceAsColor")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		footer = inflater.inflate(R.layout.component_comment_footer, null);
		expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);
		mListData = new ArrayList<UserComment>();
		commentText = (EditText) footer.findViewById(R.id.wallsF_comment_EBox);
		Button commentBut = (Button) footer.findViewById(R.id.wallsF_commBut);
		commentBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				con.insertComment(commentText.getText().toString(), "game",
						getArguments().getString(Keys.GAMENAME), getArguments()
								.getString(Keys.ID_GAME));

				String playerName = con.getCurrentPlayer().getString(
						Keys.PLAYERNAME);
				String time = DateFormat.getDateTimeInstance().format(
						new Date());
				/*
				 * mListData.add(new UserComment(new CommentInfo(playerName,
				 * commentText.getText().toString(), time), new
				 * ArrayList<CommentInfo>()));
				 * expAdapter.notifyDataSetChanged();
				 */
				Log.i("Games Wall", "Comment Button Pressed"
						+ commentText.getText().toString());
			}
		});
		mCommentsTask = new LoadCommentsTask(this, getArguments().getString(
				Keys.ID_GAME), "game").execute();
		// Inflate the layout for this fragment
		return mView;
	}

	@Override
	public void initComments(ArrayList<UserComment> comments) {
		expList.addFooterView(footer);
		mListData = comments;
		expAdapter = new CommExpListAdapter(getActivity(), mListData);
		if (expAdapter.isEmpty()) {
			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);
			expList.addHeaderView(msgText);

		}
		expList.setAdapter(expAdapter);

	}

	@Override
	public void onDestroy() {
		mCommentsTask.cancel(true);
		super.onDestroy();
	}

	@Override
	public Context getContext() {
		return getActivity();
	}
}
