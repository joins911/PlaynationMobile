package com.myapps.playnation.Operations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.DataSection;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.NewsFeed;
import com.myapps.playnation.Classes.NewsFeedItem;

public class HelperClass {
	/**
	 * Checks if current device is tablet.
	 * 
	 * @param content
	 * @return boolean
	 */
	public static boolean isTablet(Context content) {
		boolean large = ((content.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		boolean xlarge = ((content.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
		return (large || xlarge);
	}

	public static void getListViewSize(ListView myListView) {
		BaseAdapter myListAdapter = (BaseAdapter) myListView.getAdapter();
		if (myListAdapter == null) {
			// do nothing return null
			return;
		}
		// set listAdapter in loop for getting final size
		int totalHeight = 0;
		for (int size = 0; size < myListAdapter.getCount(); size++) {
			View listItem = myListAdapter.getView(size, null, myListView);
			if (listItem instanceof ViewGroup)
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight() / 2.8;
		}
		// setting listview item in adapter
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
		myListView.setLayoutParams(params);
		// print height of adapter on log
		Log.i("height of listItem:", String.valueOf(totalHeight));
	}

	/**
	 * Checks if particular game that person is tracking has comments.If not
	 * returns the specific game description.
	 * 
	 * @param game
	 * @return string
	 */
	public static String checkGameComments(Bundle game) {
		String result = "";
		if (game.getString(Keys.GameComments).equalsIgnoreCase("")) {
			result = game.getString(Keys.GAMEDESC);
		} else {
			result = game.getString(Keys.GameComments);
		}
		return result;
	}

	public static String sqliteQueryStrings(String tableName, String separeteID) {
		if (tableName.equals(Keys.HomeWallTable)) {
			return "SELECT * FROM " + tableName + " WHERE " + Keys.ID_OWNER
					+ "=" + Keys.TEMPLAYERID + " Order by "
					+ Keys.WallPostingTime + " desc;";
		} else if (tableName.equals(Keys.newsTable)) {
			return "SELECT * FROM " + tableName + " Order by "
					+ Keys.NEWSCOLPOSTINGTIME + " desc;";
		} else if (tableName.equals(Keys.groupsTable)
				|| tableName.equals(Keys.gamesTable)
				|| tableName.equals(Keys.companyTable)
				|| tableName.equals(Keys.HomeSubscriptionTable)) {
			return "SELECT * FROM " + tableName + ";";
		} else if (tableName.equals(Keys.HomeMsgTable)) {
			return "SELECT * FROM " + tableName + " Where " + Keys.ID_PLAYER
					+ "=" + Keys.TEMPLAYERID + ";";
		} else if (tableName.equals(Keys.HomeEventTable)) {
			return "SELECT * FROM " + tableName + " Where ID_PLAYER="
					+ Keys.TEMPLAYERID + ";";
		} else if (tableName.equals(Keys.HomeFriendsTable)) {
			return "SELECT * FROM " + tableName + " Where ID_OWNER="
					+ Keys.TEMPLAYERID + ";";
		} else if (tableName.equals(Keys.HomeGamesTable)) {
			return "SELECT * FROM " + tableName + " Where ID_PLAYER="
					+ Keys.TEMPLAYERID + ";";
		} else if (tableName.equals(Keys.HomeGroupTable)) {
			return "SELECT * FROM " + tableName + " Where ID_PLAYER="
					+ Keys.TEMPLAYERID + ";";
		} else if (tableName.equals(Keys.HomeWallRepliesTable)) {
			return "SELECT * FROM " + tableName + " Where "
					+ Keys.MessageID_CONVERSATION + "=" + separeteID
					+ " Order By MessageTime desc;";
		} else if (tableName.equals(Keys.HomeMsgRepliesTable)) {
			return "SELECT * FROM " + tableName + " Where " + Keys.ID_WALLITEM
					+ "=" + Keys.TEMPLAYERID + " " + Keys.ID_OWNER + "="
					+ separeteID + " Order By PostingTime desc;";
		}
		return "";
	}

	public static String getDate(String integer) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a",
				Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formattedDate = sdf.format(new Date());
		if (!integer.equalsIgnoreCase("null")) {
			long seconds = Integer.valueOf(integer);
			long millis = seconds * 1000;
			Date date = new Date(millis);
			formattedDate = sdf.format(date);
			return formattedDate;
		}
		return formattedDate;

	}

	public static String convertTime(int fetchDate, SimpleDateFormat sdf) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date((long) fetchDate * 1000));
		return sdf.format(cal.getTime());
	}

	public static String convertToAge(String age) {
		String[] dates = age.split("-");
		int year = Integer.parseInt(dates[0]);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		return (currentYear - year) + "";
	}

	public static String durationConverter(String time, Context v) {
		String returns = v.getResources().getString(R.string.Duration)
				+ v.getResources().getString(R.string.DayEvent);
		long duration = Long.parseLong(time);
		if (duration == 0)
			returns = v.getResources().getString(R.string.Duration)
					+ v.getResources().getString(R.string.DayEvent);
		else if (duration > 0) {
			long hours = duration / 3600;
			long min = (duration % 3600) / 60;
			returns = v.getResources().getString(R.string.Duration) + hours
					+ ":" + min;
		}
		return returns;
	}

	public static ArrayList<NewsFeedItem> createHeaderListView(
			ArrayList<NewsFeedItem> newsFeedList) {
		Collections.sort(newsFeedList, new Comparator<NewsFeedItem>() {
			@Override
			public int compare(NewsFeedItem lhs, NewsFeedItem rhs) {
				int i = 0;
				if (lhs instanceof NewsFeed) {
					if (rhs instanceof NewsFeed) {
						long d1 = ((NewsFeed) lhs).getKey_NewsDate().getTime()
								.getTime();
						long d2 = ((NewsFeed) rhs).getKey_NewsDate().getTime()
								.getTime();
						if (d2 > d1) {
							i = 1;
						} else if (d1 > d2) {
							i = -1;
						} else {
							i = 0;
						}
					}
				}
				return i;
			}
		});
		ArrayList<NewsFeedItem> temp = new ArrayList<NewsFeedItem>();
		int lastIndex = 0;
		for (int i = 0; i < newsFeedList.size(); i++) {
			final NewsFeed nf = (NewsFeed) newsFeedList.get(i);
			DataSection ds = new DataSection();
			ds.setKey_Title(dateWriter(nf));
			for (int a = lastIndex; a < temp.size(); a++) {
				DataSection newsFeedItem = (DataSection) temp.get(a);
				if (newsFeedItem instanceof DataSection) {
					if (ds.getKey_Title().equals(newsFeedItem.getKey_Title())) {
						int index = temp.size();
						temp.add(index, nf);
						break;
					}
					if (!((DataSection) temp.get(lastIndex)).getKey_Title()
							.equals(ds.getKey_Title())) {
						temp.add(ds);
						temp.add(nf);

						lastIndex = temp.indexOf(ds);
						break;
					}
				}
			}
			if (temp.size() == 0) {
				temp.add(ds);
				temp.add(nf);
			}
		}

		return temp;
	}

	@SuppressLint("SimpleDateFormat")
	private static String dateWriter(NewsFeed feed) {
		SimpleDateFormat time = new SimpleDateFormat("MMMMM dd, yyyy");
		Calendar date = Calendar.getInstance();
		Calendar compareDate = feed.getKey_NewsDate();
		int comparableDay = compareDate.get(Calendar.DAY_OF_YEAR);
		int currentDay = date.get(Calendar.DAY_OF_YEAR);
		if (comparableDay == currentDay)
			return "Today";
		if ((comparableDay + 1) == currentDay)
			return "Yesterday";
		if (comparableDay < currentDay)
			return time.format(compareDate.getTime());
		else {
			return time.format(compareDate.getTime());
		}
	}

	public static ArrayList<NewsFeedItem> queryNewsList(ArrayList<Bundle> result) {
		ArrayList<NewsFeedItem> newsFeedList = new ArrayList<NewsFeedItem>();
		for (int i = 0; i < result.size(); i++) {
			try {
				NewsFeed feed = new NewsFeed();

				feed.setKey_NewsFeedID(Integer.valueOf(result.get(i).getString(
						Keys.NEWSCOLID_NEWS)));
				feed.setKey_NewsText(result.get(i).getString(
						Keys.NEWSCOLNEWSTEXT));
				feed.setKey_NewsIntroText(result.get(i).getString(
						Keys.NEWSCOLINTROTEXT));
				feed.setKey_Author(result.get(i).getString(Keys.Author));
				feed.setKey_NewsFeedID(Integer.parseInt(result.get(i)
						.getString(Keys.NEWSCOLID_NEWS)));
				feed.setKey_NewsTitle(result.get(i).getString(
						Keys.NEWSCOLHEADLINE));
				feed.setKey_NewsDate(feed.convertTime(Integer.valueOf(result
						.get(i).getString(Keys.NEWSCOLPOSTINGTIME))));
				newsFeedList.add(feed);
			} catch (Exception e) {
				Log.e("ListsFragments ", " queryNewsList Error " + e);
			}
		}
		return newsFeedList;
	}

}