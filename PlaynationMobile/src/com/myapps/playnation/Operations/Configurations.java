package com.myapps.playnation.Operations;

public class Configurations {

	public final static int appStateOffUser = 1;
	public final static int appStateOnGuest = 2;
	public final static int appStateOnUser = 3;
	private int currentAdapterSection;
	public final static boolean isLoginEnabled = true;

	private static Configurations inst;
	private int appState;

	private Configurations() {
	}

	public static Configurations getConfigs() {
		if (inst == null)
			return new Configurations();
		else
			return inst;
	}

	public int getApplicationState() {
		return this.appState;
	}

	public void setApplicationState(int state) {
		appState = state;
	}

	public void setAdapterSection(int adaptSect) {
		currentAdapterSection = adaptSect;
	}

	public int getAdapterSection() {
		return this.currentAdapterSection;
	}

	// private boolean isLoginCheckEnabled = false;
	// public boolean isViewPagerSwipeEnable = false;
	// public String serverIp = "87.55.208.165:1337";

}
