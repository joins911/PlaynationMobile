package com.myapps.playnation.Fragments.Tabs.Players;

import java.util.ArrayList;

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
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.Workers.LoadCommentsTask;
import com.myapps.playnation.main.ISectionAdapter;

public class PlayerWallFragment extends Fragment implements WallFragment {
	DataConnector con;
	EditText commentText;
	ISectionAdapter mActConn;
	private CommExpListAdapter expAdapter;
	private ExpandableListView expList;
	private View footer;
	private AsyncTask mCommentsTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		mActConn = (ISectionAdapter) getActivity();
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);

		footer = inflater.inflate(R.layout.component_comment_footer, null);
		Button commentBut = (Button) footer.findViewById(R.id.wallsF_commBut);
		commentText = (EditText) footer.findViewById(R.id.wallsF_comment_EBox);

		commentBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				con.insertComment(commentText.getText().toString(), "player",
						getArguments().getString(Keys.PLAYERNAME),
						getArguments().getString(Keys.ID_PLAYER));
				expAdapter.notifyDataSetChanged();
				Log.i("Games Wall", "Comment Button Pressed"
						+ commentText.getText().toString());
			}
		});
		expList.addFooterView(footer);

		HelperClass.disableAddComments(footer, commentText, commentBut);

		mCommentsTask = new LoadCommentsTask(this, getArguments().getString(
				Keys.ID_PLAYER), "player").execute();
		// Inflate the layout for this fragment
		return mView;
	}

	@Override
	public void onDestroy() {
		mCommentsTask.cancel(true);
		super.onDestroy();
	}

	/*
	 * public void initList(ArrayList<UserComment> comments) { expAdapter = new
	 * CommExpListAdapter(getActivity(), comments); if (expAdapter.isEmpty()) {
	 * TextView msgText = new TextView(getActivity());
	 * msgText.setText(R.string.emptyListString);
	 * msgText.setTextColor(Color.parseColor("#CFCFCF"));
	 * msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
	 * msgText.setGravity(Gravity.CENTER_HORIZONTAL);
	 * expList.addHeaderView(msgText); } expList.setAdapter(expAdapter); }
	 */

	@Override
	public Context getContext() {
		return getActivity();
	}

	@Override
	public void initComments(ArrayList<UserComment> comments) {
		expAdapter = new CommExpListAdapter(getActivity(), comments);
		if (expAdapter.isEmpty()) {
			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);
			expList.addHeaderView(msgText);
		}
		footer.setVisibility(View.VISIBLE);
		expList.setAdapter(expAdapter);

	}

}
