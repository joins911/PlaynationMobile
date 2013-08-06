package com.myapps.playnation.Fragments.Tabs.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class SelectedHomeEvent extends Fragment {
	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;

	private View mView;

	public void initEvent() {
		txtNewsTitle = (WebView) mView.findViewById(R.id.webview);
		txtNewsText = (WebView) mView.findViewById(R.id.webview2);
		// newsImage = (ImageView) mView.findViewById(R.id.newsImg);

		Bundle args = getArguments();
		txtNewsTitle.loadData(args.getString(Keys.EventHeadline), "text/html",
				null);
		// txtNewsText.setText(args.getString(Keys.NEWSCOLNEWSTEXT));
		// txtNewsText.setFocusable(true);
		txtNewsText.loadData(args.getString(Keys.EventDescription),
				"text/html", null);

		// newsImage.setImageResource(args.getInt("NewsFeedImg", 0));

		TextView txtEventEndDate = (TextView) mView
				.findViewById(R.id.newsEventEndDate);
		TextView txtEventInvetation = (TextView) mView
				.findViewById(R.id.newsEventInvetation);
		TextView txtEventLocation = (TextView) mView
				.findViewById(R.id.newsEventLocation);
		TextView txtEventPartcipants = (TextView) mView
				.findViewById(R.id.newsEventPartcipants);
		TextView txtEventPrivacy = (TextView) mView
				.findViewById(R.id.newsEventPrivacy);
		TextView txtEventStartDate = (TextView) mView
				.findViewById(R.id.newsEventStartDate);
		TextView txtEventType = (TextView) mView
				.findViewById(R.id.newsEventType);

		// newsImage.setImageResource(myIntent.getString(Keys.NEWSCOLIMAGE, 0));

		txtEventInvetation.setText(args.getString(Keys.EventInviteLevel));
		txtEventLocation.setText(args.getString(Keys.EventLocation));

		txtEventPartcipants.setText("0");
		txtEventPrivacy.setText(args.getString(Keys.EventIsPublic));
		txtEventStartDate.setText(args.getString(Keys.EventTime));
		txtEventType.setText(args.getString(Keys.EventType));
		txtEventEndDate.setText(args.getString(Keys.EventEndDate));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home_event_first, container,
				false);
		initEvent();
		return mView;
	}

	@Override
	public void onDestroy() {
		mView = null;
		super.onDestroy();
	}
}
