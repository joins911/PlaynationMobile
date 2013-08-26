package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomeListViewAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class HomeEventsFragment extends Fragment {
	private DataConnector con;
	ArrayList<HashMap<String, String>> results;
	@SuppressWarnings("unused")
	private ISectionAdapter mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ISectionAdapter) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_tabslistview,
				container, false);

		con = DataConnector.getInst(getActivity());

		ListView mListView = (ListView) view
				.findViewById(R.id.generalPlayerListView);

		if (!con.checkDBTableExits(Keys.HomeEventTable))
			con.queryPlayerEvents(Keys.TEMPLAYERID, getActivity());

		mListView.setAdapter(new HomeListViewAdapter(getActivity(), con
				.getTable(Keys.HomeEventTable, ""), this));
		/*
		 * mListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @SuppressWarnings({ "deprecation", "unchecked" })
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int postion, long id) { HashMap<String, String> results =
		 * (HashMap<String, String>) parent .getItemAtPosition(postion); Bundle
		 * args = new Bundle(); args.putString(Keys.Segment,
		 * Keys.TableHomeEvent); args.putString(Keys.EventHeadline,
		 * results.get(Keys.EventHeadline));
		 * args.putString(Keys.EventDescription,
		 * results.get(Keys.EventDescription));
		 * args.putString(Keys.EventLocation, results.get(Keys.EventLocation));
		 * args.putString(Keys.EventTime, results.get(Keys.EventTime)); Calendar
		 * cal = Calendar.getInstance(); cal.setTime(new
		 * Date(results.get(Keys.EventTime))); String[] time =
		 * results.get(Keys.EventDuration).split(":"); if
		 * (time[1].trim().equals("Day Event")) { } else {
		 * 
		 * int hour = Integer.parseInt(time[1].trim()); int min =
		 * Integer.parseInt(time[2].trim()); cal.add(Calendar.HOUR, hour);
		 * cal.add(Calendar.MINUTE, min);
		 * 
		 * } SimpleDateFormat format = con.dataTemplate;
		 * args.putString(Keys.EventEndDate, format.format(cal.getTime()));
		 * 
		 * args.putString(Keys.EventType, results.get(Keys.EventType));
		 * args.putString(Keys.EventIsPublic, results.get(Keys.EventIsPublic));
		 * args.putString(Keys.EventIsExpired,
		 * results.get(Keys.EventIsExpired));
		 * args.putString(Keys.EventInviteLevel,
		 * results.get(Keys.EventInviteLevel));
		 * mCallback.getAdapter().switchTo(Keys.HomeSTATE, args); } });
		 */
		return view;
	}

}
