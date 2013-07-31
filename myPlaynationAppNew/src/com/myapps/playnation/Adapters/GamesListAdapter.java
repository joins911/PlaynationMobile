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
import com.myapps.playnation.Operations.DataConnector;

/**
 * 
 * @author viperime Adapter for the Games ListView which sets up all the items
 *         in the list
 */
public class GamesListAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ImageView thumb_image;
	ArrayList<HashMap<String, String>> gamesDataCollection;
	JSONArray gamesArray;
	ViewHolder holder;
	DataConnector con;

	public GamesListAdapter(Activity act, ArrayList<HashMap<String, String>> map) {
		this.gamesDataCollection = map;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		con = DataConnector.getInst(act);
	}

	public ArrayList<HashMap<String, String>> getGamesList() {
		return gamesDataCollection;
	}

	public int getCount() {
		return gamesDataCollection.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	/**
	 * @param position
	 *            : The position of the View within the list
	 * @param parent
	 *            : The parent of the List
	 */
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
			// holder.tvImage
			// =(ImageView)vi.findViewById(R.id.gameList_GameImage);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		// Setting all values in listview

		holder.tvGameName.setText(gamesDataCollection.get(position).get(
				Keys.GAMENAME));
		holder.tvGameType.setText(gamesDataCollection.get(position).get(
				Keys.GAMETYPE));
		holder.tvGameDate.setText(gamesDataCollection.get(position).get(
				Keys.GAMEDATE));
		/*
		 * For getting image try {
		 * holder.tvImage.setImageBitmap(con.getPicture()); } catch
		 * (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// if(holder.tvImage)
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
		ImageView tvImage;
	}
}
