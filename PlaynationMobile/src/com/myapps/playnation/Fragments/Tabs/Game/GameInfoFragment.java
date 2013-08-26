package com.myapps.playnation.Fragments.Tabs.Game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;

public class GameInfoFragment extends Fragment {
	ISectionAdapter mCallback;

	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;
	private View view;

	public void initGameInfo() {
		txtNewsText = (WebView) view.findViewById(R.id.webview2);
		txtNewsTitle = (WebView) view.findViewById(R.id.webview);
		setupWebView(txtNewsText);
		txtNewsTitle.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		// newsImage = (ImageView) view.findViewById(R.id.newsImg);
		Bundle myIntent = getArguments();

		TextView txtType = (TextView) view.findViewById(R.id.txtgameinfoType);
		TextView txtReleased = (TextView) view
				.findViewById(R.id.txtgameinfoReleased);
		TextView txtEsrb = (TextView) view.findViewById(R.id.txtgameinfoEsrb);
		TextView txtDistr = (TextView) view
				.findViewById(R.id.txtgameinfoDistributors);
		TextView txtPlatform = (TextView) view
				.findViewById(R.id.txtgameinfoPlatforms);
		TextView txtPlayer = (TextView) view
				.findViewById(R.id.txtgameinfoPlayers);
		TextView txtDeveloper = (TextView) view
				.findViewById(R.id.txtgameInfoDevelopers);
		TextView txtJoined = (TextView) view
				.findViewById(R.id.txtgameinfoJoined);
		TextView txtWeb = (TextView) view.findViewById(R.id.txtgameinfoWebsite);

		if (myIntent != null) {
			String gameName = myIntent.getString(Keys.GAMENAME);
			String gameDesc = myIntent.getString(Keys.GAMEDESC);
			String gameDate = myIntent.getString(Keys.GAMEDATE);
			String gameEsrb = myIntent.getString(Keys.GAMEESRB);
			String gamePlCount = myIntent.getString(Keys.GAMEPLAYERSCOUNT);
			String gameType = myIntent.getString(Keys.GAMETYPE);
			String gameURL = myIntent.getString(Keys.GAMEURL);

			String gameTypeName = myIntent.getString(Keys.GAMETYPENAME);
			String gamePlatform = myIntent.getString(Keys.GAMEPLATFORM);
			String gameDistributor = myIntent
					.getString(Keys.GAMECompanyDistributor);
			String gameFounded = myIntent.getString(Keys.CompanyFounded);
			String gameDeveloper = myIntent.getString(Keys.CompanyName);

			txtNewsTitle.loadData(gameName, "text/html", null);
			// newsImage.setImageResource(myIntent.getIntExtra(Keys.NEWSCOLIMAGE,
			// 0));
			txtNewsText.loadData(gameDesc, "text/html", null);
			if (gameType.contains(gameTypeName)) {
				txtType.setText(gameTypeName);
			} else {
				txtType.setText(gameType);
			}
			txtReleased.setText(gameDate);
			txtEsrb.setText(gameEsrb);
			txtJoined.setText(gameFounded);
			txtPlatform.setText(gamePlatform);
			txtPlayer.setText(gamePlCount);
			if (!HelperClass.isTablet(getActivity())) {
				txtWeb.setText(" " + gameURL + " ");
			} else {
				txtWeb.setAutoLinkMask(0);
				txtWeb.setText(" " + gameURL + " ");
			}

			txtDistr.setText(gameDistributor);
			txtDeveloper.setText(gameDeveloper);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_game_info, container, false);
		initGameInfo();
		return view;
	}

	public void setupWebView(WebView mView) {
		mView.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		mView.getSettings().setLoadWithOverviewMode(true);
	}

	@Override
	public void onDestroy() {
		mCallback = null;
		super.onDestroy();
	}
}
