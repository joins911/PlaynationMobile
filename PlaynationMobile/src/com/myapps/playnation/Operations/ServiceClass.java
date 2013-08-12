package com.myapps.playnation.Operations;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.LastIDs;

public class ServiceClass extends Service {
	private DataConnector con;
	private static final String tag = "ServiceClass";
	Handler mHandler;
	private Runnable mHandlerTask = new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(120);
				System.out.println("Start 1");
				con.getArrayFromQuerryWithPostVariable("", Keys.gamesTable, "",
						LastIDs.getLastIDCompanies());
				Thread.sleep(60);
				System.out.println("Start 2");
				con.getArrayFromQuerryWithPostVariable("", Keys.companyTable,
						"", LastIDs.getLastIDCompanies());
				Thread.sleep(60);
				System.out.println("Start 3");
				con.getArrayFromQuerryWithPostVariable("", Keys.groupsTable,
						"", LastIDs.getLastIDCompanies());
				Thread.sleep(60);
				System.out.println("Start 4");
				con.getArrayFromQuerryWithPostVariable("", Keys.newsTable, "",
						LastIDs.getLastIDCompanies());
				mHandler.postDelayed(mHandlerTask, 120);
			} catch (Exception e) {
			}

		}
	};;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		con = DataConnector.getInst(this);
		Log.i(tag, "Intent Created");
		Toast.makeText(this, "Intent Created", Toast.LENGTH_LONG).show();
		super.onCreate();

	}

	void startRepeatingTask() {
		mHandlerTask.run();
	}

	void stopRepeatingTask() {
		mHandler.removeCallbacks(mHandlerTask);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Intent Dest", Toast.LENGTH_LONG).show();
		Log.i(tag, "Intent Destro");
		stopRepeatingTask();
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Log.i(tag, "Intent Started");
		Toast.makeText(this, "Intent Started", Toast.LENGTH_LONG).show();
		startRepeatingTask();
	}

}
