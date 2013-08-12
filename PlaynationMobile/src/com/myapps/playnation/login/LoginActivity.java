package com.myapps.playnation.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.LastIDs;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.Operations.ServiceClass;
import com.myapps.playnation.main.MainActivity;

public class LoginActivity extends Activity {
	private ProgressDialog progressDialog;
	private int progressbarStatus = 0;
	public LoadViewTask task;
	private EditText username;
	private EditText password;
	private Button logButton;
	DataConnector con;
	private SharedPreferences prefrence;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		startService(new Intent(this, ServiceClass.class));
		if (android.os.Build.VERSION.SDK_INT > 10) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
<<<<<<< HEAD
		con = DataConnector.getInst(getApplicationContext());
||||||| merged common ancestors
=======

>>>>>>> 7330953f3993167eef4a808f834ebc3e050ee16a
		// UserLoginPreferences
		prefrence = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		Log.i("ActiveSession",
				"" + prefrence.getBoolean(Keys.ActiveSession, false));

		// Setting the activesession to be true so that it wont wait for button
		// press on login
		if (!Configurations.isLoginEnabled) {
			SharedPreferences.Editor edit = prefrence.edit();
			edit.putBoolean(Keys.ActiveSession, true);
			// edit.clear();
			edit.commit();
		}
		// To unset sharepref. comment the activesession line and put

		if (prefrence.getBoolean(Keys.ActiveSession, false) == true) {
			if (checkServerStatus())
				logOnlineUser();
			else
				logOfflineUser();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.password_logIn);
		password = (EditText) findViewById(R.id.username_logIn);
		logButton = (Button) findViewById(R.id.btnLogin);
		Button logGuestButton = (Button) findViewById(R.id.btnGuestLogin);
		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

		logButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				logButton.setError(null);
				if (HelperClass.EmailPassNickCheck(username, password, null)) {
					if (checkServerStatus())
						if (checkCredentials()) {
							logOnlineUser();
						} else {
							logButton
									.setError("Incorrect UserName or Password!");
							Toast.makeText(getApplicationContext(),
									"Incorrect Username or Password",
									Toast.LENGTH_LONG).show();
						}
					else {
						logOfflineUser();
					}
				}
			}
		});

		logGuestButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				logOnlineUser();
			}
		});

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				if (con.checkConnection()) {
					Intent i = new Intent(getApplicationContext(),
							RegisterActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Server can not be reached", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		if (!isNetworkAvailable()) {
			Toast.makeText(
					getApplicationContext(),
					"There is no internet connection available. Offline Mode(Ignore login).",
					Toast.LENGTH_SHORT).show();
			logButton.setText(getResources().getString(
					R.string.loginOfflineString));
			registerScreen.setText(getResources().getString(
					R.string.registerOfflineDesc));
			logGuestButton.setVisibility(View.GONE);
		}

	}

	private void logOnlineUser() {
		// Login as User XXX
		startMainActivity(Configurations.appStateOnUser);
	}

	private void logOfflineUser() {
		// Log Offline WIthout Comments posibility + ???
		Toast.makeText(getApplicationContext(), "Server could not be reached",
				Toast.LENGTH_LONG).show();
		startMainActivity(Configurations.appStateOffUser);
	}

	private void logOnlineGuest() {
		// Login as Guest
		startMainActivity(Configurations.appStateOnGuest);
	}

	private void startMainActivity(int appState) {
		task = new LoadViewTask(appState);
		task.execute();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_login);
	}

	class LoadViewTask extends AsyncTask<Void, Integer, Void> {

		String tableName;
		int appState;
		Intent mInt;

		private LoadViewTask(int appState) {
			con = DataConnector.getInst(getApplicationContext());
			this.appState = appState;
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
							"", LastIDs.getLastIDGames());
				}
				progressbarStatus += 40;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.companyTable)) {
					con.getArrayFromQuerryWithPostVariable("",
							Keys.companyTable, "", LastIDs.getLastIDCompanies());
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.groupsTable)) {
					con.getArrayFromQuerryWithPostVariable("",
							Keys.groupsTable, "", LastIDs.getLastIDGroups());
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				if (!con.checkDBTableExits(Keys.newsTable)) {
					con.getArrayFromQuerryWithPostVariable("", Keys.newsTable,
							"", LastIDs.getLastIDNews());
				}
				progressbarStatus += 20;
				progressDialog.setProgress(progressbarStatus);
				mInt = new Intent(getApplicationContext(), MainActivity.class);
				mInt.putExtra(Keys.AppState, appState);
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

	private boolean checkServerStatus() {
		if (isNetworkAvailable()) {
			return con.checkConnection();
		}
		return false;
	}

	private boolean isNetworkAvailable() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] mNetInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : mNetInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	public boolean checkCredentials() {
		String userName = username.getText().toString();
		String passWord = password.getText().toString();
		if (userName.isEmpty() || passWord.isEmpty())
			Toast.makeText(getApplicationContext(),
					"Please insert both username and password",
					Toast.LENGTH_SHORT).show();
		else {
			// if (con != null)
			return con.checkLogin(userName, passWord, prefrence);
			// return con.checkUsernameAndPassword(userName, passWord);
		}
		//
		return false;
	}
}