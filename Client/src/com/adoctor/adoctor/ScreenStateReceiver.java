package com.adoctor.adoctor;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver로서, Screen on, off 될 때 Broadcast를 받아 DB에 시간과 함께 기록
 * 
 * @author Choi H.John, Sky77
 * 
 */
public class ScreenStateReceiver extends BroadcastReceiver {

	// TODO : 하드코딩 (ENUM)
	private static final boolean ON = true;
	private static final boolean OFF = false;
	private static final String iON = Intent.ACTION_SCREEN_ON;
	private static final String iOFF = Intent.ACTION_SCREEN_OFF;

	@Override
	public void onReceive(Context context, Intent intent) {

		boolean screenstate;
		if (intent.getAction().equals(iON)) screenstate = ON;
		else if (intent.getAction().equals(iOFF)) screenstate = OFF;
		else return;
		
		ContentValues cv = new ContentValues();
		cv.put("time", System.currentTimeMillis());
		cv.put("screenstate", screenstate);
		
		// TODO : 하드코딩 (테이블 이름)
		DBAdapter adb = new DBAdapter(context, "scrlog");
		adb.open();
		adb.insertTable(cv);
		adb.close();
	}
}