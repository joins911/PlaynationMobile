package com.myapps.playnation.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.News.SelectedNewsFeed;
import com.myapps.playnation.Operations.HelperClass;

public class WrapperFragment extends Fragment {
	private Fragment mFragments;
	private Fragment mHeaderFragment;
	private boolean canBack = false;
	private int mViewPagerState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPagerState = getArguments().getInt(Keys.ARG_POSITION);
		Bundle args = new Bundle();
		args.putInt(Keys.ARG_POSITION, mViewPagerState);
		if (mViewPagerState != Keys.HomeSTATE) {
			mFragments = new ListsFragment();
			mFragments.setArguments(args);
		} else {
			mFragments = new HeaderFragment();
			mFragments.setArguments(args);
		}
		setRetainInstance(true);
		mFragments.setRetainInstance(true);
		getChildFragmentManager().beginTransaction()
				.replace(R.id.container_wrapper, mFragments).commit();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// There has to be a view with id `container` inside `wrapper.xml`

		return inflater.inflate(R.layout.wrapper, container, false);
	}

	public boolean canBack() {
		return canBack;
	}

	public void switchToHeader(Bundle args) {
		if (mViewPagerState == 3) {
			mHeaderFragment = new SelectedNewsFeed();
			mHeaderFragment.setArguments(args);
			canBack = true;

		} else if (mViewPagerState != 0) {
			args.putAll(getArguments());
			mHeaderFragment = new HeaderFragment();
			mHeaderFragment.setArguments(args);
			canBack = true;
		}
		if (HelperClass.isTablet(getActivity())
				&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			getChildFragmentManager().beginTransaction()
					.replace(R.id.fragment1, mHeaderFragment)
					.addToBackStack(null).commit();
		} else {
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_wrapper, mHeaderFragment)
					.addToBackStack(null).commit();
		}

	}

	public void switchBack() {
		if (mFragments == null) {
			Bundle args = new Bundle();
			args.putInt(Keys.ARG_POSITION,
					getArguments().getInt(Keys.ARG_POSITION));
			mFragments = new ListsFragment();
			mFragments.setArguments(args);
		}
		canBack = false;
		getChildFragmentManager().beginTransaction()
				.replace(R.id.container_wrapper, mFragments).commit();

	}

	public void switchToTab(int tabIndex, Bundle args) {
		if (mFragments instanceof ListsFragment) {
			this.switchToHeader(args);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((HeaderFragment) mHeaderFragment).switchToTab(tabIndex);
		} else {
			((HeaderFragment) mHeaderFragment).switchToTab(tabIndex);
		}

	}

}
