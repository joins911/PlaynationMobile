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
import com.myapps.playnation.Fragments.Tabs.Game.GameInfoFragment;
import com.myapps.playnation.Fragments.Tabs.Game.GameNewsFragment;
import com.myapps.playnation.Fragments.Tabs.Game.GamePlayersFragment;
import com.myapps.playnation.Fragments.Tabs.Game.GameReviewFragment;
import com.myapps.playnation.Fragments.Tabs.Game.GameWallFragment;

public class GameTabHostDesc extends TabHostDesc implements OnTabChangeListener {

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
		setupTab(GameInfoFragment.class, new TextView(getActivity()),
				Keys.TAB_INFO, getArguments());
		setupTab(GameWallFragment.class, new TextView(getActivity()),
				Keys.TAB_WALL, getArguments());
		setupTab(GameNewsFragment.class, new TextView(getActivity()),
				Keys.TAB_NEWS, getArguments());
		// setupTab(GameReviewFragment.class, new TextView(getActivity()),
		// Keys.TAB_REVIEW, getArguments());
		setupTab(GamePlayersFragment.class, new TextView(getActivity()),
				Keys.TAB_PLAYERS, getArguments());
		// setupTab(GameMediaFragment.class,new
		// TextView(getActivity()),Keys.TAB_MEDIA);
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
		Log.d("myLog", "onTabChanged(): tabId=" + tabId);
		Log.i("review", " =" + mTabHost.getHeight());
		if (Keys.TAB_INFO.equals(tabId)) {
			GameInfoFragment frag = new GameInfoFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 0;
			return;
		}
		if (Keys.TAB_NEWS.equals(tabId)) {
			GameNewsFragment frag = new GameNewsFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 1;
			return;
		}
		if (Keys.TAB_WALL.equals(tabId)) {
			GameWallFragment frag = new GameWallFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 2;
			return;
		}
		if (Keys.TAB_REVIEW.equals(tabId)) {
			GameReviewFragment frag = new GameReviewFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 3;
			return;
		}
		if (Keys.TAB_PLAYERS.equals(tabId)) {
			GamePlayersFragment frag = new GamePlayersFragment();
			updateTab(tabId, android.R.id.tabcontent, frag);
			// mCurrentTab = 4;
			return;
		}
		/*
		 * if (Keys.TAB_FORUM.equals(tabId)) { GameForumFragment frag = new
		 * GameForumFragment(); updateTab(tabId, android.R.id.tabcontent, frag);
		 * // mCurrentTab = 5; return; } if (Keys.TAB_MEDIA.equals(tabId)) {
		 * GameMediaFragment frag = new GameMediaFragment(); updateTab(tabId,
		 * android.R.id.tabcontent, frag); // mCurrentTab = 6; return; }
		 */
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
