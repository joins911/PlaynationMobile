package com.myapps.playnation.Workers;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.myapps.playnation.Classes.UserComment;
import com.myapps.playnation.Fragments.WallFragment;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

/*
 * In progress... 
 */
public class LoadCommentsTask extends AsyncTask<Void, Void, Void> {

	private ArrayList<UserComment> comments;
	private ISectionAdapter mActConn;
	private String playerId;
	private String wallType;
	private WallFragment mFrag;

	public LoadCommentsTask(WallFragment mFrag, String playerID, String section) {
		mActConn = (ISectionAdapter) mFrag.getContext();
		this.playerId = playerID;
		wallType = section;
		this.mFrag = mFrag;
	}

	@Override
	protected void onPreExecute() {
		mActConn.setIndeterminateVisibility(true);
	}

	@Override
	protected Void doInBackground(Void... params) {
		comments = DataConnector.getInst().getComments(playerId, wallType);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		mActConn.setIndeterminateVisibility(false);
		mFrag.initComments(comments);

	}

}
