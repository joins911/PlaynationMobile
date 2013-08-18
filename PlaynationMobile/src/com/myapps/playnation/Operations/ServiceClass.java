package com.myapps.playnation.Operations;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
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
			try {
				con.getArrayFromQuerryWithPostVariable("", Keys.gamesTable, "",
						con.getLastIDGames());
				// Thread.sleep(60);
				con.getArrayFromQuerryWithPostVariable("", Keys.companyTable,
						"", con.getLastIDCompanies());
				// Thread.sleep(60);
				con.getArrayFromQuerryWithPostVariable("", Keys.groupsTable,
						"", con.getLastIDGroups());
				// Thread.sleep(60);

				con.getArrayFromQuerryWithPostVariable("", Keys.newsTable, "",
						con.getLastIDNews());
			} catch (Exception e) {
				Log.e("Service", "Error Service Timer" + e.toString());
			}
		}
	};

	static int MINUTES = 1;

	private void showNotification() {
		stopTimer();

		timer = new Timer();
		timer.scheduleAtFixedRate(serviceStarterTask, MINUTES * 60 * 1000,
				MINUTES * 60 * 1000);
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
