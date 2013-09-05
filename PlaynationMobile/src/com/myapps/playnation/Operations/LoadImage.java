package com.myapps.playnation.Operations;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class LoadImage extends AsyncTask<Object, Object, Bitmap> {
	private ImageView img;
	private String url;
	private String folderName;
	private String path = "";
	private String initalmageUrl = "";
	private DataConnector con = DataConnector.getInst();

	public LoadImage() {
	}

	public LoadImage(String ownerID, String ownerType, String tableName,
			String url, ImageView img, String folderName) {
		this.img = img;
		this.url = url;
		this.folderName = folderName;
		if (img.getTag() != null)
			path = img.getTag().toString();
		String tempUrl = "";
		tempUrl = con.queryNewImageURL(ownerID, ownerType, tableName);
		if (!tempUrl.equalsIgnoreCase("")) {
			url = tempUrl;
		}
	}

	@Override
	protected Bitmap doInBackground(Object... params) {

		String finals = "";
		String main = folderName + "/";
		Bitmap returnBitmap = null;

		if (!url.equalsIgnoreCase("")) {
			String dir1 = url.substring(0, 1);
			String dir2 = url.substring(1, 2);
			initalmageUrl = main + dir1 + "/" + dir2 + "/" + url;
			String[] temp = new String[1];
			if (url.contains(".jpg")) {
				temp = url.split(".jpg");
				url = temp[0] + "_100x100.jpg";
			} else if (url.contains(".png")) {
				temp = url.split(".png");
				url = temp[0] + "_100x100.png";
			}
			finals = main + dir1 + "/" + dir2 + "/" + url;
		} else {
			finals = main;
		}
		if (Keys.internetStatus) {
			returnBitmap = getImage(finals, img, false);
			if (returnBitmap != null) {
				return returnBitmap;
			} else {
				return getImage(initalmageUrl, img, true);
			}
		} else {
			return returnBitmap;
		}
	}

	@Override
	protected void onPreExecute() {
		no_Image(folderName);
	}

	protected void onPostExecute(Bitmap bitmap) {
		if (img.getTag() != null)
			if (img.getTag().equals(path)) {
				if (bitmap == null || img == null) {
					if (!url.equalsIgnoreCase("")) {
						no_Image(folderName);
					}
				} else {
					img.setImageBitmap(bitmap);
				}
			}
	}

	private void no_Image(String folderName) {
		if (folderName.equals("games")) {
			img.setImageResource(R.drawable.no_game_100x100);
		} else if (folderName.equals("players")) {
			img.setImageResource(R.drawable.no_player_100x100);
		} else if (folderName.equals("newsitems")) {
			img.setImageResource(R.drawable.no_news_100x100);
		} else if (folderName.equals("groups")) {
			img.setImageResource(R.drawable.no_group_100x100);
		} else if (folderName.equals("wall_photos")) {
			img.setImageResource(R.drawable.no_forum_100x100);
		} else if (folderName.equals("companies")) {
			img.setImageResource(R.drawable.no_company_100x100);
		}
	}

	public Bitmap getImage(String imageLoc, ImageView tvImage, boolean attempt) {
		URL imageURL = null;
		Bitmap bitmap = null;

		try {
			imageURL = new URL("http://playnation.eu/beta/global/pub_img/"
					+ imageLoc);

			HttpURLConnection connection = (HttpURLConnection) imageURL
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		} catch (IOException e) {
		}
		if (bitmap == null) {
			try {
				imageURL = new URL("http://playnation.eu/global/pub_img/"
						+ imageLoc);

				HttpURLConnection connection = (HttpURLConnection) imageURL
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream inputStream = connection.getInputStream();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPurgeable = true;
				bitmap = BitmapFactory.decodeStream(inputStream, null, options);
			} catch (IOException e) {
			}
		}
		if (attempt) {
			if (bitmap != null)
				bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
		}
		return bitmap;
	}
}
