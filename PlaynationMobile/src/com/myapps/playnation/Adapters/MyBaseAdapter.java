package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.os.Bundle;

public interface MyBaseAdapter {
	public void showMore();

	public boolean canShowMore();

	public ArrayList<Bundle> getList();
}
