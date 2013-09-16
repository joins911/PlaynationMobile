package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.LoadImage;

public class CompanyListAdapter extends BaseAdapter implements MyBaseAdapter {
	private LayoutInflater inflator;
	private ArrayList<Bundle> companiesList;
	private int count = 10;
	private boolean showMore = true;

	public CompanyListAdapter(Activity context, ArrayList<Bundle> items) {
		this.companiesList = items;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = 10;
	}

	@Override
	public int getCount() {
		if (companiesList.size() >= count)
			return count;
		else
			return companiesList.size();
	}

	@Override
	public Object getItem(int position) {
		return companiesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null)
			v = inflator.inflate(R.layout.component_newslist_itemlayout, null);
		if (companiesList != null) {

			TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
			ImageView img = (ImageView) v.findViewById(R.id.imgPlayerAvatarLog);
			TextView txtText = (TextView) v.findViewById(R.id.txtNickNameText);
			txtTitle.setText(Html.fromHtml(companiesList.get(position)
					.getString(Keys.CompanyName)));
			txtText.setText(Html.fromHtml(companiesList.get(position)
					.getString(Keys.CompanyDesc)));
			String imageUrl = companiesList.get(position).getString(
					Keys.CompanyImageURL);
			img.setTag(imageUrl);

			new LoadImage(companiesList.get(position).getString(
					Keys.EventID_COMPANY), "company", Keys.companyTable,
					imageUrl, img, "companies").execute(img);
			img.setMaxWidth(Keys.globalMaxandMinImageSize);
			img.setMinimumWidth(Keys.globalMaxandMinImageSize);
			img.setMaxHeight(Keys.globalMaxandMinImageSize);
			img.setMinimumHeight(Keys.globalMaxandMinImageSize);
		}
		return v;
	}

	@Override
	public void showMore() {
		if (showMore)
			if (count + 10 <= companiesList.size())
				count = count + 10;
			else {
				count = companiesList.size();
				showMore = false;
			}
	}

	@Override
	public boolean canShowMore() {
		return showMore;
	}

	@Override
	public ArrayList<Bundle> getList() {
		// TODO Auto-generated method stub
		return companiesList;
	}

}
