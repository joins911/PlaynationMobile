package com.myapps.playnation.login;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;

public class RegisterActivity extends Activity {
	private EditText registerName;
	private EditText registerEmail;
	private EditText registerPassword;
	DataConnector con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.activity_register);
		con = DataConnector.getInst(getApplicationContext());
		registerName = (EditText) findViewById(R.id.reg_fullname);
		registerEmail = (EditText) findViewById(R.id.reg_email);
		registerPassword = (EditText) findViewById(R.id.reg_password);
		Button btnRegister = (Button) findViewById(R.id.btnRegister);

		TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkServerStatus()) {
					String nickname = registerName.getText().toString();
					String email = registerEmail.getText().toString();
					String password = registerPassword.getText().toString();
					if (HelperClass.EmailPassNickCheck(registerEmail,
							registerPassword, registerName)) {
						if (con.registerPlayerMobileQuery(nickname,
								registerEmail, password))
							finish();
					}
				}
			}
		});
		// Listening to Login Screen link
		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
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
}