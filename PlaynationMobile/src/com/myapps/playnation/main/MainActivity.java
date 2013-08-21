package com.myapps.playnation.main;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.ListsFragment;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.FlyOutContainer;

public class MainActivity extends ActionBarActivity implements ISectionAdapter {
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
	private int total;
	private boolean finished = false;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getSupportActionBar().setTitle("Playnation Mobile");
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.background_gradient));
		con = DataConnector.getInst(getApplicationContext());
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		initializePager();
		this.setContentView(root);
		setSupportProgressBarIndeterminateVisibility(true);
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
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {
				Configurations.getConfigs().setAdapterSection(arg0);
			}

		});
		mViewPager.setOffscreenPageLimit(6);
	}

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
		getMenuInflater().inflate(R.menu.barmenu, menu);

		MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchMenuItem);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String arg0) {
				searchList(arg0);
				Toast.makeText(getApplicationContext(), arg0,
						Toast.LENGTH_SHORT).show();
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {

				return false;

			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.menu_search: Toast.makeText(this,
		 * "Menu item search selected", Toast.LENGTH_SHORT).show(); break;
		 */

		default:
			break;
		}

		return true;
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

	public void searchList(String args) {
		ListsFragment frag = mSectionAdapter.getFragments()
				.get(mViewPager.getCurrentItem()).getListFragment();
		ArrayList<Bundle> temp = null;
		if (frag != null) {
			if (mViewPager.getCurrentItem() == Keys.GamesSTATE)
				temp = searchListGames(args);
			else if (mViewPager.getCurrentItem() == Keys.GroupsSTATE)
				temp = searchListGroups(args);
			else if (mViewPager.getCurrentItem() == Keys.NewsSTATE)
				Log.i("NewsSearch", "To Do");
			// else if (mViewPager.getCurrentItem() == Keys.PlayersSTATE)
			// temp = searchListPlayers(args);
			else if (mViewPager.getCurrentItem() == Keys.CompaniesSTATE)
				temp = searchListCompanies(args);
			if (temp != null)
				frag.setListBundle(temp);
		}
	}

	public ArrayList<Bundle> searchListGames(String args) {
		ArrayList<Bundle> list = con.getTable(Keys.gamesTable, "");
		ArrayList<Bundle> results = new ArrayList<Bundle>();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getString(Keys.GAMENAME).contains(args))
				results.add(list.get(i));
		return results;
	}

	public ArrayList<Bundle> searchListGroups(String args) {
		ArrayList<Bundle> list = con.getTable(Keys.groupsTable, "");
		ArrayList<Bundle> results = new ArrayList<Bundle>();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getString(Keys.GROUPNAME).contains(args))
				results.add(list.get(i));
		return results;
	}

	public ArrayList<Bundle> searchListCompanies(String args) {
		ArrayList<Bundle> list = con.getTable(Keys.companyTable, "");
		ArrayList<Bundle> results = new ArrayList<Bundle>();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getString(Keys.CompanyName).contains(args))
				results.add(list.get(i));
		return results;
	}

	public ArrayList<Bundle> searchListPlayers(String args) {
		ArrayList<Bundle> list = con.queryPlayerFriendsSearch("");
		ArrayList<Bundle> results = new ArrayList<Bundle>();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getString(Keys.PLAYERNAME).contains(args))
				results.add(list.get(i));
		return results;
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

	public void setIndeterminateVisibility(boolean isVisible) {
		setSupportProgressBarIndeterminateVisibility(isVisible);
	}

	public void finishTask(int viewPagerState) {
		Log.i("total:=" + total + " ", "state:=" + viewPagerState + "; "
				+ finished);
		total = total + viewPagerState;
		if (total == 0)
			setSupportProgressBarIndeterminateVisibility(false);
	}

	public void startTask(int viewPagerState) {
		total = total - viewPagerState;
		setSupportProgressBarIndeterminateVisibility(true);
	}
}
