package com.myapps.playnation.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerEventsFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerFriendsFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerGamesFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerGroupsFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerInfoFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerSubscriptionFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerWallFragment;

public class PlayersTabHostDesc extends TabHostDesc implements
		OnTabChangeListener {

	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initTabHost();
	}

	private FragmentTabHost initTabHost() {
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabhost);
		setupTab(PlayerInfoFragment.class, new TextView(getActivity()),
				Keys.TAB_INFO, getArguments());
		setupTab(PlayerWallFragment.class, new TextView(getActivity()),
				Keys.TAB_WALL, getArguments());
	//	setupTab(PlayerGroupsFragment.class, new TextView(getActivity()),
	//			Keys.TAB_GROUPS, getArguments());
		setupTab(PlayerGamesFragment.class, new TextView(getActivity()),
				Keys.TAB_GAMES, getArguments());
	//	setupTab(PlayerSubscriptionFragment.class, new TextView(getActivity()),
	//			Keys.TAB_SUBSCRIPTION, getArguments());
		setupTab(PlayerFriendsFragment.class, new TextView(getActivity()),
				Keys.TAB_FRIENDS, getArguments());
	//	setupTab(PlayerEventsFragment.class, new TextView(getActivity()),
	//			Keys.TAB_EVENTS, getArguments());
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
		mTabHost = null;
		super.onDestroyView();
	}

	@Override
	public void onTabChanged(String tabId) {
		if (Keys.TAB_INFO.equals(tabId)) {
			PlayerInfoFragment frag = new PlayerInfoFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 0;
			return;
		}
		if (Keys.TAB_WALL.equals(tabId)) {
			PlayerWallFragment frag = new PlayerWallFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 0;
			return;
		}
		if (Keys.TAB_MESSAGES.equals(tabId)) {
			PlayerGroupsFragment frag = new PlayerGroupsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 1;
			return;
		}
		if (Keys.TAB_GROUPS.equals(tabId)) {
			PlayerGamesFragment frag = new PlayerGamesFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 2;
			return;
		}
		if (Keys.TAB_GAMES.equals(tabId)) {
			PlayerSubscriptionFragment frag = new PlayerSubscriptionFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 3;
			return;
		}
		if (Keys.TAB_FRIENDS.equals(tabId)) {
			PlayerFriendsFragment frag = new PlayerFriendsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 5;
			return;
		}
		if (Keys.TAB_EVENTS.equals(tabId)) {
			PlayerEventsFragment frag = new PlayerEventsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 6;
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
