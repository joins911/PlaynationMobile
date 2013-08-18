package com.myapps.playnation.Operations;

public class Configurations {

	public final static int appStateOffUser = 2;
	public final static int appStateOnGuest = 1;
	public final static int appStateOnUser = 0;
	private int currentAdapterSection;
	public final static boolean isLoginEnabled = true;

	private static Configurations inst;
	private static int appState;

	private Configurations() {
	}

	public static Configurations getConfigs() {
		if (inst == null)
			return new Configurations();
		else
			return inst;
	}

	public static int getApplicationState() {
		return appState;
	}

	public static void setApplicationState(int state) {
		appState = state;
	}

	public void setAdapterSection(int adaptSect) {
		currentAdapterSection = adaptSect;
	}

	public int getAdapterSection() {
		return this.currentAdapterSection;
	}

	public static boolean isAppState(int appState2) {
		if (appState == appState2)
			return true;
		return false;
	}

	// private boolean isLoginCheckEnabled = false;
	// public boolean isViewPagerSwipeEnable = false;
	// public String serverIp = "87.55.208.165:1337";

}