package com.myapps.playnation.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.MainActivity;

public class LoginActivity extends Activity {
	private ProgressDialog progressDialog;
	private int progressbarStatus = 0;
	public LoadViewTask task;
	DataConnector con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});

		Button logButton = (Button) findViewById(R.id.btnLogin);
		logButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (checkCredentials())
					task = new LoadViewTask();
				task.execute();
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_login);
	}

	class LoadViewTask extends AsyncTask<Void, Integer, Void> {

		String tableName;
		int stateId;
		Intent mInt;

		private LoadViewTask() {
			con = DataConnector.getInst(getApplicationContext());
		}

		// Before running code in separate thread
		@Override
		protected void onPreExecute() {
			// Create a new progress dialog
			progressDialog = new ProgressDialog(LoginActivity.this);
			// progressDialog.setMax(100);
			progressDialog.setCancelable(true);
			progressDialog.setMessage("Downloading Data... Please wait");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// progressDialog.setProgress(0);
			progressDialog.show();
			// ProgressBar.show(LoginActivity.this,
			// "Loading...", "Please wait...", false, false);
		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			// Get the current thread's token
			synchronized (this) {
				progressbarStatus += 0;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.gamesTable)) {
					con.getArrayFromQuerryWithPostVariable("", Keys.gamesTable,
							"");
				}
				progressbarStatus += 40;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.companyTable)) {
					con.getArrayFromQuerryWithPostVariable("",
							Keys.companyTable, "");
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.groupsTable)) {
					con.getArrayFromQuerryWithPostVariable("",
							Keys.groupsTable, "");
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.newsTable)) {
					con.getArrayFromQuerryWithPostVariable("", Keys.newsTable,
							"");
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.companyTable)) {
					con.getArrayFromQuerryWithPostVariable("",
							Keys.companyTable, "");
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				mInt = new Intent(getApplicationContext(), MainActivity.class);
			}
			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {
			// set the current progress of the progress dialog
			// progressDialog.setProgress(values[0]);
		}

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			// close the progress dialog
			if (progressDialog != null)
				progressDialog.dismiss();
			proceed(mInt);
		}
	}

	public void proceed(Intent mInt) {
		startActivity(mInt);
		finish();
	}

	public boolean checkCredentials() {
		return true;
	}
}