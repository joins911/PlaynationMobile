package com.myapps.playnation.Fragments.Tabs.Game;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.NewsListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;

public class GameNewsFragment extends Fragment {
	private View mView;
	private ListView list;
	private DataConnector con;
	private ISectionAdapter mCallback;
	private TextView txtMessage;

	public void initGameNews() {
		con = DataConnector.getInst();
		final ArrayList<HashMap<String, String>> results = con
				.getTable(Keys.newsTempTable);
		if (results != null) {
			list = (ListView) mView.findViewById(R.id.mainList);
			txtMessage = (TextView) mView.findViewById(R.id.frag_Gnews_TView);
			if (results.size() != 0)
				txtMessage.setVisibility(View.GONE);

			NewsListAdapter bindingData = new NewsListAdapter(getActivity(),
					HelperClass.createHeaderListView(HelperClass
							.queryNewsList(results)));
			list.setAdapter(bindingData);
			// list.setOnItemClickListener(new OnItemClickListener() {
			//
			// @SuppressLint("SimpleDateFormat")
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id) {
			//
			// if (parent.getItemAtPosition(position) instanceof NewsFeed) {
			// NewsFeed feed = (NewsFeed) parent
			// .getItemAtPosition(position);
			// Bundle edit = new Bundle();
			// SimpleDateFormat format = con.dataTemplate;
			//
			// edit.putInt(Keys.NEWSCOLID_NEWS,
			// feed.getKey_NewsFeedID());
			// edit.putInt(Keys.NEWSCOLIMAGE, feed.getKey_NewsImage());
			// edit.putString(Keys.NEWSCOLHEADLINE,
			// feed.getKey_NewsTitle());
			// edit.putString(Keys.NEWSCOLINTROTEXT,
			// feed.getKey_NewsIntroText());
			// edit.putString(Keys.NEWSCOLNEWSTEXT,
			// feed.getKey_NewsText());
			// edit.putString(Keys.Author, feed.getKey_Author());
			// edit.putString(Keys.NEWSCOLPOSTINGTIME,
			// format.format(feed.getKey_NewsDate().getTime()));
			// mCallback.getAdapter().switchTo(Keys.NewsSTATE, edit);
			// }
			// }
			// });
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_game_news, container, false);
		initGameNews();
		return mView;
	}

	@Override
	public void onDestroy() {
		mCallback = null;
		super.onDestroy();
	}
}
