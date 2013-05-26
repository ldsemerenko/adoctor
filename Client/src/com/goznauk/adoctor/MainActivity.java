package com.goznauk.adoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 화면에 보여지는 Activity로, DB의 내용을 가져와 보여줌 일단은 전부 보여주게 코딩함
 * 
 * @author Choi H.John, Sky77
 * 
 */
public class MainActivity extends Activity {

	Context mCtx = this;
	TextView mTV;

	/**
	 * 프로그램 진입점
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTV = (TextView) findViewById(R.id.textview);

		// Start Service 버튼 onClick 이벤트 핸들러에 이벤트 등록
		Button button = (Button) findViewById(R.id.startservicebtn);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startService(new Intent(mCtx, BRControlService.class));
			}
		});
	}

	/**
	 * 새로고침 버튼 누르면 호출됨 DB의 내용을 가져와 TextView에 뿌려줌
	 */
	public void onRefreshButton(View v) {
		// TODO : 하드코딩 (테이블 이름)
		DBAdapter adb = new DBAdapter(this, "scrlog");
		adb.open();

		// TODO : 하드코딩 (칼럼 이름)
		String columns[] = { "time", "screenstate" };
		Cursor c = adb.selectTable(columns, null, null, null, null, null);

		String msg = "● Screen State Log \n";
		if (c.moveToFirst()) {
			do msg += c.getLong(0) + ' ' + c.getString(1) + '\n';
			while (c.moveToNext());
		}

		adb.close();

		mTV.setText(msg);
	}

	public void delete() {
		// TODO : 하드코딩 (테이블 이름)
		DBAdapter adb = new DBAdapter(this, "scrlog");
		adb.open();
		adb.query("DELETE FROM scrlog");
		adb.close();
	}

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO : 나중에 메뉴버튼 늘리면 고쳐야함
		delete();
		return super.onOptionsItemSelected(item);
	}
}