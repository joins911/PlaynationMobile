package com.myapps.playnation.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.SpinnerAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.FlyOutContainer;

public class MainActivity extends FragmentActivity implements ISectionAdapter {
	// protected MyApp mMyApp;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionAdapter mSectionAdapter;
	FlyOutContainer root;
	DataConnector con;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		con = DataConnector.getInst(getApplicationContext());
		this.setContentView(R.layout.component_games_desc_layout);
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);

		initializePager();
		// initializeSearchMenu();

		this.setContentView(root);
		// mViewPager.setId(R.id.pager);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		this.setContentView(root);
	}

	private void initializePager() {
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) root.findViewById(R.id.pager);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		this.mSectionAdapter = new SectionAdapter(getSupportFragmentManager(),
				this, mViewPager);
		mViewPager.setAdapter(mSectionAdapter);
		mViewPager.setOffscreenPageLimit(6);
	}

	/*
	 * private void initializeSearchMenu() { EditText searchBox = (EditText)
	 * root .findViewById(R.id.component_searchsort_searchBox);
	 * 
	 * final Spinner spinner = (Spinner) root
	 * .findViewById(R.id.component_spinner); SpinnerAdapter adapter = new
	 * SpinnerAdapter(this, android.R.layout.simple_dropdown_item_1line,
	 * con.getGroupTypes()); spinner.setAdapter(adapter);
	 * 
	 * /* spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	 * 
	 * @Override public void onItemSelected(AdapterView<?> parent, View view,
	 * int position, long id) { String selectedType =
	 * spinner.getSelectedItem().toString(); if
	 * (!selectedType.equalsIgnoreCase("All")) { searchResults.clear(); for (int
	 * i = 0; i < results.size(); i++) { Log.e("log.MainList in for loop",
	 * "selectedType = " + selectedType + "; results[i]" +
	 * results.get(i).get(Keys.GROUPTYPE) + ";"); if
	 * (results.get(i).get(Keys.GROUPTYPE) .equals(selectedType))
	 * searchResults.add(results.get(i)); } bindingData.notifyDataSetChanged();
	 * } else { searchResults.clear(); searchResults.addAll(results);
	 * bindingData.notifyDataSetChanged(); } }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> parent) { }
	 * 
	 * }); searchBox.addTextChangedListener(new TextWatcher() { public void
	 * onTextChanged(CharSequence s, int start, int before, int count) { // get
	 * the text in the EditText String searchString =
	 * searchBox.getText().toString(); int textLength = searchString.length();
	 * searchResults.clear();
	 * 
	 * for (int i = 0; i < results.size(); i++) { String playerName =
	 * results.get(i).get(Keys.GROUPNAME) .toString(); if (textLength <=
	 * playerName.length()) { // compare the String in EditText with Names in
	 * the // ArrayList //
	 * if(searchString.equalsIgnoreCase(playerName.substring(0,textLength))) if
	 * (playerName.contains(s)) searchResults.add((results.get(i))); } }
	 * bindingData.notifyDataSetChanged();
	 * 
	 * }
	 * 
	 * public void beforeTextChanged(CharSequence s, int start, int count, int
	 * after) { }
	 * 
	 * public void afterTextChanged(Editable s) { } });
	 * 
	 * }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.d("MainActivity", "MENU pressed");
			this.root.toggleMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void onHome_click(View v) {
		toggleMenu(Keys.HomeSTATE);
	}

	public void onGames_click(View v) {
		toggleMenu(Keys.GamesSTATE);
	}

	public void onGroups_click(View v) {
		toggleMenu(Keys.GroupsSTATE);
	}

	public void onNews_click(View v) {
		toggleMenu(Keys.NewsSTATE);
	}

	public void onPlayers_click(View v) {
		toggleMenu(Keys.PlayersSTATE);
	}

	public void onCompanies_click(View v) {
		toggleMenu(Keys.CompaniesSTATE);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) {
	 * 
	 * switch (item.getItemId()) {
	 * 
	 * case 1: Toast msg = Toast.makeText(MainActivity.this, "Menu 1",
	 * Toast.LENGTH_LONG); this.root.toggleMenu(); // msg.show(); return true;
	 * 
	 * case 2:
	 * 
	 * mViewPager.setCurrentItem(2); return true;
	 * 
	 * case 3: mViewPager.beginFakeDrag(); mViewPager.fakeDragBy(-150);
	 * mViewPager.endFakeDrag(); return true;
	 * 
	 * default: break;
	 * 
	 * } return super.onOptionsItemSelected(item); }
	 */

	public SectionAdapter getAdapter() {
		return this.mSectionAdapter;
	}

	public void toggleMenu(int pageID) {
		this.root.toggleMenu();
		mViewPager.setCurrentItem(pageID);
	}

	@Override
	public void onBackPressed() {
		if (mSectionAdapter.canBack(mViewPager.getCurrentItem()))
			mSectionAdapter.onBackBtnPressed();
		else
			super.onBackPressed();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void setPageAndTab(int pageIndex, int tabIndex, Bundle args) {
		mViewPager.setCurrentItem(pageIndex);
		mSectionAdapter.setPageAndTab(pageIndex, tabIndex, args);
	}
}
