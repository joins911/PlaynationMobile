package com.myapps.playnation.Operations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.CommentInfo;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.UserComment;

/**
 * 
 * @author viperime
 * @category Operations Class in charge of data pulling from the database and
 *           holding it;
 */
public class DataConnector extends SQLiteOpenHelper {

	static DataConnector inst;
	InputStream is = null;
	HttpClient httpclient;
	// final String ServerIp ="87.55.208.165:1337";
	// final String ServerIp = "192.168.1.11:1337";
	final String ServerIp = "10.0.2.2";
	String url;
	HashMap<String, ArrayList<HashMap<String, String>>> lilDb;
	String[] gameTypes;
	String[] groupTypes;
	public final SimpleDateFormat dataTemplate = new SimpleDateFormat(
			"MMM dd,yyyy HH:mm", Locale.getDefault());
	private static JSONArray json;
	private static ArrayList<HashMap<String, String>> searchArray;
	private static ArrayList<HashMap<String, String>> arrayChildren = new ArrayList<HashMap<String, String>>();

	private static String DATABASE_NAME = "cdcol";
	private static int DATABASE_VERSION = 2;

	private DataConnector(Context con) {
		super(con, DATABASE_NAME, null, DATABASE_VERSION);
		url = "http://" + ServerIp + "/test/";
		lilDb = new HashMap<String, ArrayList<HashMap<String, String>>>();

	}

	/**
	 * Single ton pattern
	 * 
	 * @return the static refrence of the class
	 */
	public static DataConnector getInst(Context con) {
		if (inst == null)
			return inst = new DataConnector(con);
		return inst;
	}

	public String[] getGroupTypes() {
		return groupTypes;
	}

	public String[] getGameTypes() {
		return gameTypes;
	}

	/**
	 * Returns HashMap with companyName,join date. If the company is not
	 * distributor returns map of companyName and the other company from the
	 * list.
	 * 
	 * @return
	 */
	public HashMap<String, String> returnGameDistributorDeveloperInfo(
			String id_game) {
		HashMap<String, String> map = new HashMap<String, String>();
		JSONArray json = getArrayFromQuerryWithPostVariable("",
				Keys.GAMEPROCCOMPANY, id_game);
		if (json != null) {
			for (int i = 0; i < json.length(); i++) {
				try {
					int isDistributor = json.getJSONObject(i).getInt(
							Keys.GAMEisDistributor);
					int isDeveloper = json.getJSONObject(i).getInt(
							Keys.GAMEisDeveloper);
					if (isDistributor == 1) {
						map.put(Keys.GAMECompanyDistributor, json
								.getJSONObject(i).getString(Keys.CompanyName));
					} else if (isDeveloper == 1) {
						map.put(Keys.CompanyName, json.getJSONObject(i)
								.getString(Keys.CompanyName));
						map.put(Keys.CompanyFounded, json.getJSONObject(i)
								.getString(Keys.CompanyFounded));
					} else if (isDeveloper == 1 && isDistributor == 1) {
						map.put(Keys.CompanyName, json.getJSONObject(i)
								.getString(Keys.CompanyName));
						map.put(Keys.CompanyFounded, json.getJSONObject(i)
								.getString(Keys.CompanyFounded));
						map.put(Keys.GAMECompanyDistributor, json
								.getJSONObject(i).getString(Keys.CompanyName));
					}

				} catch (Exception e) {
					Log.e("Fetching GameCompanyInfo", "Error GameCompanyInfo"
							+ e);
				}
			}
		} else if (json == null) {
			map.put(Keys.GAMECompanyDistributor, "PlayNation");
			map.put(Keys.CompanyName, "PlayNation");
			map.put(Keys.CompanyFounded, "2011-12-21");
		}
		return map;
	}

	@SuppressLint("DefaultLocale")
	public void writeTempNewsTab(String id_game, String gameType) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(id_game, gameType,
				"0");

