package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class GroupsListAdapter extends BaseAdapter implements MyBaseAdapter {
	LayoutInflater inflater;
	ImageView thumb_image;
	ArrayList<Bundle> groupsDataCollection;
	JSONArray groupsArray;
	ViewHolder holder;
	int count;
	boolean showMore = true;

	public GroupsListAdapter() {
		// TODO Auto-generated constructor stub
	}

	public GroupsListAdapter(Activity act, ArrayList<Bundle> map) {
		this.groupsDataCollection = map;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = 10;
	}

	public ArrayList<Bundle> getGamesList() {
		return groupsDataCollection;
	}

	public int getCount() {
		if (groupsDataCollection.size() >= count)
			return count;
		else
			return groupsDataCollection.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public void showMore() {
		if (showMore)
			if (count + 5 <= groupsDataCollection.size())
				count = count + 5;
			else {
				count = groupsDataCollection.size();
				showMore = false;
			}
	}

	public boolean canShowMore() {
		return showMore;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {

			vi = inflater.inflate(R.layout.component_mainlist_itemlayout, null);
			holder = new ViewHolder();

			holder.tvGameName = (TextView) vi
					.findViewById(R.id.gameList_GameName_TView);
			holder.tvGameType = (TextView) vi
					.findViewById(R.id.gameList_GameType_TView);
			holder.tvGameDate = (TextView) vi
					.findViewById(R.id.gameList_GameDate_TView);
			// holder.tvImage =(ImageView)vi.findViewById(R.id.list_image);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		// Setting all values in listview
		/*
		 * long seconds =
		 * Integer.valueOf(groupsDataCollection.get(position).get(
		 * Keys.GROUPDATE)); long millis = seconds * 1000; Date date = new
		 * Date(millis); SimpleDateFormat sdf = new
		 * SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
		 * sdf.setTimeZone(TimeZone.getTimeZone("UTC")); String formattedDate =
		 * sdf.format(date);
		 */
		if (groupsDataCollection != null) {
			holder.tvGameName.setText(groupsDataCollection.get(position)
					.getString(Keys.GROUPNAME));
			holder.tvGameType.setText(groupsDataCollection.get(position)
					.getString(Keys.GROUPTYPE)
					+ "  "
					+ groupsDataCollection.get(position).getString(
							Keys.GROUPTYPE2));
			holder.tvGameDate.setText(groupsDataCollection.get(position)
					.getString(Keys.GROUPDATE));
		}
		// Setting an image
		// String uri = "drawable/"+
		// gamesDataCollection.get(position).get(KEY_ICON);
		// int imageResource =
		// vi.getContext().getApplicationContext().getResources().getIdentifier(uri,
		// null, vi.getContext().getApplicationContext().getPackageName());
		// Drawable image =
		// vi.getContext().getResources().getDrawable(imageResource);
		// holder.tvImage.setImageDrawable(image);

		return vi;
	}

	static class ViewHolder {
		TextView tvGameName, tvGameType, tvGameDate;
		// ImageView tvImage;
	}

	@Override
	public ArrayList<Bundle> getList() {
		// TODO Auto-generated method stub
		return null;
	}
}
