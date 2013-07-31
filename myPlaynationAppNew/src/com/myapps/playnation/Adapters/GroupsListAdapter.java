package com.myapps.playnation.Adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class GroupsListAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ImageView thumb_image;
	ArrayList<HashMap<String, String>> groupsDataCollection;
	JSONArray groupsArray;
	ViewHolder holder;

	public GroupsListAdapter() {
		// TODO Auto-generated constructor stub
	}

	public GroupsListAdapter(Activity act,
			ArrayList<HashMap<String, String>> map) {
		this.groupsDataCollection = map;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ArrayList<HashMap<String, String>> getGamesList() {
		return groupsDataCollection;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return groupsDataCollection.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
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
		holder.tvGameName.setText(groupsDataCollection.get(position).get(
				Keys.GROUPNAME));
		holder.tvGameType.setText(groupsDataCollection.get(position).get(
				Keys.GROUPTYPE)
				+ "  "
				+ groupsDataCollection.get(position).get(Keys.GROUPTYPE2));
		holder.tvGameDate.setText(groupsDataCollection.get(position).get(
				Keys.GROUPDATE));

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
}
