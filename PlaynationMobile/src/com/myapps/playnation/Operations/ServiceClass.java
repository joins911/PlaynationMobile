package com.myapps.playnation.Operations;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.NotificationActivity;
import com.myapps.playnation.main.MainActivity;

public class ServiceClass extends Service {
	private DataConnector con;
	private Timer timer;
	private ArrayList<Bundle> playersNotificationList;

	// private static final String tag = "ServiceClass";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		con = DataConnector.getInst(this);
		playersNotificationList = con
				.queryNotification(Configurations.CurrentPlayerID);
		super.onCreate();
	}

	TimerTask serviceStarterTask = new TimerTask() {
		@Override
		public void run() {
			if (MainActivity.configs.getApplicationState() == 0)
				createNotification();

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

	public void createNotification() {

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, NotificationActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(NotificationActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		if (playersNotificationList != null)
			for (Bundle bund : playersNotificationList) {
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						this).setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle(bund.getString(Keys.NotificationType))
						.setDefaults(Notification.DEFAULT_ALL)
						.setContentIntent(resultPendingIntent)
						.setContentText(bund.getString(Keys.PLAYERNICKNAME))
						.setAutoCancel(true);
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(
						Integer.parseInt(bund.getString(Keys.ID_NOTIFICATION)),
						mBuilder.build());
			}
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
