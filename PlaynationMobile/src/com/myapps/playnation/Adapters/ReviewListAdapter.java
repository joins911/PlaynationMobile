package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class ReviewListAdapter extends BaseAdapter {
	LayoutInflater inflater;
	ArrayList<Bundle> reviewsDataCollection;
	ViewHolder holder;

	public ReviewListAdapter(Activity act, ArrayList<Bundle> map) {
		this.reviewsDataCollection = map;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reviewsDataCollection.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return reviewsDataCollection.get(index);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {

			vi = inflater.inflate(R.layout.component_reviewlist_item, null);
			holder = new ViewHolder();

			holder.tvReviewTitle = (TextView) vi
					.findViewById(R.id.gameR_listTitle_TV);
			holder.tvReviewDesc = (TextView) vi
					.findViewById(R.id.gameR_listContent_TV);
			holder.tvReviewUser = (TextView) vi
					.findViewById(R.id.gameR_listUser_TV);
			holder.tvReviewDate = (TextView) vi
					.findViewById(R.id.gameR_listDate_TV);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		holder.tvReviewTitle.setText(reviewsDataCollection.get(position)
				.getString(Keys.GAMENAME));
		holder.tvReviewDesc.setText(reviewsDataCollection.get(position)
				.getString(Keys.GAMEDESC));
		holder.tvReviewUser.setText(reviewsDataCollection.get(position)
				.getString(Keys.GAMENAME));
		holder.tvReviewDate.setText(reviewsDataCollection.get(position)
				.getString(Keys.GAMEDATE));
		return vi;
	}

	static class ViewHolder {
		TextView tvReviewTitle, tvReviewDesc, tvReviewUser, tvReviewDate;
		// ImageView tvImage;
	}
}
