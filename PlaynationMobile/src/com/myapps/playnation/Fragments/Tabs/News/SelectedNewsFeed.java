package com.myapps.playnation.Fragments.Tabs.News;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class SelectedNewsFeed extends Fragment {
	private TextView txtNewsAuthor;
	// private ImageView newsImage;
	private View mView;

	public void initNews() {
		WebView mNewsTitle = (WebView) mView.findViewById(R.id.webview);
		WebView mNewsText = (WebView) mView.findViewById(R.id.webview2);
		setupWebView(mNewsText);
		mNewsTitle.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		// newsImage = (ImageView) mView.findViewById(R.id.newsImg);
		txtNewsAuthor = (TextView) mView.findViewById(R.id.newsAuthor);
		Bundle args = getArguments();
		mNewsText.loadData(
				args.getString(Keys.NEWSCOLNEWSTEXT).replace("\\", ""),
				"text/html", null);

		mNewsTitle.loadData(
				args.getString(Keys.NEWSCOLHEADLINE).replace("\\", ""),
				"text/html", null);

		Spanned text = Html.fromHtml(args.getString(Keys.NEWSCOLPOSTINGTIME));
		String author = args.getString(Keys.Author);
		txtNewsAuthor.setText("Playnation.eu" + " -Author: " + author + " "
				+ text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_news, container, false);
		initNews();
		return mView;
	}

	public void setupWebView(WebView mView) {
		mView.setBackgroundColor(getResources().getColor(
				R.color.background_gradient));
		mView.getSettings().setLoadWithOverviewMode(true);
		mView.getSettings().setUseWideViewPort(true);
	}

	@Override
	public void onDestroy() {
		mView = null;
		super.onDestroy();
	}

}
