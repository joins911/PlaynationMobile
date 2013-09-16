package com.myapps.playnation.Fragments.Tabs.Game;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.DialogSendCommentFragment;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;
import com.myapps.playnation.main.MainActivity;

public class GameInfoFragment extends Fragment {
	ISectionAdapter mCallback;

	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;
	private View view;
	private DataConnector con;

	public void initGameInfo() {
		con = DataConnector.getInst();
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

			final String id_game = myIntent.getString(Keys.ID_GAME);
			String isLiked = myIntent.getString(Keys.GameIsLiked);
			String isPlaying = myIntent.getString(Keys.GameisPlaying);

			TextView tx = (TextView) view.findViewById(R.id.btnAddGame);

			if (isPlaying.equals("1"))
				tx.setVisibility(View.GONE);

			tx.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DialogFragment dialog = new DialogSendCommentFragment();
					dialog.show(getChildFragmentManager(), "Game");
					Bundle argss = new Bundle();
					argss.putString(Keys.ID_PLAYER, Configurations.CurrentPlayerID);
					argss.putString(Keys.functionAnotherID, id_game);
					argss.putString(Keys.functionAction,
							Keys.POSTFUNCOMMANTAddGame);
					argss.putString(Keys.functionPhpName, "gameFunction.php");
					dialog.setArguments(argss);
				}
			});

			final TextView txlike = (TextView) view
					.findViewById(R.id.btnGameLike);
			if (isLiked.equals("1")) {
				txlike.setText(getActivity().getResources().getString(
						R.string.btnUnlike));
			} else {
				txlike.setText(getActivity().getResources().getString(
						R.string.btnLike));

			}
			txlike.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (txlike.getText().equals("Like"))
						con.functionQuery(Configurations.CurrentPlayerID, id_game,
								"gameFunction.php", Keys.POSTFUNCOMMANTLike, "");
					else
						con.functionQuery(Configurations.CurrentPlayerID, id_game,
								"gameFunction.php", Keys.POSTFUNCOMMANTUnLike,
								"");
				}
			});
			if (MainActivity.configs.getApplicationState() != 0) {
				tx.setVisibility(View.GONE);
				txlike.setVisibility(View.GONE);
			}

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
