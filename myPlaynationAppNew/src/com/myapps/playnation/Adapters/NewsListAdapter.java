package com.myapps.playnation.Adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.DataSection;
import com.myapps.playnation.Classes.NewsFeed;
import com.myapps.playnation.Classes.NewsFeedItem;
import com.myapps.playnation.main.ISectionAdapter;

@SuppressWarnings("rawtypes")
public class NewsListAdapter extends ArrayAdapter implements MyBaseAdapter {
	private LayoutInflater inflator;
	private List<NewsFeedItem> newsFeedsLists;
	ISectionAdapter context;
	int count = 10;
	boolean showMore = true;

	@SuppressWarnings("unchecked")
	public NewsListAdapter(Activity context, List<NewsFeedItem> items) {
		super(context, 0, items);
		this.context = (ISectionAdapter) context;
		this.newsFeedsLists = items;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = 10;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		NewsFeedItem item = newsFeedsLists.get(position);

		if (item != null) {
			if (item.isSection()) {
				DataSection ds = (DataSection) item;
				row = inflator.inflate(
						R.layout.component_newslist_dateselected, null);
				TextView txtNewsTitle = (TextView) row
						.findViewById(R.id.txtNewsDate);
				txtNewsTitle.setText(ds.getKey_Title());
				row.setOnClickListener(null);
				row.setOnLongClickListener(null);
				row.setSelected(false);
				row.setLongClickable(false);
				row.setEnabled(false);
				row.setClickable(false);
				row.setFocusable(false);
			} else {
				NewsFeed feed = (NewsFeed) item;
				row = inflator.inflate(R.layout.component_newslist_itemlayout,
						null);
				TextView txtTitle = (TextView) row.findViewById(R.id.txtTitle);
				// ImageView img = (ImageView) row
				// .findViewById(R.id.imgPlayerAvatarLog);
				TextView txtText = (TextView) row
						.findViewById(R.id.txtNickNameText);
				txtTitle.setText(Html.fromHtml(feed.getKey_NewsTitle()));
				// img.setImageResource(feed.getKey_NewsImage());
				txtText.setText(Html.fromHtml(feed.getKey_NewsIntroText()));

			}
		}
		return row;
	}

	@Override
	public int getCount() {
		if (newsFeedsLists.size() <= count) {
			return newsFeedsLists.size();
		} else {
			return count;
		}
	}

	@Override
	public Object getItem(int position) {
		return newsFeedsLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public void showMore() {
		if (showMore)
			if (count + 10 <= newsFeedsLists.size())
				count = count + 10;
			else {
				count = newsFeedsLists.size();
				showMore = false;
			}
	}

	@Override
	public boolean canShowMore() {
		// TODO Auto-generated method stub
		return false;
	}
}
