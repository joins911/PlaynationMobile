package com.myapps.playnation.main;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.WrapperFragment;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;

/**
 * 
 * @author Claudiu Manea The SectionAdapter class is in charge of the
 *         ViewPager's Fragments (switching between them) aswell as acting as a
 *         intermediary between the activity and the fragments attached
 * 
 */
public class SectionAdapter extends FragmentPagerAdapter {
	Activity act;
	DataConnector con;
	FragmentManager fm;
	ArrayList<String> titles;
	ViewPager mContainer;
	private boolean enabled;
	private ArrayList<WrapperFragment> currFragments;
	private final int slideCounts = 6;

	/**
	 * SectionAdapter constructor: gets the parent activity,Fragment manager and
	 * VPager *
	 */
	public SectionAdapter(FragmentManager fm, Activity act, ViewPager pager) {
		super(fm);
		this.fm = fm;
		this.act = act;
		this.enabled = true;
		this.mContainer = pager;
		prepareAdapter();

	}

	public void prepareAdapter() {
		titles = new ArrayList<String>();
		if (Configurations.isAppState(Configurations.appStateOnUser))
			titles.add("Home");
		titles.add("Games");
		titles.add("Groups");
		titles.add("News");
		titles.add("Players");
		titles.add("Companies");
		currFragments = new ArrayList<WrapperFragment>();
		con = DataConnector.getInst(act);
		for (int i = 0; i < titles.size(); i++)
			currFragments.add(null);
	}

	public ArrayList<WrapperFragment> getFragments() {
		return currFragments;
	}

	/**
	 * 
	 * 
	 */
	/**
	 * tells the current ViewPager ListFragment to switch to HeaderFragment
	 * aswell as updates the viewpager after the change took place
	 * 
	 * @param position
	 *            = viewpager.getCurrentItem()
	 */
	public void switchTo(int position, Bundle args) {
		if (currFragments.get(position) == null) {
			return;
		}
		this.startUpdate(mContainer);
		currFragments.get(position).switchToHeader(args);
		this.notifyDataSetChanged();
		this.finishUpdate(mContainer);
	}

	public void setPageAndTab(int pageState, int tabIndex, Bundle args) {
		// currFragments.get(pageState).switchToHeader(args);
		currFragments.get(pageState).switchToTab(tabIndex, args);
	}

	/*
	 * public void setPageAndTab(int pageState,int tabIndex,Bundle args) { //
	 * mContainer.setCurrentItem(pageState); //currFragments.get(pageState) //
	 * currFragments.get(pageState).switchToTab(tabIndex,args); }
	 * 
	 * /** Feeds back to the ViewPager the Fragments to be displayed in each
	 * section
	 */
	@Override
	public Fragment getItem(int position) {
		if (currFragments.get(position) == null) {
			Bundle args = new Bundle();
			args.putInt(Keys.ARG_POSITION, position);
			WrapperFragment frag = new WrapperFragment();
			frag.setArguments(args);
			currFragments.set(position, frag);
		}
		return currFragments.get(position);
	}

	/**
	 * returns to the ViewPager the amount of pages the Section contains
	 */
	@Override
	public int getCount() {
		// Show 4 total pages.
		return slideCounts;
	}

	/**
	 * Back Button functunality called from the main activity
	 */
	public void onBackBtnPressed() {
		currFragments.get(mContainer.getCurrentItem()).switchBack();
	}

	/**
	 * 
	 * @param index
	 *            = index of the section
	 * @return if the current page can go back meaning if the Fragment displayed
	 *         is HeaderFragment
	 */
	public boolean canBack(int index) {
		return currFragments.get(index).canBack();
	}

	@Override
	public String getPageTitle(int position) {
		return titles.get(position);
	}

	/*
	 * public TabFragment getTabFrag(int pos) { return tabFragments.get(pos); }
	 * 
	 * public void initializeFrags() { for (int i = 1; i <= 2; i++) {
	 * initTabFrag(i); } }
	 * 
	 * public void initTabFrag(int pos) { TabFragment tab = null; switch (pos) {
	 * case Keys.GamesSTATE: tab = new GameTabHostDesc(); break; case
	 * Keys.GroupsSTATE: tab = new GroupTabHostDesc(); break; }
	 * tabFragments.add(tab); }
	 * 
	 * 
	 * 
	 * public void replace(int position) {
	 * replace(position,tabFragments.get(position)); }
	 */

}
