package com.goznauk.adoctor;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenStateReceiver extends BroadcastReceiver {

	public static boolean screenstate;
	public static String time;

	private final boolean ON = true;
	private final boolean OFF = false;
	private final String iON = Intent.ACTION_SCREEN_ON;
	private final String iOFF = Intent.ACTION_SCREEN_OFF;

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();

		Date dtime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy,MM,dd,hh,mm,ss");
		time = sdf.format(dtime);

		DBAdapter adb = new DBAdapter(context, DBAdapter.SQL_CREATE_SCRLOG, "srclog");
		adb.open();

		ContentValues cv = new ContentValues();

		if (intent.getAction().equals(iON)) {
			screenstate = ON;

			cv.put("time", time);
			cv.put("screenstate", screenstate);
			adb.insertTable(cv);
			adb.close();

			Toast.makeText(context,"BR catched screen is on" + time + screenstate,
							Toast.LENGTH_SHORT).show();
		}
		if (intent.getAction().equals(iOFF)) {
			screenstate = OFF;

			cv.put("time", time);
			cv.put("screenstate", screenstate);
			adb.insertTable(cv);
			adb.close();

		}

		// Intent toServiceIntent = new Intent(context, BRControlService.class);
		// toServiceIntent.putExtra("screenstate", screenstate)
		// .putExtra("time", time);
		// context.startService(toServiceIntent);
	}
}