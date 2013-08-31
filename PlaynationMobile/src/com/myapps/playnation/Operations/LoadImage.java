package com.myapps.playnation.Operations;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.myapps.playnation.R;

public class LoadImage extends AsyncTask<Object, Object, Bitmap> {
	private ImageView img;
	private String url;
	private String folderName;
	private String path = "";

	public LoadImage() {
		// TODO Auto-generated constructor stub
	}

	public LoadImage(String url, ImageView img, String folderName) {
		this.img = img;
		this.url = url;
		this.folderName = folderName;
		if (img.getTag() != null)
			path = img.getTag().toString();

	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		String finals = "";
		String main = folderName + "/";
		if (!url.equalsIgnoreCase("")) {
			String dic1 = url.substring(0, 1);
			String dic2 = url.substring(1, 2);
			finals = main + dic1 + "/" + dic2 + "/" + url;
		} else {
			finals = main;
		}
		return getImage(finals, img);
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
					bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
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

	public Bitmap getImage(String imageLoc, ImageView tvImage) {
		URL imageURL = null;
		Bitmap bitmap = null;
		try {
			imageURL = new URL("http://playnation.eu/beta/global/pub_img/"
					+ imageLoc);
		} catch (MalformedURLException e) {
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) imageURL
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream inputStream = connection.getInputStream();

			bitmap = BitmapFactory.decodeStream(inputStream);

		} catch (IOException e) {
		}
		if (bitmap == null) {
			try {
				imageURL = new URL("http://playnation.eu/global/pub_img/"
						+ imageLoc);
			} catch (MalformedURLException e) {
			}
			try {
				HttpURLConnection connection = (HttpURLConnection) imageURL
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream inputStream = connection.getInputStream();

				bitmap = BitmapFactory.decodeStream(inputStream);

			} catch (IOException e) {
			}
		}

		return bitmap;
	}
}
