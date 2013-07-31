package com.myapps.playnation.Fragments.Tabs.Companies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class CompaniesInfoFragment extends Fragment {
	private WebView txtNewsTitle;
	private WebView txtNewsText;
	// private ImageView newsImage;
	private View view;

	public void initCompany() {
		txtNewsText = (WebView) view.findViewById(R.id.webView2);
		txtNewsTitle = (WebView) view.findViewById(R.id.webView1);
		// newsImage = (ImageView) view.findViewById(R.id.newsImg);
		Bundle myIntent = getArguments();
		TextView txtInvetationLabel = (TextView) view
				.findViewById(R.id.textView9);

		txtInvetationLabel.setText(getActivity().getResources().getString(
				R.string.website));
		TextView txtEventType = (TextView) view
				.findViewById(R.id.newsEventType);
		TextView txtEventStartDate = (TextView) view
				.findViewById(R.id.newsEventStartDate);
		TextView txtEventEndDate = (TextView) view
				.findViewById(R.id.newsEventEndDate);
		TextView txtEventLocation = (TextView) view
				.findViewById(R.id.newsEventLocation);
		TextView txtEventPartcipants = (TextView) view
				.findViewById(R.id.newsEventPartcipants);
		TextView txtEventInvetation = (TextView) view
				.findViewById(R.id.newsEventInvetation);

		if (myIntent != null) {
			txtNewsTitle.loadData(myIntent.getString(Keys.CompanyName),
					"text/html", null);

			txtNewsText.loadData(myIntent.getString(Keys.CompanyDesc),
					"text/html", null);

			txtEventType.setText(myIntent.getString(Keys.CompanyType));
			String s = myIntent.getString(Keys.CompanyAddress);
			StringBuilder sb = new StringBuilder(s);
			int i = 10;
			while ((i = sb.indexOf(" ", i + 10)) != -1) {
				sb.replace(i, i + 1, "\r\n");
			}
			txtEventStartDate.setText(sb.toString());
			txtEventEndDate.setText(myIntent.getString(Keys.CompanyFounded));
			txtEventLocation.setText(myIntent.getString(Keys.CompanyEmployees));
			txtEventPartcipants.setText(myIntent
					.getString(Keys.CompanyCreatedTime));
			txtEventInvetation.setText(Uri.parse(myIntent
					.getString(Keys.CompanyURL)) + "");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_company_info, container,
				false);
		initCompany();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}