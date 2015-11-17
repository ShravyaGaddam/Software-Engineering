package com.cs5103.project.calendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DaysOfWeekAdapter extends ArrayAdapter<String> {
	private Activity mActivity;
	private SharedPreferences mPreferences;
	public DaysOfWeekAdapter(Activity mApp, String[] mDaysOfWeek) {
		super(mApp.getApplicationContext(), 0, mDaysOfWeek);
		mActivity = mApp ;
		mPreferences = PreferenceManager.getDefaultSharedPreferences(mApp.getApplicationContext());
		mPreferences.edit();
	}
	
	@Override
	public View getView(int mPosition, View convertView, ViewGroup parent) {
		String mDay;
		if (convertView == null)
			convertView = mActivity.getLayoutInflater().inflate(R.layout.calendar_header_layout, null);
		TextView mDayText = (TextView)convertView.findViewById(R.id.calendar_header_grid_text);
		mDay = getItem(mPosition);
		mDayText.setText(mDay);
		mDayText.setBackgroundColor(mActivity.getResources().getColor(R.color.black));
		mDayText.setTextColor(mActivity.getResources().getColor(R.color.white));
		return convertView;
	}
}
