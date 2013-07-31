package com.myapps.playnation.Adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.main.ISectionAdapter;

public class CompanyListAdapter extends BaseAdapter {
	private LayoutInflater inflator;
	private ArrayList<HashMap<String, String>> companiesList;
	ISectionAdapter context;

	public CompanyListAdapter(Activity context,
			ArrayList<HashMap<String, String>> items) {
		this.context = (ISectionAdapter) context;
		this.companiesList = items;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, String> map = (HashMap<String, String>) companiesList
				.get(position);
		View v = convertView;
		if (v == null)
			v = inflator.inflate(R.layout.component_newslist_itemlayout, null);

		TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
		// ImageView img = (ImageView) v.findViewById(R.id.imgPlayerAvatarLog);
		TextView txtText = (TextView) v.findViewById(R.id.txtNickNameText);
		txtTitle.setText(Html.fromHtml(map.get(Keys.CompanyName)));
		// img.setImageResource(map.get(Keys.CompanyImageURL));
		txtText.setText(Html.fromHtml(map.get(Keys.CompanyDesc)));

		return v;
	}

}
