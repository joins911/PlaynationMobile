package com.myapps.playnation.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Group.GroupEventsFragment;
import com.myapps.playnation.Fragments.Tabs.Group.GroupInfoFragment;
import com.myapps.playnation.Fragments.Tabs.Group.GroupMediaFragment;
import com.myapps.playnation.Fragments.Tabs.Group.GroupWallFragment;
import com.myapps.playnation.Fragments.Tabs.Group.GroupPlayersFragment;

public class GroupTabHostDesc extends TabHostDesc implements
		OnTabChangeListener {
	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.component_game_tabholder,
		// container,false);
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabhost);
		setupTab(GroupInfoFragment.class, new TextView(getActivity()),
				Keys.TAB_INFO, getArguments());
		setupTab(GroupWallFragment.class, new TextView(getActivity()),
				Keys.TAB_WALL, getArguments());
		setupTab(GroupPlayersFragment.class, new TextView(getActivity()),
				Keys.TAB_PLAYERS, getArguments());
		setupTab(GroupMediaFragment.class, new TextView(getActivity()),
				Keys.TAB_MEDIA, getArguments());
		setupTab(GroupEventsFragment.class, new TextView(getActivity()),
				Keys.TAB_EVENTS, getArguments());
		mTabHost.setOnTabChangedListener(this);
		return mTabHost;
	}

	private void setupTab(Class<?> fragClass, final View view,
			final String tag, Bundle args) {
		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabHost.addTab(setContent, fragClass, args);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.component_tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d("myLog", "onTabChanged(): tabId=" + tabId);
		Log.i("review", " =" + mTabHost.getHeight());
		if (Keys.TAB_INFO.equals(tabId)) {
			GroupInfoFragment frag = new GroupInfoFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 0;
			return;
		}
		if (Keys.TAB_NEWS.equals(tabId)) {
			GroupWallFragment frag = new GroupWallFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 1;
			return;
		}
		if (Keys.TAB_PLAYERS.equals(tabId)) {
			GroupPlayersFragment frag = new GroupPlayersFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 4;
			return;
		}
		if (Keys.TAB_MEDIA.equals(tabId)) {
			GroupMediaFragment frag = new GroupMediaFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 6;
			return;
		}
		if (Keys.TAB_MEDIA.equals(tabId)) {
			GroupEventsFragment frag = new GroupEventsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 5;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder, Fragment frag) {
		FragmentManager fm = getFragmentManager();
		Bundle args = new Bundle();
		args.putInt(Keys.ARG_SECTION_NUMBER, 60);
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
