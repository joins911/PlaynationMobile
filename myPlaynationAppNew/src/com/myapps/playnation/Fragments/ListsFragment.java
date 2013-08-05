package com.myapps.playnation.Fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CompanyListAdapter;
import com.myapps.playnation.Adapters.FriendsListAdapter;
import com.myapps.playnation.Adapters.GamesListAdapter;
import com.myapps.playnation.Adapters.GroupsListAdapter;
import com.myapps.playnation.Adapters.MyBaseAdapter;
import com.myapps.playnation.Adapters.NewsListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.NewsFeed;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;

public class ListsFragment extends Fragment {

	private DataConnector con;
	private View rootView;
	private int mViewPagerState;
	private ISectionAdapter mCallback;
	private ViewFlipper flipper = null;
	private ListView mList;

	public ListsFragment() {
		con = DataConnector.getInst(getActivity());
		// setRetainInstance(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ISectionAdapter) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.component_mainlist, container,
				false);
		mViewPagerState = this.getArguments().getInt(Keys.ARG_POSITION);
		ListView list = (ListView) rootView.findViewById(R.id.mainList);
		mList = list;

		// Button but = (Button) rootView.findViewById(R.id.showMoreButton);
		/*
		 * but.setOnClickListener(new OnClickListener(){ public void
		 * onClick(View v) { ======= Button but = (Button)
		 * rootView.findViewById(R.id.showMoreButton);
		 * but.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { >>>>>>> 8f627546d38847a030e8026a653fbd7383c40d29
		 * ((MyBaseAdapter) mList.getAdapter()).showMore(); ((BaseAdapter)
		 * mList.getAdapter()).notifyDataSetChanged(); // Sets the index to the
		 * last item of the list. mList.setSelection(((BaseAdapter)
		 * mList.getAdapter()) .getCount() - 1); Log.i("onClick showMore",
		 * "ListsFragment"); } });
		 */
		mList.setOnScrollListener(new OnScrollListener() {

			private int currentFirstVisibleItem;
			private int currentVisibleItemCount;
			private int currentScrollState;
			private boolean isLoading;

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				currentFirstVisibleItem = firstVisibleItem;
				currentVisibleItemCount = visibleItemCount;

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.currentScrollState = scrollState;
				this.isScrollCompleted();

			}

			private void isScrollCompleted() {
				if (this.currentVisibleItemCount > 0
						&& this.currentScrollState == SCROLL_STATE_IDLE) {
					/***
					 * In this way I detect if there's been a scroll which has
					 * completed
					 ***/
					/*** do the work for load more date! ***/
					if (((MyBaseAdapter) mList.getAdapter()).canShowMore()) {
						((MyBaseAdapter) mList.getAdapter()).showMore();
						((BaseAdapter) mList.getAdapter())
								.notifyDataSetChanged();
					}
				}
			}

		});
		if (HelperClass.isTablet(getActivity())) {
			flipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper1);
		}

		if (Keys.GamesSTATE == mViewPagerState) {
			initializeGames(list);
		} else if (Keys.GroupsSTATE == mViewPagerState) {
			initializeGroups(list);
		} else if (Keys.NewsSTATE == mViewPagerState) {
			initializeNews(list);
		} else if (Keys.PlayersSTATE == mViewPagerState) {
			initializePlayers(list);
		} else if (Keys.CompaniesSTATE == mViewPagerState) {
			initializeCompanies(list);
		}
		return rootView;
	}

	private void initializeGames(ListView list) {
		final ArrayList<Bundle> results = con.getTable(Keys.gamesTable, "");

		// list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		GamesListAdapter bindingData = new GamesListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				con.writeTempNewsTab(
						results.get(position).getString(Keys.ID_GAME),
						Keys.gamesubNewsTAB);

				tabletOrPhoneControll(Keys.GamesSTATE, results.get(position));
			}
		});
	}

	private void initializeGroups(ListView list) {
		final ArrayList<Bundle> results = con.getTable(Keys.groupsTable, "");
		// list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		GroupsListAdapter bindingData = new GroupsListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				tabletOrPhoneControll(Keys.GroupsSTATE, results.get(position));
			}
		});
	}

	private void initializeNews(ListView list) {
		final ArrayList<Bundle> results = con.getTable(Keys.newsTable, "");
		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		NewsListAdapter bindingData = new NewsListAdapter(getActivity(),
				HelperClass.createHeaderListView(HelperClass
						.queryNewsList(results)));
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (parent.getItemAtPosition(position) instanceof NewsFeed) {
					NewsFeed feed = (NewsFeed) parent
							.getItemAtPosition(position);
					Bundle edit = new Bundle();
					SimpleDateFormat format = con.dataTemplate;
					edit.putInt(Keys.NEWSCOLID_NEWS, feed.getKey_NewsFeedID());
					edit.putInt(Keys.NEWSCOLIMAGE, feed.getKey_NewsImage());
					edit.putString(Keys.NEWSCOLHEADLINE,
							feed.getKey_NewsTitle());
					edit.putString(Keys.NEWSCOLINTROTEXT,
							feed.getKey_NewsIntroText());
					edit.putString(Keys.NEWSCOLNEWSTEXT, feed.getKey_NewsText());
					edit.putString(Keys.Author, feed.getKey_Author());
					edit.putString(Keys.NEWSCOLPOSTINGTIME,
							format.format(feed.getKey_NewsDate().getTime()));

					tabletOrPhoneControll(Keys.NewsSTATE, edit);
				}
			}
		});
	}

	private void tabletOrPhoneControll(int state, Bundle edit) {
		if (flipper != null) {
			flipper.setDisplayedChild(2);
			flipper.showNext();
			mCallback.getAdapter().switchTo(state, edit);
		} else {
			mCallback.getAdapter().switchTo(state, edit);
		}
	}

	private void initializePlayers(ListView list) {

		final EditText edit = (EditText) rootView.findViewById(R.id.editText1);

		final ArrayList<Bundle> results = con.queryPlayerFriendsSearch(edit
				.getText());
		list = (ListView) rootView.findViewById(R.id.mainList);
		if (results != null) {
			FriendsListAdapter bindingData = new FriendsListAdapter(
					getActivity(), results);
			list.setAdapter(bindingData);
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					tabletOrPhoneControll(Keys.PlayersSTATE,
							results.get(position));
				}
			});
		}

		// Players ListView initialized twice Could cause bugs later on. NEEDS
		// TO CHANGE!

		Button btn = (Button) rootView.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ArrayList<Bundle> results = con
						.queryPlayerFriendsSearch(edit.getText());
				ListView list = (ListView) rootView.findViewById(R.id.mainList);
				if (results != null) {
					FriendsListAdapter bindingData = new FriendsListAdapter(
							getActivity(), results);
					list.setAdapter(bindingData);
					list.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							tabletOrPhoneControll(Keys.PlayersSTATE,
									results.get(position));
						}
					});

				}
			}
		});

		// GroupsListAdapter bindingData = new GroupsListAdapter(getActivity(),
		// results);
		// list.setAdapter(bindingData);
		// list.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Bundle args = new Bundle();
		// args.putString(Keys.PLAYERNAME,
		// results.get(position).get(Keys.GROUPNAME));
		// args.putString(Keys.PLAYERTYPE,
		// results.get(position).get(Keys.GROUPTYPE));
		//
		// args.putString(Keys.PLAYERDATE,
		// results.get(position).get(Keys.GROUPDATE));
		// mCallback.getAdapter().switchTo(Keys.PlayersSTATE, args);
		// // getChildFragmentManager().executePendingTransactions();
		//
		// }
		// });
	}
	
	public ListView getList()
	{
		return mList;
	}

	private void initializeCompanies(ListView list) {
		final ArrayList<Bundle> results = con.getTable(Keys.companyTable, "");
		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		CompanyListAdapter bindingData = new CompanyListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				con.writeTempNewsTab(
						results.get(position).getString(Keys.EventID_COMPANY),
						Keys.companysubNewsTAB);

				tabletOrPhoneControll(Keys.CompaniesSTATE,
						results.get(position));
			}
		});
	}

}
