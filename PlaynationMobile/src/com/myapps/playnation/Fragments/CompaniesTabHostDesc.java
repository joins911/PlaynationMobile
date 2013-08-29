package com.myapps.playnation.Fragments;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Companies.*;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class CompaniesTabHostDesc extends TabHostDesc implements OnTabChangeListener {

	
	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initTabHost();
	}
	
	private FragmentTabHost initTabHost()
	{
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabhost);
		setupTab(CompaniesInfoFragment.class,new TextView(getActivity()),Keys.TAB_INFO,getArguments());
		setupTab(CompaniesWallFragment.class,new TextView(getActivity()),Keys.TAB_WALL,getArguments());
		setupTab(CompaniesNewsFragment.class,new TextView(getActivity()),Keys.TAB_NEWS,getArguments());
		mTabHost.setOnTabChangedListener(this);
		return mTabHost;
	}

	private void setupTab(Class<?> fragClass,final View view, final String tag, Bundle bundle) {
		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {return view;}
		});
		mTabHost.addTab(setContent, fragClass,bundle);
	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.component_tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	@Override
	public void onDestroyView() {
		mTabHost = null;
		super.onDestroyView();		
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d("myLog", "onTabChanged(): tabId=" + tabId);
		Log.i("review", " =" + mTabHost.getHeight());
		if (Keys.TAB_INFO.equals(tabId)) {
			CompaniesInfoFragment frag = new CompaniesInfoFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 0;
			return;
		}
		if (Keys.TAB_WALL.equals(tabId)) {
			CompaniesWallFragment frag = new CompaniesWallFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 1;
			return;
		}
		if (Keys.TAB_NEWS.equals(tabId)) {
			CompaniesNewsFragment frag = new CompaniesNewsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 2;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder, Fragment frag) {
		FragmentManager fm = getFragmentManager();
		Bundle args = new Bundle();
		args.putAll(getArguments());
		frag.setArguments(args);
		if (fm.findFragmentByTag(tabId) == null) {
			fm.beginTransaction().replace(placeholder, frag, tabId).commit();
		}
	}
	
	@Override
	public void switchToTab(int tabIndex) {
		this.mTabHost.setCurrentTab(tabIndex);
		
	}
}
