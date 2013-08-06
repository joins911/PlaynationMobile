package com.myapps.playnation.main;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class GameDescriptionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_game_description);
		//ViewGroup vg = (ViewGroup) findViewById(R.id.gameM_Header_Empty);
        //ViewGroup.inflate(GameDescriptionActivity.this, R.layout.activity_game_description, vg);
		TextView gameName = (TextView) findViewById(R.id.gameM_NameText_TView);
		TextView gameType = (TextView) findViewById(R.id.gameM_TypeText_TView);
		TextView gameDesc = (TextView) findViewById(R.id.gamesDesc_descriptionText_TView);
		TextView rating = (TextView) findViewById(R.id.gameM_RatingsNr_TView);
		//RatingBar bar = (RatingBar) findViewById(R.id.gameM_RatingBar1);
		Intent myInt = getIntent();
		//gameReview.setText(android.text.Html.fromHtml(myInt.getStringExtra(Keys.GAMEDESC)));
		gameDesc.setText(android.text.Html.fromHtml(myInt.getStringExtra(Keys.GAMEDESC)));
		rating.setText(myInt.getStringExtra(Keys.RATING));
		gameName.setText(myInt.getStringExtra(Keys.GAMENAME));
	//	bar.setRating(Float.valueOf(myInt.getStringExtra(Keys.RATING)));
		gameType.setText(myInt.getStringExtra(Keys.GAMETYPE));
		//gameName.setText(myInt.getStringExtra(Keys.GAMENAME));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.game_description, menu);
		return false;
	}

}