		if (json != null) {
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues temp = new ContentValues();
					temp.put(Keys.NEWSCOLID_NEWS, json.getJSONObject(i)
							.getString(Keys.NEWSCOLID_NEWS));
					temp.put(Keys.ID_GAME, id_game);
					temp.put(Keys.NEWSCOLNEWSTEXT, json.getJSONObject(i)
							.getString(Keys.NEWSCOLNEWSTEXT));
					temp.put(Keys.NEWSCOLINTROTEXT, json.getJSONObject(i)
							.getString(Keys.NEWSCOLINTROTEXT));
					temp.put(Keys.NEWSCOLPOSTINGTIME, json.getJSONObject(i)
							.getString(Keys.NEWSCOLPOSTINGTIME));
					temp.put(Keys.NEWSCOLHEADLINE, json.getJSONObject(i)
							.getString(Keys.NEWSCOLHEADLINE));
					temp.put(
							Keys.Author,
							json.getJSONObject(i).getString(Keys.FirstName)
									+ " "
									+ json.getJSONObject(i).getString(
											Keys.LastName));

					if (gameType.toLowerCase().equals("game")) {
						sql.insert(Keys.newsTempTable, null, temp);
					} else {
						sql.insert(Keys.companyTempTable, null, temp);
					}
				} catch (Exception e) {
					Log.e("Fetching writeTempNewsTab", "Error writeTempNewsTab"
							+ e);
				}
			}
			sql.close();
		}
	}

	public ArrayList<HashMap<String, String>> getTempNewsTab(String id,
			String gameType) {
		SQLiteDatabase sql = null;
		Cursor cursor = null;
		String selectQuery = "";
		if (gameType.equals("game")) {
			selectQuery = "Select * from " + Keys.newsTempTable
					+ " Where ID_GAME=" + id;
		} else {
			selectQuery = "Select * from " + Keys.companyTempTable
					+ " Where ID_GAME=" + id;
		}

		sql = getReadableDatabase();
		cursor = sql.rawQuery(selectQuery, null);
		if (cursor != null) {
			ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				do {
					HashMap<String, String> temp = new HashMap<String, String>();
					temp.put(Keys.NEWSCOLID_NEWS, cursor.getString(cursor
							.getColumnIndex(Keys.NEWSCOLID_NEWS)));
					temp.put(Keys.NEWSCOLNEWSTEXT, cursor.getString(cursor
							.getColumnIndex(Keys.NEWSCOLNEWSTEXT)));
					temp.put(Keys.NEWSCOLINTROTEXT, cursor.getString(cursor
							.getColumnIndex(Keys.NEWSCOLINTROTEXT)));
					temp.put(Keys.NEWSCOLPOSTINGTIME, cursor.getString(cursor
							.getColumnIndex(Keys.NEWSCOLPOSTINGTIME)));
					temp.put(Keys.NEWSCOLHEADLINE, cursor.getString(cursor
							.getColumnIndex(Keys.NEWSCOLHEADLINE)));
					temp.put(Keys.Author, cursor.getString(cursor
							.getColumnIndex(Keys.Author)));
					arrayList.add(temp);
				} while (cursor.moveToNext());
			}
			cursor.close();
			sql.close();
			return arrayList;
		}

		return null;
	}

	public ArrayList<HashMap<String, String>> getGameInfo(String id_game,
			String gameType) {
		JSONArray json = getArrayFromQuerryWithPostVariable("",
				Keys.GAMEPROCINFO, id_game);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		try {
			HashMap<String, String> map = new HashMap<String, String>();

			if (json != null) {
				map.put(Keys.GAMEPLATFORM,
						json.getJSONObject(0).getString(Keys.GAMEPLATFORM));
				map.put(Keys.GAMETYPENAME,
						json.getJSONObject(0).getString(Keys.GAMETYPENAME));
			} else {
				map.put(Keys.GAMEPLATFORM, "unknown");
				map.put(Keys.GAMETYPENAME, gameType);
			}
			HashMap<String, String> temp = returnGameDistributorDeveloperInfo(id_game);
			map.put(Keys.GAMECompanyDistributor,
					temp.get(Keys.GAMECompanyDistributor));
			map.put(Keys.CompanyName, temp.get(Keys.CompanyName));
			map.put(Keys.CompanyFounded, temp.get(Keys.CompanyFounded));
			list.add(map);
		} catch (Exception e) {
			Log.e("Fetching GetGameInfo", "Error GetGameInfo" + e);
		}
		// lilDb.put(Keys.GAMELILDBTABLENAME, list);
		return list;
	}

	/**
	 * @param groupTypes
	 *            The Set containing all the group Types to be changed into a
	 *            normal array
	 */
	private void convertGroupTypes(Set<String> groupTypes) {
		String[] items = new String[groupTypes.size() + 1];
		items[0] = "All";
		Iterator<String> itr = groupTypes.iterator();
		int i = 1;
		while (itr.hasNext()) {
			items[i] = itr.next();
			i++;
		}
		this.groupTypes = items;
	}

	/**
	 * @param gameTypes
	 *            The Set containing all the games Types to be changed into a
	 *            normal array
	 */
	public void convertGameTypes(Set<String> gameTypes) {
		String[] items = new String[gameTypes.size() + 1];
		items[0] = "All";
		Iterator<String> itr = gameTypes.iterator();
		int i = 1;
		while (itr.hasNext()) {
			items[i] = itr.next();
			i++;
		}
		this.gameTypes = items;
	}

	/**
	 * Takes the data out of the JSON response and adds it to a Map for holding
	 * until destroyed
	 * 
	 * @param jsonArray
	 *            the JSON response from the Database
	 * @throws JSONException
	 */
	public void addGames(JSONArray jsonArray) throws JSONException {
		SQLiteDatabase sql = this.getWritableDatabase();
		// ArrayList<HashMap<String, String>> arrayList = new
		// ArrayList<HashMap<String, String>>();
		Set<String> gameTypes = new HashSet<String>();
		for (int i = 0; i < jsonArray.length(); i++) {

			ContentValues temp = new ContentValues();
			temp.put(Keys.GAMENAME,
					jsonArray.getJSONObject(i).getString(Keys.GAMENAME));
			String gameType = jsonArray.getJSONObject(i).getString(
					Keys.GAMETYPE);
			temp.put(Keys.GAMETYPE, gameType);
			temp.put(Keys.GAMEDESC,
					jsonArray.getJSONObject(i).getString(Keys.GAMEDESC));
			temp.put(Keys.GAMEDATE,
					jsonArray.getJSONObject(i).getString(Keys.GAMEDATE));
			temp.put(Keys.RATING,
					jsonArray.getJSONObject(i).getString(Keys.RATING));
			temp.put(Keys.GAMEESRB,
					jsonArray.getJSONObject(i).getString(Keys.GAMEESRB));
			temp.put(Keys.GAMEURL,
					jsonArray.getJSONObject(i).getString(Keys.GAMEURL));
			temp.put(Keys.GAMEPLAYERSCOUNT, jsonArray.getJSONObject(i)
					.getString(Keys.GAMEPLAYERSCOUNT));
			String id_GAME = jsonArray.getJSONObject(i).getString(Keys.ID_GAME);
			temp.put(Keys.ID_GAME, id_GAME);

			HashMap<String, String> map = getGameInfo(id_GAME, gameType).get(0);
			temp.put(Keys.GAMETYPENAME, map.get(Keys.GAMETYPENAME));
			temp.put(Keys.GAMEPLATFORM, map.get(Keys.GAMEPLATFORM));
			temp.put(Keys.GAMECompanyDistributor,
					map.get(Keys.GAMECompanyDistributor));
			temp.put(Keys.CompanyFounded, map.get(Keys.CompanyFounded));
			temp.put(Keys.CompanyName, map.get(Keys.CompanyName));

			// temp.put(Keys.GAMETYPENAME, "");
			// temp.put(Keys.GAMEPLATFORM, "");
			// temp.put(Keys.GAMECompanyDistributor, "");
			// temp.put(Keys.CompanyFounded, "");
			// temp.put(Keys.CompanyName, "");
			// arrayList.add(temp);
			sql.insert(Keys.gamesTable, null, temp);
			gameTypes.add(jsonArray.getJSONObject(i).getString(Keys.GAMETYPE));
		}

		// lilDb.put(Keys.gamesTable, arrayList);
		sql.close();
		convertGameTypes(gameTypes);
	}

	/**
	 * Takes the data out of the JSON response and adds it to a DatabaseMAP for
	 * holding until destroyed
	 * 
	 * @param jsonArray
	 *            the JSON response from the Database
	 * @throws JSONException
	 */
	public void addComments(JSONArray jsonArray) throws JSONException {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < jsonArray.length(); i++) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put(Keys.ID_OWNER,
					jsonArray.getJSONObject(i).getString(Keys.ID_OWNER));
			temp.put(Keys.COMMENT,
					jsonArray.getJSONObject(i).getString(Keys.COMMENT));
			temp.put(Keys.WallMessage,
					jsonArray.getJSONObject(i).getString(Keys.WallMessage));
			temp.put(Keys.CommentTime,
					jsonArray.getJSONObject(i).getString(Keys.CommentTime));
			arrayList.add(temp);
		}
		if (!lilDb.containsKey(Keys.commentsTable))
			lilDb.put(Keys.commentsTable, arrayList);
		else
			lilDb.get(Keys.commentsTable).addAll(arrayList);
	}

	public void addGroups(JSONArray jsonArray) throws JSONException {
		SQLiteDatabase sql = this.getWritableDatabase();
		// ArrayList<HashMap<String, String>> arrayList = new
		// ArrayList<HashMap<String, String>>();
		Set<String> groupTypes = new HashSet<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			ContentValues temp = new ContentValues();
			temp.put(Keys.GROUPNAME,
					jsonArray.getJSONObject(i).getString(Keys.GROUPNAME));
			temp.put(Keys.GROUPTYPE,
					jsonArray.getJSONObject(i).getString(Keys.GROUPTYPE));
			temp.put(Keys.GROUPDESC,
					jsonArray.getJSONObject(i).getString(Keys.GROUPDESC));
			temp.put(Keys.GROUPTYPE2,
					jsonArray.getJSONObject(i).getString(Keys.GROUPTYPE2));

			// Changed so date and members should be
			temp.put(Keys.GroupMemberCount, jsonArray.getJSONObject(i)
					.getString(Keys.GroupMemberCount));

			temp.put(Keys.GROUPDATE, HelperClass.convertTime(Integer
					.parseInt(jsonArray.getJSONObject(i).getString(
							Keys.GROUPDATE)), new SimpleDateFormat(
					"EEEE,MMMM d,yyyy h:mm,a", Locale.getDefault())));
			temp.put(Keys.ID_GROUP,
					jsonArray.getJSONObject(i).getString(Keys.ID_GROUP));
			String creator = jsonArray.getJSONObject(i).getString(
					Keys.PLAYERNICKNAME);
			temp.put(Keys.GruopCreatorName, creator);
			// arrayList.add(temp);
			sql.insert(Keys.groupsTable, null, temp);
			groupTypes
					.add(jsonArray.getJSONObject(i).getString(Keys.GROUPTYPE));
		}
		sql.close();
		// lilDb.put(Keys.groupsTable, arrayList);
		convertGroupTypes(groupTypes);
	}

	public void addNews(JSONArray jsonArray) throws JSONException {
		SQLiteDatabase sql = this.getWritableDatabase();
		// ArrayList<HashMap<String, String>> arrayList = new
		// ArrayList<HashMap<String, String>>();
		for (int i = 0; i < jsonArray.length(); i++) {
			ContentValues temp = new ContentValues();
			temp.put(Keys.NEWSCOLID_NEWS,
					jsonArray.getJSONObject(i).getString(Keys.NEWSCOLID_NEWS));
			temp.put(Keys.NEWSCOLNEWSTEXT, jsonArray.getJSONObject(i)
					.getString(Keys.NEWSCOLNEWSTEXT));
			temp.put(Keys.NEWSCOLINTROTEXT, jsonArray.getJSONObject(i)
					.getString(Keys.NEWSCOLINTROTEXT));
			temp.put(Keys.NEWSCOLPOSTINGTIME, jsonArray.getJSONObject(i)
					.getString(Keys.NEWSCOLPOSTINGTIME));
			temp.put(Keys.NEWSCOLHEADLINE, jsonArray.getJSONObject(i)
					.getString(Keys.NEWSCOLHEADLINE));
			temp.put(
					Keys.Author,
					jsonArray.getJSONObject(i).getString(Keys.FirstName)
							+ " "
							+ jsonArray.getJSONObject(i).getString(
									Keys.LastName));
			// arrayList.add(temp);
			sql.insert(Keys.newsTable, null, temp);
		}
		// lilDb.put(Keys.newsTable, arrayList);
		sql.close();
	}

	public void jsonToArray(JSONArray jsonArray, String table)
			throws JSONException {
		if (jsonArray != null && table.equals(Keys.gamesTable)) {
			addGames(jsonArray);
		} else if (jsonArray != null && table.equals(Keys.commentsTable)) {
			addComments(jsonArray);
		} else if (jsonArray != null && table.equals(Keys.groupsTable)) {
			addGroups(jsonArray);
		} else if (jsonArray != null && table.equals(Keys.newsTable)) {
			addNews(jsonArray);
		} else if (jsonArray != null && table.equals(Keys.companyTable)) {
			addCompany(jsonArray);
		}
	}

	/**
	 * Gets the required Table from the DatabaseMAP
	 * 
	 * @param tableName
	 *            Table name we want to get from the DatabaseMAP
	 * @return Database table
	 */
	public ArrayList<HashMap<String, String>> getTable(String tableName,
			String sepateID) {
		if (lilDb.get(tableName) == null) {
			if (!checkDBTableExits(tableName)) {
				getQuerry(tableName);
			} else {
				// TODO
				SQLiteDatabase sql = null;
				Cursor cursor = null;
				String selectQuery = "";
				if (Keys.HomeWallTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + " WHERE "
							+ Keys.ID_OWNER + "=" + Keys.TEMPLAYERID
							+ " Order by " + Keys.WallPostingTime + " desc;";

				} else if (Keys.newsTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + " Order by "
							+ Keys.NEWSCOLPOSTINGTIME + " desc;";
				} else if (Keys.gamesTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + ";";
				} else if (Keys.groupsTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + ";";
				} else if (Keys.companyTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + ";";
				} else if (Keys.HomeMsgTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + "Where "
							+ Keys.ID_PLAYER + "=" + Keys.TEMPLAYERID + ";";
				} else if (Keys.HomeSubscriptionTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + ";";
				} else if (Keys.HomeEventTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName
							+ " Where ID_PLAYER=" + Keys.TEMPLAYERID + ";";
				} else if (Keys.HomeFriendsTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName
							+ " Where ID_OWNER=" + Keys.TEMPLAYERID + ";";
				} else if (Keys.HomeGamesTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName
							+ " Where ID_PLAYER=" + Keys.TEMPLAYERID + ";";
				} else if (Keys.HomeGroupTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName
							+ " Where ID_PLAYER=" + Keys.TEMPLAYERID + ";";
				} else if (Keys.HomeMsgRepliesTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + " Where "
							+ Keys.MessageID_CONVERSATION + "=" + sepateID
							+ " Order By MessageTime desc;";
				} else if (Keys.HomeWallRepliesTable.equals(tableName)) {
					selectQuery = "SELECT * FROM " + tableName + " Where "
							+ Keys.ID_WALLITEM + "=" + Keys.TEMPLAYERID + " "
							+ Keys.ID_OWNER + "=" + sepateID
							+ " Order By PostingTime desc;";
				}

				sql = getReadableDatabase();
				cursor = sql.rawQuery(selectQuery, null);
				if (cursor != null) {
					ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
					cursor.moveToFirst();
					if (!cursor.isAfterLast()) {
						do {
							if (tableName.equals(Keys.HomeWallTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.WallPosterDisplayName,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallPosterDisplayName))
												+ "");
								m.put(Keys.ID_WALLITEM,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_WALLITEM))
												+ "");
								m.put(Keys.ID_OWNER,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_OWNER))
												+ "");
								m.put(Keys.ItemType, cursor.getString(cursor
										.getColumnIndex(Keys.ItemType)));
								m.put(Keys.WallLastActivityTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallLastActivityTime)));
								m.put(Keys.WallMessage, cursor.getString(cursor
										.getColumnIndex(Keys.WallMessage)));
								m.put(Keys.WallOwnerType,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallOwnerType)));
								m.put(Keys.WallPostingTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallPostingTime)));
								list.add(m);
							} else if (tableName.equals(Keys.newsTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.NEWSCOLID_NEWS,
										cursor.getString(cursor
												.getColumnIndex(Keys.NEWSCOLID_NEWS)));
								m.put(Keys.NEWSCOLNEWSTEXT,
										cursor.getString(cursor
												.getColumnIndex(Keys.NEWSCOLNEWSTEXT)));
								m.put(Keys.NEWSCOLINTROTEXT,
										cursor.getString(cursor
												.getColumnIndex(Keys.NEWSCOLINTROTEXT)));
								m.put(Keys.NEWSCOLPOSTINGTIME,
										cursor.getString(cursor
												.getColumnIndex(Keys.NEWSCOLPOSTINGTIME)));
								m.put(Keys.NEWSCOLHEADLINE,
										cursor.getString(cursor
												.getColumnIndex(Keys.NEWSCOLHEADLINE)));
								m.put(Keys.Author, cursor.getString(cursor
										.getColumnIndex(Keys.Author)));
								list.add(m);
							} else if (tableName.equals(Keys.groupsTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.GROUPNAME, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPNAME)));
								m.put(Keys.GROUPTYPE, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPTYPE)));
								m.put(Keys.GROUPDESC, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPDESC)));
								m.put(Keys.GROUPTYPE2, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPTYPE2)));
								// Changed so date and members should be
								m.put(Keys.GroupMemberCount,
										cursor.getString(cursor
												.getColumnIndex(Keys.GroupMemberCount)));
								m.put(Keys.GROUPDATE, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPDATE)));
								m.put(Keys.ID_GROUP, cursor.getString(cursor
										.getColumnIndex(Keys.ID_GROUP)));

								m.put(Keys.GruopCreatorName,
										cursor.getString(cursor
												.getColumnIndex(Keys.GruopCreatorName)));
								list.add(m);
							} else if (tableName.equals(Keys.gamesTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.GAMENAME, cursor.getString(cursor
										.getColumnIndex(Keys.GAMENAME)));
								String gameType = cursor.getString(cursor
										.getColumnIndex(Keys.GAMETYPE));
								m.put(Keys.GAMETYPE, gameType);
								m.put(Keys.GAMEDESC, cursor.getString(cursor
										.getColumnIndex(Keys.GAMEDESC)));
								m.put(Keys.GAMEDATE, cursor.getString(cursor
										.getColumnIndex(Keys.GAMEDATE)));
								m.put(Keys.RATING, cursor.getString(cursor
										.getColumnIndex(Keys.RATING)));
								m.put(Keys.GAMEESRB, cursor.getString(cursor
										.getColumnIndex(Keys.GAMEESRB)));
								m.put(Keys.GAMEURL, cursor.getString(cursor
										.getColumnIndex(Keys.GAMEURL)));
								m.put(Keys.GAMEPLAYERSCOUNT,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMEPLAYERSCOUNT)));
								String id_GAME = cursor.getString(cursor
										.getColumnIndex(Keys.ID_GAME));
								m.put(Keys.ID_GAME, id_GAME);
								m.put(Keys.GAMETYPENAME,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMETYPENAME)));
								m.put(Keys.GAMEPLATFORM,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMEPLATFORM)));
								m.put(Keys.GAMECompanyDistributor,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMECompanyDistributor)));
								m.put(Keys.CompanyFounded,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyFounded)));
								m.put(Keys.CompanyName, cursor.getString(cursor
										.getColumnIndex(Keys.CompanyName)));

								list.add(m);
							} else if (tableName.equals(Keys.companyTable)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Keys.EventID_COMPANY,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventID_COMPANY)));
								map.put(Keys.CompanyName,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyName)));
								map.put(Keys.CompanyEmployees,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyEmployees)));
								map.put(Keys.CompanyImageURL,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyImageURL)));
								map.put(Keys.CompanyAddress,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyAddress)));
								map.put(Keys.CompanyDesc,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyDesc)));

								map.put(Keys.CompanyFounded,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyFounded)));
								map.put(Keys.CompanyURL,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyURL)));
								map.put(Keys.CompanyCreatedTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyCreatedTime)));
								map.put(Keys.CompanyOwnership,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyOwnership)));
								map.put(Keys.CompanyType,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyType)));
								map.put(Keys.CompanyNewsCount,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyNewsCount)));
								map.put(Keys.CompanyEventCount,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyEventCount)));
								map.put(Keys.CompanyGameCount,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanyGameCount)));
								map.put(Keys.CompanySocialRating,
										cursor.getString(cursor
												.getColumnIndex(Keys.CompanySocialRating)));
								list.add(map);
							} else if (tableName.equals(Keys.HomeMsgTable)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Keys.ID_MESSAGE,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_MESSAGE))
												+ "");
								map.put(Keys.MessageID_CONVERSATION,
										cursor.getInt(cursor
												.getColumnIndex(Keys.MessageID_CONVERSATION))
												+ "");
								map.put(Keys.PLAYERNICKNAME,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERNICKNAME)));
								map.put(Keys.PLAYERAVATAR,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERAVATAR)));

								map.put(Keys.MessageText,
										cursor.getString(cursor
												.getColumnIndex(Keys.MessageText)));
								map.put(Keys.MessageTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.MessageTime)));
								list.add(map);
							} else if (tableName
									.equals(Keys.HomeSubscriptionTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.ID_ITEM,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_ITEM))
												+ "");
								m.put(Keys.ID_OWNER,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_OWNER))
												+ "");
								m.put(Keys.ItemName, cursor.getString(cursor
										.getColumnIndex(Keys.ItemName)));
								m.put(Keys.ItemType, cursor.getString(cursor
										.getColumnIndex(Keys.ItemType)));
								m.put(Keys.SubscriptionTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.SubscriptionTime)));
								list.add(m);
							} else if (tableName.equals(Keys.HomeEventTable)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Keys.ID_EVENT,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_EVENT))
												+ "");
								map.put(Keys.EventID_COMPANY,
										cursor.getInt(cursor
												.getColumnIndex(Keys.EventID_COMPANY))
												+ "");
								map.put(Keys.ID_GAME,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_GAME))
												+ "");
								map.put(Keys.ID_GROUP,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_GROUP))
												+ "");
								map.put(Keys.EventID_TEAM,
										cursor.getInt(cursor
												.getColumnIndex(Keys.EventID_TEAM))
												+ "");
								map.put(Keys.EventIMAGEURL,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventIMAGEURL)));
								map.put(Keys.EventDescription,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventDescription)));
								map.put(Keys.EventDuration,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventDuration)));
								map.put(Keys.EventHeadline,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventHeadline)));
								map.put(Keys.EventTime, cursor.getString(cursor
										.getColumnIndex(Keys.EventTime)));
								map.put(Keys.EventLocation,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventLocation)));
								map.put(Keys.EventInviteLevel,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventInviteLevel)));
								map.put(Keys.EventIsPublic,
										cursor.getInt(cursor
												.getColumnIndex(Keys.EventIsPublic))
												+ "");
								map.put(Keys.EventType, cursor.getString(cursor
										.getColumnIndex(Keys.EventType)));
								map.put(Keys.EventIsExpired,
										cursor.getInt(cursor
												.getColumnIndex(Keys.EventIsExpired))
												+ "");
								list.add(map);
							} else if (tableName.equals(Keys.HomeFriendsTable)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Keys.ID_PLAYER, cursor.getString(cursor
										.getColumnIndex(Keys.ID_PLAYER)));
								map.put(Keys.ID_OWNER, cursor.getString(cursor
										.getColumnIndex(Keys.ID_OWNER)));
								map.put(Keys.CITY, cursor.getString(cursor
										.getColumnIndex(Keys.CITY)));
								map.put(Keys.COUNTRY, cursor.getString(cursor
										.getColumnIndex(Keys.COUNTRY)));
								map.put(Keys.PLAYERNICKNAME,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERNICKNAME)));
								map.put(Keys.Email, cursor.getString(cursor
										.getColumnIndex(Keys.Email)));
								map.put(Keys.PLAYERAVATAR,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERAVATAR)));
								map.put(Keys.FirstName, cursor.getString(cursor
										.getColumnIndex(Keys.FirstName)));
								map.put(Keys.LastName, cursor.getString(cursor
										.getColumnIndex(Keys.LastName)));

								map.put(Keys.Age, cursor.getString(cursor
										.getColumnIndex(Keys.Age)));

								list.add(map);
							} else if (tableName.equals(Keys.HomeGamesTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.ID_GAME,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_GAME))
												+ "");
								m.put(Keys.GameComments,
										cursor.getString(cursor
												.getColumnIndex(Keys.GameComments)));
								m.put(Keys.GAMENAME, cursor.getString(cursor
										.getColumnIndex(Keys.GAMENAME)));
								m.put(Keys.GAMEDESC,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMEDESC))
												+ "");

								m.put(Keys.GameID_GAMETYPE,
										cursor.getInt(cursor
												.getColumnIndex(Keys.GameID_GAMETYPE))
												+ "");
								m.put(Keys.GAMETYPE,
										cursor.getString(cursor
												.getColumnIndex(Keys.GAMETYPE))
												+ "");
								m.put(Keys.GameisPlaying,
										cursor.getInt(cursor
												.getColumnIndex(Keys.GameisPlaying))
												+ "");
								m.put(Keys.GamesisSubscribed,
										cursor.getInt(cursor
												.getColumnIndex(Keys.GamesisSubscribed))
												+ "");
								m.put(Keys.GamePostCount,
										cursor.getInt(cursor
												.getColumnIndex(Keys.GamePostCount))
												+ "");
								m.put(Keys.GamesSubscriptionTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.GamesSubscriptionTime)));

								list.add(m);
							} else if (tableName.equals(Keys.HomeGroupTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.ID_GROUP,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_GROUP))
												+ "");
								m.put(Keys.ID_PLAYER,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_PLAYER))
												+ "");
								m.put(Keys.GROUPNAME, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPNAME)));
								m.put(Keys.GROUPDESC, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPDESC)));
								m.put(Keys.GROUPTYPE, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPTYPE)));
								m.put(Keys.GROUPTYPE2, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPTYPE2)));
								m.put(Keys.GAMENAME, cursor.getString(cursor
										.getColumnIndex(Keys.GAMENAME)));
								m.put(Keys.GroupMemberCount,
										cursor.getString(cursor
												.getColumnIndex(Keys.GroupMemberCount)));
								m.put(Keys.EventIMAGEURL,
										cursor.getString(cursor
												.getColumnIndex(Keys.EventIMAGEURL)));
								m.put(Keys.GROUPDATE, cursor.getString(cursor
										.getColumnIndex(Keys.GROUPDATE)));
								m.put(Keys.GruopCreatorName,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERNICKNAME)));

								list.add(m);
							} else if (tableName
									.equals(Keys.HomeWallRepliesTable)) {
								HashMap<String, String> m = new HashMap<String, String>();
								m.put(Keys.WallPosterDisplayName,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallPosterDisplayName))
												+ "");
								m.put(Keys.ID_ORGOWNER,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_ORGOWNER))
												+ "");
								m.put(Keys.ID_WALLITEM,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_WALLITEM))
												+ "");
								m.put(Keys.PLAYERAVATAR,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERAVATAR))
												+ "");
								m.put(Keys.WallLastActivityTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallLastActivityTime)));
								m.put(Keys.WallMessage, cursor.getString(cursor
										.getColumnIndex(Keys.WallMessage)));
								m.put(Keys.WallOwnerType,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallOwnerType)));
								m.put(Keys.WallPostingTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.WallPostingTime)));

								list.add(m);
							} else if (tableName
									.equals(Keys.HomeMsgRepliesTable)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put(Keys.ID_MESSAGE,
										cursor.getInt(cursor
												.getColumnIndex(Keys.ID_MESSAGE))
												+ "");
								map.put(Keys.MessageID_CONVERSATION,
										cursor.getInt(cursor
												.getColumnIndex(Keys.MessageID_CONVERSATION))
												+ "");
								map.put(Keys.PLAYERNICKNAME,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERNICKNAME)));
								map.put(Keys.PLAYERAVATAR,
										cursor.getString(cursor
												.getColumnIndex(Keys.PLAYERAVATAR)));

								map.put(Keys.MessageText,
										cursor.getString(cursor
												.getColumnIndex(Keys.MessageText)));
								map.put(Keys.MessageTime,
										cursor.getString(cursor
												.getColumnIndex(Keys.MessageTime)));

								list.add(map);
							}

						} while (cursor.moveToNext());
					}
					cursor.close();
					sql.close();
					return list;
				}
			}
		}
		return lilDb.get(tableName);
	}

	/**
	 * @param tableName
	 *            the tableName to check
	 * @return if the databaseMAP contains the selected table
	 */
	public boolean checkTable(String tableName) {
		return lilDb.containsKey(tableName);
	}

	public String getScriptString(String tableName) {

		if (tableName.equals(Keys.commentsTable))
			return "getComments.php";
		if (tableName.equals(Keys.SearchGroupTable)
				|| tableName.equals(Keys.SearchEventTable)
				|| tableName.equals(Keys.SearchGameTable)
				|| tableName.equals(Keys.SearchFriendsTable)
				|| tableName.equals(Keys.SearchSubscriptionTable))
			return "getSearching.php";
		if (tableName.equals(Keys.gamesubNewsTAB)
				|| tableName.equals(Keys.companysubNewsTAB))
			return "getTabNews.php";
		else {
			return "getItem.php";
		}
		// if(tableName.equals(Keys.playersTable)) temp= "getPlayers.php";

	}

	public Bitmap getPicture() throws JSONException {
		InputStream is = null;
		String result = "";
		String url = "http://192.168.1.10:1337/test/getPicture.php";
		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("DataConnector ",
					"getPic()  Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();

		} catch (Exception e) {
			Log.e("DataConnector",
					"getPic() Error converting result " + e.toString());
		}

		// jArray = new JSONObj7ect(result);

		String mThumbnail = result;// jArray.getString(0);
		byte[] decodedString = Base64.decode(mThumbnail, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return decodedByte;
		// ImageView mReportImage = (ImageView) findViewById(R.id.imageView1);
		// mReportImage.setImageBitmap(decodedByte);
	}

	public void getQuerry(String tableName) {
		String result = "";
		if (!lilDb.containsKey(tableName)) {
			String temp = url;
			url += getScriptString(tableName);
			JSONArray jArray = null;
			// http post
			try {
				httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

			} catch (Exception e) {
				Log.e("DataConnector ",
						" getQuerry() Error in http connection " + e.toString());
			}

			// convert response to string
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();

			} catch (Exception e) {
				Log.e("DataConnector ", "getQuerry() Error converting result "
						+ e.toString());
			}

			try {
				jArray = new JSONArray(result);
				jsonToArray(jArray, tableName);
			} catch (JSONException e) {
				Log.e("DataConnector " + tableName + " ", "Error parsing data "
						+ e.toString());
			}
			url = temp;
		}
		// return null;
	}

	public void getQuerryWithPostVariable(String tableName,
			HashMap<String, String> data) {
		String result = "";

		String temp = url;
		url += getScriptString(tableName);
		JSONArray jArray = null;
		// http post
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(initializeData(
					Keys.gamesID, data)));
			Log.e("log_DataConn Querry+Post",
					"ownertype " + data.get(Keys.OWNERTYPE));
			Log.e("log_DataConn Querry+Post",
					"owner id " + data.get(Keys.ID_OWNER));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("DataConnector ", "getWithPost() Error in http connection "
					+ e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("log.DataConnector getWithPost()", "BFFREAD:" + result + "  ");

		} catch (Exception e) {
			Log.e("DataConnector", "Error converting result " + e.toString());
		}

		try {
			jArray = new JSONArray(result);
			jsonToArray(jArray, tableName);
		} catch (JSONException e) {
			Log.e("DataConnector getWithPost()" + tableName + " ",
					"Error parsing data " + e.toString());
		}
		url = temp;
	}

	public JSONArray getArrayFromQuerryWithPostVariable(String id,
			String tableName, String wallItem) {
		String result = "";
		String temp = url;
		url += getScriptString(tableName);
		JSONArray jArray = null;
		// http post
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			url = temp;
			pairs.add(new BasicNameValuePair(Keys.POSTID_PLAYER, id));
			pairs.add(new BasicNameValuePair(Keys.POSTTableName, tableName));
			pairs.add(new BasicNameValuePair(Keys.POSTWallItem, wallItem));

			httppost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag HTML Conn",
					"Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("DataConnector getWithPost2() ", "Error converting result "
					+ e.toString());
		}

		// parse json data
		try {
			if (result != null) {
				jArray = new JSONArray(result);
				jsonToArray(jArray, tableName);
				return jArray;
			}

		} catch (JSONException e) {
			Log.e("log_tag DB Parsing", "Error parsing data " + e.toString());
		}

		return null;
	}

	public ArrayList<NameValuePair> initializeData(int tableID,
			HashMap<String, String> data) {
		switch (tableID) {
		case Keys.commentsID:
			ArrayList<NameValuePair> comment = new ArrayList<NameValuePair>();
			comment.add(new BasicNameValuePair(Keys.USERNAME, data
					.get(Keys.USERNAME)));
			comment.add(new BasicNameValuePair(Keys.COMMENT, data
					.get(Keys.COMMENT)));
			lilDb.get(Keys.commentsTable).add(data);
			return comment;
		case Keys.replysID:
			ArrayList<NameValuePair> reply = new ArrayList<NameValuePair>();
			reply.add(new BasicNameValuePair(Keys.USERNAME, data
					.get(Keys.USERNAME)));
			reply.add(new BasicNameValuePair(Keys.COMMENT, data
					.get(Keys.COMMENT)));
			return reply;
		case Keys.gamesID:
			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair(Keys.ID_OWNER, data
					.get(Keys.ID_OWNER)));
			post.add(new BasicNameValuePair(Keys.OWNERTYPE, data
					.get(Keys.OWNERTYPE)));
			// lilDb.get(Keys.commentsTable).add(data);
			return post;

		}
		return null;
	}

	public String returnUnserializedText(String text) {
		String result = "";
		SerializedPhpParser spp = new SerializedPhpParser(text);
		Object obj = spp.parse();
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) obj;
		Set<Entry<Object, Object>> set = map.entrySet();
		Iterator<Entry<Object, Object>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry<Object, Object> ent = (Entry<Object, Object>) itr.next();
			if (ent.getKey().toString().equals("content")) {
				result = ent.getValue().toString();
			}

		}
		return result;
	}

	// -------------------------------------------------
	private HashMap<String, String> mPlayer;

	// -------------------------------------------------------------------

	@SuppressLint("SimpleDateFormat")
	public void addCompany(JSONArray jsonArray) throws JSONException {
		SQLiteDatabase sql = this.getWritableDatabase();
		// ArrayList<HashMap<String, String>> arrayQueryValues = new
		// ArrayList<HashMap<String, String>>();

		// // Print the data to the console
		if (jsonArray != null)
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					ContentValues map = new ContentValues();
					map.put(Keys.EventID_COMPANY, jsonArray.getJSONObject(i)
							.getInt(Keys.EventID_COMPANY) + "");
					map.put(Keys.CompanyName, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyName));
					map.put(Keys.CompanyEmployees, jsonArray.getJSONObject(i)
							.getInt(Keys.CompanyEmployees) + "");
					map.put(Keys.CompanyImageURL, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyImageURL));
					map.put(Keys.CompanyAddress, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyAddress));
					map.put(Keys.CompanyDesc, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyDesc));
					String[] foundYear = jsonArray.getJSONObject(i)
							.getString(Keys.CompanyFounded).split("-");
					map.put(Keys.CompanyFounded, foundYear[0]);
					map.put(Keys.CompanyURL, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyURL));
					map.put(Keys.CompanyCreatedTime, HelperClass.convertTime(
							Integer.parseInt(jsonArray.getJSONObject(i)
									.getString(Keys.CompanyCreatedTime)),
							new SimpleDateFormat("dd/MM/yyyy")));
					map.put(Keys.CompanyOwnership, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyOwnership));
					map.put(Keys.CompanyType, jsonArray.getJSONObject(i)
							.getString(Keys.CompanyType));
					map.put(Keys.CompanyNewsCount, jsonArray.getJSONObject(i)
							.getInt(Keys.CompanyNewsCount) + "");
					map.put(Keys.CompanyEventCount, jsonArray.getJSONObject(i)
							.getInt(Keys.CompanyEventCount) + "");
					map.put(Keys.CompanyGameCount, jsonArray.getJSONObject(i)
							.getInt(Keys.CompanyGameCount) + "");
					map.put(Keys.CompanySocialRating,
							jsonArray.getJSONObject(i).getString(
									Keys.CompanySocialRating));

					// arrayQueryValues.add(map);
					sql.insert(Keys.companyTable, null, map);
				} catch (Exception e) {
					Log.e("Fetching Company", "Error Company" + e);
				}
			}
		// lilDb.put(Keys.companyTable, arrayQueryValues);
		sql.close();
	}

	public void queryPlayerEvents(String playerID, Context v) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeEventTable, "0");
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {

					ContentValues map = new ContentValues();
					map.put(Keys.ID_EVENT,
							json.getJSONObject(i).getInt(Keys.ID_EVENT) + "");
					map.put(Keys.EventID_COMPANY,
							json.getJSONObject(i).getInt(Keys.EventID_COMPANY)
									+ "");
					map.put(Keys.ID_GAME,
							json.getJSONObject(i).getInt(Keys.ID_GAME) + "");
					map.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getInt(Keys.ID_PLAYER) + "");
					map.put(Keys.ID_GROUP,
							json.getJSONObject(i).getInt(Keys.ID_GROUP) + "");
					map.put(Keys.EventID_TEAM,
							json.getJSONObject(i).getInt(Keys.EventID_TEAM)
									+ "");
					map.put(Keys.EventIMAGEURL, json.getJSONObject(i)
							.getString(Keys.EventIMAGEURL));
					map.put(Keys.EventDescription, json.getJSONObject(i)
							.getString(Keys.EventDescription));
					map.put(Keys.EventDuration,
							HelperClass.durationConverter(json.getJSONObject(i)
									.getString(Keys.EventDuration) + "", v));
					map.put(Keys.EventHeadline, json.getJSONObject(i)
							.getString(Keys.EventHeadline));
					map.put(Keys.EventTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.EventTime)), dataTemplate));
					map.put(Keys.EventLocation, json.getJSONObject(i)
							.getString(Keys.EventLocation));
					map.put(Keys.EventInviteLevel, json.getJSONObject(i)
							.getString(Keys.EventInviteLevel));
					map.put(Keys.EventIsPublic, returnEventPrivacy(json
							.getJSONObject(i).getInt(Keys.EventIsPublic)));
					map.put(Keys.EventType,
							json.getJSONObject(i).getString(Keys.EventType));
					map.put(Keys.EventIsExpired,
							json.getJSONObject(i).getInt(Keys.EventIsExpired)
									+ "");

					// if (!arrayChildren.contains(map))
					// arrayChildren.add(map);
					sql.insert(Keys.HomeEventTable, null, map);
				} catch (Exception e) {
					Log.e("Fetching Events", "Error Events" + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeEventTable, arrayChildren);
	}

	public void queryPlayerFriends(String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeFriendsTable, "0");
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		if (json != null) {
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues map = new ContentValues();
					map.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getString(Keys.ID_PLAYER));
					map.put(Keys.ID_OWNER,
							json.getJSONObject(i).getString(Keys.ID_OWNER));
					map.put(Keys.CITY,
							json.getJSONObject(i).getString(Keys.CITY));
					map.put(Keys.COUNTRY,
							json.getJSONObject(i).getString(Keys.COUNTRY));
					map.put(Keys.PLAYERNICKNAME, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					map.put(Keys.Email,
							json.getJSONObject(i).getString(Keys.Email));
					map.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR));
					map.put(Keys.FirstName,
							json.getJSONObject(i).getString(Keys.FirstName));
					map.put(Keys.LastName,
							json.getJSONObject(i).getString(Keys.LastName));

					map.put(Keys.Age, json.getJSONObject(i).getString(Keys.Age));
					// arrayChildren.add(map);
					sql.insert(Keys.HomeFriendsTable, null, map);
				} catch (Exception e) {
					Log.e("Fetching Friends", "Error Friends" + e);
				}
			}
		}
		sql.close();
		// lilDb.put(Keys.HomeFriendsTable, arrayChildren);
	}

	public void queryPlayerGames(String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeGamesTable, "0");
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues m = new ContentValues();
					m.put(Keys.ID_GAME,
							json.getJSONObject(i).getInt(Keys.ID_GAME) + "");
					m.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getInt(Keys.ID_PLAYER) + "");
					m.put(Keys.GameComments,
							json.getJSONObject(i).getString(Keys.GameComments));
					m.put(Keys.GAMENAME,
							json.getJSONObject(i).getString(Keys.GAMENAME));
					m.put(Keys.GAMEDESC,
							json.getJSONObject(i).getString(Keys.GAMEDESC) + "");

					m.put(Keys.GameID_GAMETYPE,
							json.getJSONObject(i).getInt(Keys.GameID_GAMETYPE)
									+ "");
					m.put(Keys.GAMETYPE,
							json.getJSONObject(i).getString(Keys.GAMETYPE) + "");
					m.put(Keys.GameisPlaying,
							json.getJSONObject(i).getInt(Keys.GameisPlaying)
									+ "");
					m.put(Keys.GamesisSubscribed,
							json.getJSONObject(i)
									.getInt(Keys.GamesisSubscribed) + "");
					m.put(Keys.GamePostCount,
							json.getJSONObject(i).getInt(Keys.GamePostCount)
									+ "");
					m.put(Keys.GamesSubscriptionTime, json.getJSONObject(i)
							.getString(Keys.GamesSubscriptionTime));

					sql.insert(Keys.HomeGamesTable, null, m);
					// if (!arrayChildren.contains(m))
					// arrayChildren.add(m);
				} catch (Exception e) {
					Log.e("Fetching Games", "Error Games" + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeGamesTable, arrayChildren);
	}

	public void queryPlayerGroup(String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeGroupTable, "0");
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues m = new ContentValues();
					m.put(Keys.ID_GROUP,
							json.getJSONObject(i).getInt(Keys.ID_GROUP) + "");
					m.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getInt(Keys.ID_PLAYER) + "");
					m.put(Keys.GROUPNAME,
							json.getJSONObject(i).getString(Keys.GROUPNAME));
					m.put(Keys.GROUPDESC,
							json.getJSONObject(i).getString(Keys.GROUPDESC));
					m.put(Keys.GROUPTYPE,
							json.getJSONObject(i).getString(Keys.GROUPTYPE));
					m.put(Keys.GROUPTYPE2,
							json.getJSONObject(i).getString(Keys.GROUPTYPE2));
					m.put(Keys.GAMENAME,
							json.getJSONObject(i).getString(Keys.GAMENAME));
					m.put(Keys.GroupMemberCount, json.getJSONObject(i)
							.getString(Keys.GroupMemberCount));
					m.put(Keys.EventIMAGEURL,
							json.getJSONObject(i).getString(Keys.EventIMAGEURL));
					m.put(Keys.GROUPDATE, HelperClass.convertTime(Integer
							.parseInt(json.getJSONObject(i).getString(
									Keys.GROUPDATE)), new SimpleDateFormat(
							"dd/MM/yyyy", Locale.getDefault())));
					m.put(Keys.GruopCreatorName, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					sql.insert(Keys.HomeGroupTable, null, m);
					// arrayChildren.add(m);
				} catch (Exception e) {
					Log.e("Fetching Group", "Error Group " + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeGroupTable, arrayChildren);
	}

	public void queryPlayerMessages(String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeMsgTable, "0");
		// ArrayList<HashMap<String, String>> list = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues map = new ContentValues();

					map.put(Keys.ID_MESSAGE,
							json.getJSONObject(i).getInt(Keys.ID_MESSAGE) + "");
					map.put(Keys.MessageID_CONVERSATION, json.getJSONObject(i)
							.getInt(Keys.MessageID_CONVERSATION) + "");
					map.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getInt(Keys.ID_PLAYER) + "");
					map.put(Keys.PLAYERNICKNAME, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					map.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR));

					map.put(Keys.MessageText, returnUnserializedText(json
							.getJSONObject(i).getString(Keys.MessageText)));
					map.put(Keys.MessageTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.MessageTime)), dataTemplate));

					// list.add(map);
					sql.insert(Keys.HomeMsgTable, null, map);
				} catch (Exception e) {
					Log.e("Fetching Msg", "Error Msg" + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeMsgTable, list);
	}

	public void queryPlayerSubscription(String playerID) {
		// TODO
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeSubscriptionTable, "0");
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues m = new ContentValues();
					m.put(Keys.ID_ITEM,
							json.getJSONObject(i).getInt(Keys.ID_ITEM) + "");
					m.put(Keys.ID_OWNER,
							json.getJSONObject(i).getInt(Keys.ID_OWNER) + "");
					m.put(Keys.ItemName,
							json.getJSONObject(i).getString(Keys.ItemName));
					m.put(Keys.ItemType,
							json.getJSONObject(i).getString(Keys.ItemType));
					m.put(Keys.SubscriptionTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.SubscriptionTime)), dataTemplate));

					// if (!arrayChildren.contains(m))
					// arrayChildren.add(m);
					sql.insert(Keys.HomeSubscriptionTable, null, m);
				} catch (Exception e) {
					Log.e("Fetching Subscription", "Error Subscription " + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeSubscriptionTable, arrayChildren);
	}

	public boolean checkDBTableExits(String tableName) {
		String selectQuery = "SELECT  * FROM " + tableName;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		cursor.close();
		return false;
	}

	public void queryPlayerWall(String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();

		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeWallTable, "0");
		// ArrayList<HashMap<String, String>> list = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues m = new ContentValues();

					// HashMap<String, String> m = new HashMap<String,
					// String>();
					m.put(Keys.WallPosterDisplayName, json.getJSONObject(i)
							.getString(Keys.WallPosterDisplayName) + "");
					m.put(Keys.ID_WALLITEM,
							json.getJSONObject(i).getInt(Keys.ID_WALLITEM) + "");
					m.put(Keys.ID_OWNER,
							json.getJSONObject(i).getInt(Keys.ID_OWNER) + "");
					m.put(Keys.ItemType,
							json.getJSONObject(i).getString(Keys.ItemType));
					m.put(Keys.WallLastActivityTime, json.getJSONObject(i)
							.getString(Keys.WallLastActivityTime));
					m.put(Keys.WallMessage, returnUnserializedText(json
							.getJSONObject(i).getString(Keys.WallMessage)));
					m.put(Keys.WallOwnerType,
							json.getJSONObject(i).getString(Keys.WallOwnerType));
					m.put(Keys.WallPostingTime, json.getJSONObject(i)
							.getString(Keys.WallPostingTime));
					sql.insert(Keys.HomeWallTable, null, m);
					// list.add(m);
				} catch (Exception e) {
					Log.e("HomeWallFrag ", " querryPlayerWall() Error " + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.TableHomeWall, list);
	}

	public void queryPlayerWallReplices(String wallitem, String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeWallRepliesTable, wallitem);
		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();

		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues m = new ContentValues();
					m.put(Keys.WallPosterDisplayName, json.getJSONObject(i)
							.getString(Keys.WallPosterDisplayName) + "");
					m.put(Keys.ID_WALLITEM,
							json.getJSONObject(i).getInt(Keys.ID_WALLITEM) + "");
					m.put(Keys.ID_ORGOWNER,
							json.getJSONObject(i).getInt(Keys.ID_ORGOWNER) + "");
					m.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR)
									+ "");
					m.put(Keys.WallLastActivityTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.WallLastActivityTime)), dataTemplate));
					m.put(Keys.WallMessage,
							json.getJSONObject(i).getString(Keys.WallMessage));
					m.put(Keys.WallOwnerType,
							json.getJSONObject(i).getString(Keys.WallOwnerType));
					m.put(Keys.WallPostingTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.WallPostingTime)), dataTemplate));

					sql.insert(Keys.HomeWallRepliesTable, null, m);
					// arrayChildren.add(m);
				} catch (Exception e) {
					Log.e("Fetching Wall Replies",
							"Fetching WallReplies: Error" + e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeWallRepliesTable, arrayChildren);
	}

	public void queryPlayerMSGReplices(String wallitem, String playerID) {
		SQLiteDatabase sql = this.getWritableDatabase();
		JSONArray json = getArrayFromQuerryWithPostVariable(playerID,
				Keys.HomeMsgRepliesTable, wallitem);

		// ArrayList<HashMap<String, String>> arrayChildren = new
		// ArrayList<HashMap<String, String>>();
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					ContentValues map = new ContentValues();
					map.put(Keys.ID_MESSAGE,
							json.getJSONObject(i).getInt(Keys.ID_MESSAGE) + "");
					map.put(Keys.MessageID_CONVERSATION, json.getJSONObject(i)
							.getInt(Keys.MessageID_CONVERSATION) + "");
					map.put(Keys.PLAYERNICKNAME, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					map.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR));

					map.put(Keys.MessageText, returnUnserializedText(json
							.getJSONObject(i).getString(Keys.MessageText)));
					map.put(Keys.MessageTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.MessageTime)), dataTemplate));
					sql.insert(Keys.HomeMsgRepliesTable, null, map);
					// arrayChildren.add(map);
				} catch (Exception e) {
					Log.e("Fetching MSG Replies", "Fetching MSGReplies Error"
							+ e);
				}
			}
		sql.close();
		// lilDb.put(Keys.HomeMsgRepliesTable, arrayChildren);
	}

	// -----------------------------------------------------------------

	public void queryPlayerInfo(String playerID) {

		json = getArrayFromQuerryWithPostVariable(playerID, Keys.PlayerTable,
				"0");

		// Strong limit(10) of DB for fetching big table
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getString(Keys.ID_PLAYER));
					map.put(Keys.CITY,
							json.getJSONObject(i).getString(Keys.CITY));
					map.put(Keys.COUNTRY,
							json.getJSONObject(i).getString(Keys.COUNTRY));
					map.put(Keys.PLAYERNICKNAME, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					map.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR));
					map.put(Keys.FirstName,
							json.getJSONObject(i).getString(Keys.FirstName));
					map.put(Keys.LastName,
							json.getJSONObject(i).getString(Keys.LastName));
					map.put(Keys.LastName,
							json.getJSONObject(i).getString(Keys.LastName));
					map.put(Keys.Age, json.getJSONObject(i).getString(Keys.Age));
					map.put(Keys.Email,
							json.getJSONObject(i).getString(Keys.Email));
					setPlayer(map);
					arrayChildren.add(map);
				} catch (Exception e) {
					Log.e("Fetching Info", "Error " + e);
				}
			}
		lilDb.put(Keys.PlayerTable, arrayChildren);
	}

	public View populatePlayerGeneralInfo(View v, String nameT) {
		HashMap<String, String> currentPlayer = getPlayer();

		if (v != null) {
			TextView txPlName = (TextView) v.findViewById(R.id.txPlName);
			TextView txPlNick = (TextView) v.findViewById(R.id.txPlNick);
			TextView txPlAge = (TextView) v.findViewById(R.id.txPlAge);
			TextView txPlCountry = (TextView) v.findViewById(R.id.txPlCountry);

			// TextView txlabel = (TextView) v.findViewById(R.id.txWhereLabel);
			// txlabel.setText(nameT);

			if (txPlName != null)
				txPlName.setText("Name : " + currentPlayer.get(Keys.FirstName)
						+ " , " + currentPlayer.get(Keys.LastName));

			if (txPlNick != null)
				txPlNick.setText("Nick : "
						+ currentPlayer.get(Keys.PLAYERNICKNAME));

			if (txPlAge != null)
				txPlAge.setText("Age : "
						+ HelperClass.convertToAge(currentPlayer.get(Keys.Age)));

			if (txPlCountry != null)
				txPlCountry.setText("Country: "
						+ currentPlayer.get(Keys.COUNTRY));
		}
		return v;
	}

	// MAYNOT BE NEEDED CURRENTLY NOT USED
	public ArrayList<HashMap<String, String>> queryPlayerGroupSearch(
			CharSequence search) {
		searchArray = new ArrayList<HashMap<String, String>>();

		json = getArrayFromQuerryWithPostVariable(Keys.TEMPLAYERID,
				Keys.SearchGroupTable, search.toString());

		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> m = new HashMap<String, String>();
					m.put(Keys.ID_GROUP,
							json.getJSONObject(i).getInt(Keys.ID_GROUP) + "");
					m.put(Keys.GROUPNAME,
							json.getJSONObject(i).getString(Keys.GROUPNAME));
					m.put(Keys.GROUPDESC,
							json.getJSONObject(i).getString(Keys.GROUPDESC));
					m.put(Keys.GROUPTYPE,
							json.getJSONObject(i).getString(Keys.GROUPTYPE));
					m.put(Keys.GROUPTYPE2,
							json.getJSONObject(i).getString(Keys.GROUPTYPE2));
					m.put(Keys.GAMENAME,
							json.getJSONObject(i).getString(Keys.GAMENAME));
					m.put(Keys.GroupMemberCount, json.getJSONObject(i)
							.getString(Keys.GroupMemberCount));
					m.put(Keys.EventIMAGEURL,
							json.getJSONObject(i).getString(Keys.EventIMAGEURL));
					m.put(Keys.GROUPDATE, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.GROUPDATE)), dataTemplate));

					searchArray.add(m);
				} catch (Exception e) {
					Log.e("Fetching Group Search", "Error Group Search" + e);
				}
			}
		return searchArray;
	}

	// MAYNOT BE NEEDED CURRENTLY NOT USED
	public ArrayList<HashMap<String, String>> queryPlayerEventSearch(
			CharSequence search, Context context) {
		searchArray = new ArrayList<HashMap<String, String>>();

		json = getArrayFromQuerryWithPostVariable(Keys.TEMPLAYERID,
				Keys.SearchEventTable, search.toString());

		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Keys.ID_EVENT,
							json.getJSONObject(i).getInt(Keys.ID_EVENT) + "");
					map.put(Keys.EventID_COMPANY,
							json.getJSONObject(i).getInt(Keys.EventID_COMPANY)
									+ "");
					map.put(Keys.ID_GAME,
							json.getJSONObject(i).getInt(Keys.ID_GAME) + "");
					map.put(Keys.ID_GROUP,
							json.getJSONObject(i).getInt(Keys.ID_GROUP) + "");
					map.put(Keys.EventID_TEAM,
							json.getJSONObject(i).getInt(Keys.EventID_TEAM)
									+ "");
					map.put(Keys.EventIMAGEURL, json.getJSONObject(i)
							.getString(Keys.EventIMAGEURL));
					map.put(Keys.EventDescription, json.getJSONObject(i)
							.getString(Keys.EventDescription));
					map.put(Keys.EventDuration, HelperClass.durationConverter(
							json.getJSONObject(i).getString(Keys.EventDuration)
									+ "", context));
					map.put(Keys.EventHeadline, json.getJSONObject(i)
							.getString(Keys.EventHeadline));
					map.put(Keys.EventTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.EventTime)), dataTemplate));
					map.put(Keys.EventLocation, json.getJSONObject(i)
							.getString(Keys.EventLocation));
					map.put(Keys.EventIsExpired,
							json.getJSONObject(i).getInt(Keys.EventIsExpired)
									+ "");

					searchArray.add(map);
				} catch (Exception e) {
					Log.e("Fetching Event Search", "Error Event Search" + e);
				}
			}
		return searchArray;
	}

	// MAYNOT BE NEEDED CURRENTLY NOT USED
	public ArrayList<HashMap<String, String>> queryPlayerGameSearch(
			CharSequence search, Context context) {
		searchArray = new ArrayList<HashMap<String, String>>();

		json = getArrayFromQuerryWithPostVariable(Keys.TEMPLAYERID,
				Keys.SearchGameTable, search.toString());

		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> m = new HashMap<String, String>();
					m.put(Keys.ID_GAME,
							json.getJSONObject(i).getInt(Keys.ID_GAME) + "");
					m.put(Keys.GameComments,
							json.getJSONObject(i).getString(Keys.GameComments));
					m.put(Keys.GAMENAME,
							json.getJSONObject(i).getString(Keys.GAMENAME));
					m.put(Keys.GAMEDESC,
							json.getJSONObject(i).getString(Keys.GAMEDESC) + "");
					m.put(Keys.GameID_GAMETYPE, json.getJSONObject(i)
							.getString(Keys.GameID_GAMETYPE) + "");
					m.put(Keys.GAMETYPE,
							json.getJSONObject(i).getInt(Keys.GAMETYPE) + "");
					m.put(Keys.GameisPlaying,
							json.getJSONObject(i).getInt(Keys.GameisPlaying)
									+ "");
					m.put(Keys.GamesisSubscribed,
							json.getJSONObject(i)
									.getInt(Keys.GamesisSubscribed) + "");
					m.put(Keys.GamePostCount,
							json.getJSONObject(i).getInt(Keys.GamePostCount)
									+ "");
					m.put(Keys.GamesSubscriptionTime, json.getJSONObject(i)
							.getString(Keys.GamesSubscriptionTime));

					searchArray.add(m);
				} catch (Exception e) {
					Log.e("Fetching Event Search", "Error Event Search" + e);
				}
			}
		return searchArray;
	}

	public ArrayList<HashMap<String, String>> queryPlayerFriendsSearch(
			CharSequence search) {
		searchArray = new ArrayList<HashMap<String, String>>();
		JSONArray json = getArrayFromQuerryWithPostVariable(Keys.TEMPLAYERID,
				Keys.SearchFriendsTable, search.toString());

		// // Print the data to the console
		if (json != null) {
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Keys.ID_PLAYER,
							json.getJSONObject(i).getString(Keys.ID_PLAYER));
					map.put(Keys.CITY,
							json.getJSONObject(i).getString(Keys.CITY));
					map.put(Keys.COUNTRY,
							json.getJSONObject(i).getString(Keys.COUNTRY));
					map.put(Keys.PLAYERNICKNAME, json.getJSONObject(i)
							.getString(Keys.PLAYERNICKNAME));
					map.put(Keys.Email,
							json.getJSONObject(i).getString(Keys.Email));
					map.put(Keys.PLAYERAVATAR,
							json.getJSONObject(i).getString(Keys.PLAYERAVATAR));
					map.put(Keys.FirstName,
							json.getJSONObject(i).getString(Keys.FirstName));
					map.put(Keys.LastName,
							json.getJSONObject(i).getString(Keys.LastName));

					map.put(Keys.Age, json.getJSONObject(i).getString(Keys.Age));

					searchArray.add(map);
				} catch (Exception e) {
					Log.e("Fetching Friends Search", "Error Friends Search" + e);
				}
			}
			return searchArray;
		} else
			return null;
	}

	// MAYNOT BE NEEDED CURRENTLY NOT USED
	public ArrayList<HashMap<String, String>> queryPlayerSubsSearch(
			CharSequence search) {
		searchArray = new ArrayList<HashMap<String, String>>();

		json = getArrayFromQuerryWithPostVariable(Keys.TEMPLAYERID,
				Keys.SearchSubscriptionTable, search.toString());

		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Keys.ID_ITEM,
							json.getJSONObject(i).getInt(Keys.ID_ITEM) + "");
					map.put(Keys.ID_OWNER,
							json.getJSONObject(i).getInt(Keys.ID_OWNER) + "");
					map.put(Keys.ItemName,
							json.getJSONObject(i).getString(Keys.ItemName));
					map.put(Keys.ItemType,
							json.getJSONObject(i).getString(Keys.ItemType));
					map.put(Keys.SubscriptionTime, HelperClass.convertTime(
							Integer.parseInt(json.getJSONObject(i).getString(
									Keys.SubscriptionTime)), dataTemplate));

					searchArray.add(map);
				} catch (Exception e) {
					Log.e("Fetching Friends Search", "Error Friends Search" + e);
				}
			}
		return searchArray;
	}

	// -------------------------------------------------
	// public void queryArrayGameType() {
	// json = getArrayFromQuerryWithPostVariable("getArrayGameType.php",
	// Keys.TEMPLAYERID, Keys.TableSearchGroup, "0");
	// String[] arr = new String[json.length() + 1];
	// // // Print the data to the console
	// if (json != null)
	// for (int i = 0; i < json.length(); i++) {
	// try {
	// arr[json.getJSONObject(i).getInt(Keys.GameID_GAMETYPE)] = json
	// .getJSONObject(i).getString(Keys.GameTypeName);
	// } catch (Exception e) {
	// Log.e("Fetching ArrayGameType", "Error ArrayGameType" + e);
	// }
	// }
	// setGametypes(arr);
	// }

	// NOT NEEDED OR USED STILL NOT SURE TO BE REMOVED
	public String queryGroupCreator(String idCreator) {
		json = getArrayFromQuerryWithPostVariable(idCreator, Keys.PlayerTable,
				"0");
		String name = "";
		// // Print the data to the console
		if (json != null)
			for (int i = 0; i < json.length(); i++) {
				try {
					name = json.getJSONObject(i).getString(Keys.PLAYERNICKNAME);
				} catch (Exception e) {
					Log.e("Fetching Creator", "Error Creator" + e);
				}
			}
		return name;
	}

	public String returnEventPrivacy(int index) {
		if (index == 0) {
			return "Private";
		} else {
			return "Public";
		}
	}

	private String[] gametypes;

	public void setPlayer(HashMap<String, String> map) {
		mPlayer = map;
	}

	public HashMap<String, String> getPlayer() {
		return mPlayer;
	}

	public ArrayList<HashMap<String, String>> getArrayChildren() {
		return arrayChildren;
	}

	public ArrayList<HashMap<String, String>> getSearchArray() {
		return searchArray;
	}

	public String[] getGametypes() {
		return gametypes;
	}

	@SuppressWarnings("unused")
	private void setGametypes(String[] gametypess) {
		gametypes = gametypess;
	}

	// STIll NEEDED TO BE FINISHED
	public ArrayList<UserComment> getComments() {

		/*
		 * con = DataConnector.getInst(); ArrayList<HashMap<String,String>>
		 * commentsTable = con.getTable(Keys.commentsTable);
		 * ArrayList<UserComment> comments = new ArrayList<UserComment>();
		 * for(int i=0;i<commentsTable.size();i++) {
		 * if(commentsTable.get(i).get(
		 * Keys.ID_OWNER).equals(getIntent().getIntExtra(Keys.ID_GAME, 0)))
		 * comments.add(new UserComment(commentsTable.get(i).get(Keys.USERNAME),
		 * commentsTable.get(i).get(Keys.COMMENT),null)); } this.commentsList =
		 * comments;
		 */
		ArrayList<UserComment> comments = new ArrayList<UserComment>();
		ArrayList<CommentInfo> dummy1 = new ArrayList<CommentInfo>();
		ArrayList<CommentInfo> dummy2 = new ArrayList<CommentInfo>();
		ArrayList<CommentInfo> dummy3 = new ArrayList<CommentInfo>();
		dummy1.add(new CommentInfo("Claudiu", "Are you doing good?",
				"Saturday 11:12AM, 17/10/2013"));
		dummy1.add(new CommentInfo("Me", "Yes I am",
				"Saturday 11:12AM, 17/10/2013"));
		dummy1.add(new CommentInfo("Claudiu", "Good Good",
				"Saturday 11:13AM, 17/10/2013"));
		dummy2.add(new CommentInfo("Me", "When are you back",
				"Saturday 11:17AM, 17/10/2013"));
		dummy2.add(new CommentInfo("Claudiu", "Soon i hope",
				"Saturday 11:17AM, 17/10/2013"));
		dummy3.add(new CommentInfo("Me", "I'm sure you'll find out",
				"Saturday 11:19AM, 17/10/2013"));
		comments.add(new UserComment(new CommentInfo("Me",
				"Fine good weather today", "Saturday 12:12AM, 17/10/2013"),
				dummy1));
		comments.add(new UserComment(new CommentInfo("OtherMe",
				"Out for the weekend", "Saturday 12:15AM, 17/10/2013"), dummy2));
		comments.add(new UserComment(new CommentInfo("Claudiu",
				"Not sure what to do next", "Saturday 11:12AM, 17/10/2013"),
				dummy3));
		return comments;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String cREATE_PWall = "CREATE TABLE " + Keys.HomeWallTable + " ("
				+ Keys.ID_WALLITEM + " INTEGER PRIMARY KEY, " + Keys.ID_OWNER
				+ " INTEGER, " + Keys.ItemType + " TEXT, "
				+ Keys.WallLastActivityTime + " TEXT, "
				+ Keys.WallPosterDisplayName + " TEXT, " + Keys.WallMessage
				+ " TEXT, " + Keys.WallOwnerType + " TEXT, "
				+ Keys.WallPostingTime + " TEXT" + ");";
		db.execSQL(cREATE_PWall);

		String cREATE_gamesTable = "CREATE TABLE " + Keys.gamesTable + " ("
				+ Keys.ID_GAME + " INTEGER PRIMARY KEY, " + Keys.GAMENAME
				+ " TEXT, " + Keys.GAMETYPE + " TEXT, " + Keys.GAMEDESC
				+ " TEXT, " + Keys.GAMEDATE + " TEXT, " + Keys.RATING
				+ " TEXT, " + Keys.GAMEESRB + " TEXT, " + Keys.GAMEURL
				+ " TEXT, " + Keys.GAMEPLAYERSCOUNT + " TEXT, "
				+ Keys.GAMETYPENAME + " TEXT, " + Keys.GAMEPLATFORM + " TEXT, "
				+ Keys.GAMECompanyDistributor + " TEXT, " + Keys.CompanyFounded
				+ " TEXT, " + Keys.CompanyName + " TEXT" + ");";
		db.execSQL(cREATE_gamesTable);

		String cREATE_groupsTable = "CREATE TABLE " + Keys.groupsTable + " ("
				+ Keys.ID_GROUP + " INTEGER PRIMARY KEY, " + Keys.GROUPNAME
				+ " TEXT, " + Keys.GROUPTYPE + " TEXT, " + Keys.GROUPDESC
				+ " TEXT, " + Keys.GROUPTYPE2 + " TEXT, "
				+ Keys.GroupMemberCount + " TEXT, " + Keys.GROUPDATE
				+ " TEXT, " + Keys.GruopCreatorName + " TEXT);";
		db.execSQL(cREATE_groupsTable);

		String cREATE_newsTable = "CREATE TABLE " + Keys.newsTable + " ("
				+ Keys.NEWSCOLID_NEWS + " INTEGER PRIMARY KEY,"
				+ Keys.NEWSCOLNEWSTEXT + " TEXT," + Keys.NEWSCOLINTROTEXT
				+ " TEXT," + Keys.NEWSCOLPOSTINGTIME + " TEXT,"
				+ Keys.NEWSCOLHEADLINE + " TEXT," + Keys.Author + " TEXT);";
		db.execSQL(cREATE_newsTable);

		String cREATE_newsTempTable = "CREATE TABLE " + Keys.newsTempTable
				+ " (" + Keys.NEWSCOLID_NEWS + " INTEGER PRIMARY KEY,"
				+ Keys.ID_GAME + " INTEGER," + Keys.NEWSCOLNEWSTEXT + " TEXT,"
				+ Keys.NEWSCOLINTROTEXT + " TEXT," + Keys.NEWSCOLPOSTINGTIME
				+ " TEXT," + Keys.NEWSCOLHEADLINE + " TEXT," + Keys.Author
				+ " TEXT);";
		db.execSQL(cREATE_newsTempTable);

		String cREATE_companyTempTable = "CREATE TABLE "
				+ Keys.companyTempTable + " (" + Keys.NEWSCOLID_NEWS
				+ " INTEGER PRIMARY KEY," + Keys.ID_GAME + " INTEGER,"
				+ Keys.NEWSCOLNEWSTEXT + " TEXT," + Keys.NEWSCOLINTROTEXT
				+ " TEXT," + Keys.NEWSCOLPOSTINGTIME + " TEXT,"
				+ Keys.NEWSCOLHEADLINE + " TEXT," + Keys.Author + " TEXT);";
		db.execSQL(cREATE_companyTempTable);

		String cREATE_HomeMsgTable = "CREATE TABLE " + Keys.HomeMsgTable + " ("
				+ Keys.ID_MESSAGE + " INTEGER PRIMARY KEY,"
				+ Keys.MessageID_CONVERSATION + " INTEGER," + Keys.ID_PLAYER
				+ " INTEGER," + Keys.PLAYERNICKNAME + " TEXT,"
				+ Keys.PLAYERAVATAR + " TEXT," + Keys.MessageText + " TEXT,"
				+ Keys.MessageTime + " TEXT);";
		db.execSQL(cREATE_HomeMsgTable);

		String cREATE_HomeEventTable = "CREATE TABLE " + Keys.HomeEventTable
				+ " (" + Keys.ID_EVENT + " INTEGER PRIMARY KEY,"
				+ Keys.EventID_COMPANY + " INTEGER," + Keys.ID_PLAYER
				+ " INTEGER," + Keys.ID_GAME + " INTEGER," + Keys.ID_GROUP
				+ " INTEGER," + Keys.EventID_TEAM + " INTEGER,"
				+ Keys.EventIMAGEURL + " TEXT," + Keys.EventInviteLevel
				+ " TEXT," + Keys.EventIsPublic + " INTEGER," + Keys.EventType
				+ " TEXT," + Keys.EventIsExpired + " INTEGER,"
				+ Keys.EventDescription + " TEXT," + Keys.EventDuration
				+ " TEXT," + Keys.EventTime + " TEXT," + Keys.EventLocation
				+ " TEXT," + Keys.EventHeadline + " TEXT);";
		db.execSQL(cREATE_HomeEventTable);

		String cREATE_HomeFriendsTable = "CREATE TABLE "
				+ Keys.HomeFriendsTable + " (" + Keys.ID_PLAYER
				+ " INTEGER PRIMARY KEY," + Keys.ID_OWNER + " INTEGER,"
				+ Keys.CITY + " TEXT," + Keys.COUNTRY + " TEXT,"
				+ Keys.FirstName + " TEXT," + Keys.LastName + " TEXT,"
				+ Keys.Age + " TEXT," + Keys.PLAYERNICKNAME + " TEXT,"
				+ Keys.Email + " TEXT," + Keys.PLAYERAVATAR + " TEXT);";
		db.execSQL(cREATE_HomeFriendsTable);

		String cREATE_HomeGamesTable = "CREATE TABLE " + Keys.HomeGamesTable
				+ " (" + Keys.ID_GAME + " INTEGER PRIMARY KEY,"
				+ Keys.GameComments + " TEXT," + Keys.ID_PLAYER + " INTEGER,"
				+ Keys.GAMENAME + " TEXT," + Keys.GamesSubscriptionTime
				+ " TEXT," + Keys.GAMEDESC + " TEXT," + Keys.GameID_GAMETYPE
				+ " INTEGER," + Keys.GAMETYPE + " TEXT," + Keys.GameisPlaying
				+ " INTEGER," + Keys.GamesisSubscribed + " INTEGER,"
				+ Keys.GamePostCount + " INTEGER);";
		db.execSQL(cREATE_HomeGamesTable);

		String cREATE_HomeGroupTable = "CREATE TABLE " + Keys.HomeGroupTable
				+ " (" + Keys.ID_GROUP + " INTEGER PRIMARY KEY,"
				+ Keys.GROUPNAME + " TEXT," + Keys.ID_PLAYER + " INTEGER,"
				+ Keys.GROUPDESC + " TEXT," + Keys.GruopCreatorName + " TEXT,"
				+ Keys.GROUPTYPE + " TEXT," + Keys.GROUPTYPE2 + " TEXT,"
				+ Keys.GROUPDATE + " TEXT," + Keys.GAMENAME + " TEXT,"
				+ Keys.GroupMemberCount + " TEXT," + Keys.PLAYERNICKNAME
				+ " TEXT," + Keys.EventIMAGEURL + " TEXT);";
		db.execSQL(cREATE_HomeGroupTable);

		String cREATE_HomeWallRepliesTable = "CREATE TABLE "
				+ Keys.HomeWallRepliesTable + " (" + Keys.ID_WALLITEM
				+ " INTEGER PRIMARY KEY," + Keys.ID_ORGOWNER + " INTEGER,"
				+ Keys.WallPosterDisplayName + " TEXT," + Keys.PLAYERAVATAR
				+ " TEXT," + Keys.WallLastActivityTime + " TEXT,"
				+ Keys.WallMessage + " TEXT," + Keys.WallOwnerType + " TEXT,"
				+ Keys.WallPostingTime + " TEXT);";
		db.execSQL(cREATE_HomeWallRepliesTable);

		String cREATE_HomeMsgRepliesTable = "CREATE TABLE "
				+ Keys.HomeMsgRepliesTable + " (" + Keys.ID_MESSAGE
				+ " INTEGER PRIMARY KEY," + Keys.MessageID_CONVERSATION
				+ " INTEGER," + Keys.PLAYERNICKNAME + " TEXT,"
				+ Keys.PLAYERAVATAR + " TEXT," + Keys.MessageText + " TEXT,"
				+ Keys.MessageTime + " TEXT);";
		db.execSQL(cREATE_HomeMsgRepliesTable);

		String cREATE_HomeSubscriptionTable = "CREATE TABLE "
				+ Keys.HomeSubscriptionTable + " (" + Keys.ID_ITEM
				+ " INTEGER PRIMARY KEY," + Keys.ID_PLAYER + " INTEGER,"
				+ Keys.ID_OWNER + " INTEGER," + Keys.ItemName + " TEXT,"
				+ Keys.ItemType + " TEXT," + Keys.SubscriptionTime + " TEXT);";
		db.execSQL(cREATE_HomeSubscriptionTable);

		String cREATE_companyTable = "CREATE TABLE " + Keys.companyTable + " ("
				+ Keys.EventID_COMPANY + " INTEGER PRIMARY KEY,"
				+ Keys.CompanyName + " TEXT," + Keys.CompanyEmployees
				+ " INTEGER," + Keys.CompanyImageURL + " TEXT,"
				+ Keys.CompanyAddress + " TEXT," + Keys.CompanyDesc + " TEXT,"
				+ Keys.CompanyFounded + " TEXT," + Keys.CompanyURL + " TEXT,"
				+ Keys.CompanyCreatedTime + " TEXT," + Keys.CompanyOwnership
				+ " TEXT," + Keys.CompanyType + " TEXT,"
				+ Keys.CompanyNewsCount + " INTEGER," + Keys.CompanyEventCount
				+ " INTEGER," + Keys.CompanyGameCount + " INTEGER,"
				+ Keys.CompanySocialRating + " TEXT);";
		// String totalSQL = cREATE_PWall + cREATE_newsTable +
		// cREATE_groupsTable
		// + cREATE_gamesTable + cREATE_companyTable;
		db.execSQL(cREATE_companyTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + Keys.newsTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.groupsTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.gamesTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.companyTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.HomeWallTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.newsTempTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.companyTempTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.HomeMsgTable);
		db.execSQL("DROP TABLE IF EXISTS " + Keys.HomeSubscriptionTable);
		onCreate(db);
	}
}

/*
 * public void execQuerry(String phpScript, ArrayList<NameValuePair> data) {
 * String temp = url; try {
 * 
 * url += phpScript; httpclient = new DefaultHttpClient(); HttpPost httppost =
 * new HttpPost(url); httppost.setEntity(new UrlEncodedFormEntity(data));
 * HttpResponse response = httpclient.execute(httppost);
 * Log.e("log.tag.httpResponse", response.toString()); } catch (Exception e) {
 * Log.e("log_tag", "Error:  " + e.toString()); } url = temp; }
 */
