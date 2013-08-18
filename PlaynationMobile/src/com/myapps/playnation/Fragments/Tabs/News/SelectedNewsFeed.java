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
	private WebView mWebView;
	private WebView mWebView2;
	private View mView;

	public void initNews() {
		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView2 = (WebView) mView.findViewById(R.id.webview2);
		// newsImage = (ImageView) mView.findViewById(R.id.newsImg);
		txtNewsAuthor = (TextView) mView.findViewById(R.id.newsAuthor);
		Bundle args = getArguments();
		mWebView2.loadData(args.getString(Keys.NEWSCOLNEWSTEXT), "text/html",
				null);

		mWebView.loadData(args.getString(Keys.NEWSCOLHEADLINE), "text/html",
				null);

		Spanned text = Html.fromHtml(args.getString(Keys.NEWSCOLPOSTINGTIME));
		String author = args.getString(Keys.Author);
		txtNewsAuthor.setText("Playnation.eu" + " -Author: " + author + " "
				+ text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater
				.inflate(R.layout.fragment_news, container, false);
		initNews();
		return mView;
	}

	@Override
	public void onDestroy() {
		mView = null;
		super.onDestroy();
	}

}
