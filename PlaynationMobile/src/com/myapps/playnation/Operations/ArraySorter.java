package com.myapps.playnation.Operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.myapps.playnation.Classes.Keys;

public class ArraySorter {

	// private ArrayList<HashMap<String,String>> myArray;
	
	public static ArrayList<HashMap<String, String>> sortBy(ArrayList<HashMap<String, String>> map, final String columnName)
	{
		Collections.sort(map, new Comparator<HashMap<String, String>>() {
			public int compare(HashMap<String, String> o1,
					HashMap<String, String> o2) {
				String t1 = o1.get(columnName);
				String t2 = o2.get(columnName);
				return t2.compareTo(t1);
			}
		});
		return map;
	}

	
	public static ArrayList<HashMap<String, String>> sortAlphabeticaly(
			ArrayList<HashMap<String, String>> map, int tableId) {
		if (tableId == Keys.gamesID)
			Collections.sort(map, new Comparator<HashMap<String, String>>() {
				public int compare(HashMap<String, String> o1,
						HashMap<String, String> o2) {
					String t1 = o1.get(Keys.GAMENAME);
					String t2 = o2.get(Keys.GAMENAME);
					return t2.compareTo(t1);
				}
			});
		else if (tableId == Keys.groupsID)
			Collections.sort(map, new Comparator<HashMap<String, String>>() {
				public int compare(HashMap<String, String> o1,
						HashMap<String, String> o2) {
					String t1 = o1.get(Keys.GROUPNAME);
					String t2 = o2.get(Keys.GROUPNAME);
					return t1.compareTo(t2);
				}
			});
		return map;

	}

	public static ArrayList<HashMap<String, String>> sortByDate(
			ArrayList<HashMap<String, String>> map, int tableId) {
		if (tableId == Keys.gamesID)
			Collections.sort(map, new Comparator<HashMap<String, String>>() {
				public int compare(HashMap<String, String> o1,
						HashMap<String, String> o2) {
					String t1 = o1.get(Keys.GAMEDATE);
					String t2 = o2.get(Keys.GAMEDATE);
					return t1.compareTo(t2);
				}
			});
		else if (tableId == Keys.groupsID)
			Collections.sort(map, new Comparator<HashMap<String, String>>() {
				public int compare(HashMap<String, String> o1,
						HashMap<String, String> o2) {
					String t1 = o1.get(Keys.GROUPDATE);
					String t2 = o2.get(Keys.GROUPDATE);
					return t1.compareTo(t2);
				}
			});
		return map;
	}

	public static ArrayList<HashMap<String, String>> sortByRating(
			ArrayList<HashMap<String, String>> map, int tableId) {
		Collections.sort(map, new Comparator<HashMap<String, String>>() {
			public int compare(HashMap<String, String> o1,
					HashMap<String, String> o2) {
				String t1 = o1.get(Keys.RATING);
				String t2 = o2.get(Keys.RATING);
				return t1.compareTo(t2);
			}
		});
		return map;
	}
	
}
