package com.myapps.playnation.Operations;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import com.myapps.playnation.Classes.Keys;

public class ServiceClass extends Service {
	private DataConnector con;
	private Timer timer;

	// private static final String tag = "ServiceClass";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		con = DataConnector.getInst(this);
		super.onCreate();
	}

	TimerTask serviceStarterTask = new TimerTask() {
		@Override
		public void run() {
			Keys.internetStatus = HelperClass
					.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
			try {
				if (con.getMinGameID() == con.getLastIDGames()) {
					con.setLastIDGames(0);
				}
				if (con.getMinGroupID() == con.getLastIDGroups()) {
					con.setLastIDGroups(0);
				}
				if (con.getMinCompanyID() == con.getLastIDCompanies()) {
					con.setLastIDCompanies(0);
				}

				con.getArrayFromQuerryWithPostVariable("", Keys.gamesTable, "",
						con.getLastIDGames());

				con.getArrayFromQuerryWithPostVariable("", Keys.companyTable,
						"", con.getLastIDCompanies());

				con.getArrayFromQuerryWithPostVariable("", Keys.groupsTable,
						"", con.getLastIDGroups());

				if (con.getLastIDNews() == con.getMinNewsID()) {
					con.setLastIDNews(0);
					con.getArrayFromQuerryWithPostVariable("", Keys.newsTable,
							"", con.getLastIDNews());
				} else {
					if (con.getLastIDNews() > 0) {
						con.getArrayFromQuerryWithPostVariable("",
								Keys.newsServiceTable, "", con.getLastIDNews());
					}
				}
			} catch (Exception e) {
				Log.e("Service", "Error Service Timer" + e.toString());
			}
		}
	};

	static int MINUTES = 3;

	private void showNotification() {
		stopTimer();
		if (Keys.internetStatus) {
			timer = new Timer();
			timer.scheduleAtFixedRate(serviceStarterTask, MINUTES * 60 * 1000,
					MINUTES * 60 * 1000);
		}
	}

	private void stopTimer() {
		if (timer != null)
			timer.cancel();
	}

	@Override
	public void onDestroy() {
		stopTimer();
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		showNotification();
	}

}
