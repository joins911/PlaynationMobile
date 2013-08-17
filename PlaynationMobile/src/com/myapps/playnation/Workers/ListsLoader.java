package com.myapps.playnation.Workers;

import android.os.AsyncTask;
import android.widget.ListView;

/*
 * In progress... 
 */
public class ListsLoader extends AsyncTask<Void, Void, Void> {

	ListView mList;

	public ListsLoader(ListView list) {
		mList = list;
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// mList.setAdapter(adapter);

	}

}
