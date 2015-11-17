package com.cs5103.project.calendar;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.content.Context;

public class CalendarData {

	private static CalendarData sCalendarData;
	
	private Calendar mDateCurrent;
	private Calendar mDateSelected;
	
	private Calendar mEventStart;
	private Calendar mEventEnd;
	
	private String[] mMonths = new DateFormatSymbols().getMonths();
	private String[] mWeekdays = new DateFormatSymbols().getWeekdays();
	private String[] mWeekdays_Full;
	private String[] mWeekdays_Abbr;
	
	private List<ColorSpinnerItem> mColors;
	
	private CalendarData(Context mContext) {
		setDateCurrent(Calendar.getInstance());
		setDateSelected(Calendar.getInstance());
		initializeWeekdays();
		initColors();
	}
	
	public static CalendarData get(Context c) {
		if (sCalendarData == null)
			sCalendarData = new CalendarData(c.getApplicationContext());
		
		return sCalendarData;
	}
	
	private void initializeWeekdays() {
		mWeekdays_Full = new String[mWeekdays.length - 1];
		mWeekdays_Abbr = new String[mWeekdays.length - 1];
		for (int i = 1; i < mWeekdays.length; i++) {
			mWeekdays_Full[i-1] = mWeekdays[i];
			mWeekdays_Abbr[i-1] = mWeekdays[i].substring(0,3);
		}
	}
	
	public String[] getWeekdays_Full() {
		return mWeekdays_Full;
	}
	
	public String[] getWeekdays_Abbr() {
		return mWeekdays_Abbr;
	}
	
	public void setDateSelected(Calendar mDate) {
		mDateSelected = (Calendar)mDate.clone();
	}
	
	public Calendar getDateSelected() {
		return mDateSelected;
	}
	
	public void setDateCurrent(Calendar mDate) {
		mDateCurrent = (Calendar)mDate.clone();
	}
	
	public Calendar getDateCurrent() {
		return mDateCurrent;
	}
	
	public Calendar getToday() {
		return Calendar.getInstance();
	}
	
	public void setEventStart(Calendar mStart) {
		mEventStart = (Calendar)mStart.clone();
	}
	
	public Calendar getEventStart() {
		return mEventStart;
	}
	
	public void setEventEnd(Calendar mEnd) {
		mEnd = (Calendar)mEnd.clone();
	}
	
	public Calendar getEventEnd() {
		return mEventEnd;
	}
	
	public String getCalendarHeader() {
		int mYear = mDateCurrent.get(Calendar.YEAR);
		int mMonth = mDateCurrent.get(Calendar.MONTH);
		
		return mMonths[mMonth] + " " + Integer.toString(mYear);
	}
	
	public String getCompleteCalendarHeader() {
		int mYear = mDateSelected.get(Calendar.YEAR);
		int mMonth = mDateSelected.get(Calendar.MONTH);
		int mDay = mDateSelected.get(Calendar.DAY_OF_MONTH);
		
		return Integer.toString(mDay) + " " + mMonths[mMonth] + " " + Integer.toString(mYear);
	}
	
	private void initColors() {
		mColors = new ArrayList<ColorSpinnerItem>();
		ColorSpinnerItem mColor;
		
		mColor = new ColorSpinnerItem(R.color.yellow, "Yellow", R.drawable.icon_yellow);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.fuchsia, "Fuscia", R.drawable.icon_fuscia);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.red, "Red", R.drawable.icon_red);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.silver, "Silver", R.drawable.icon_silver);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.gray, "Gray", R.drawable.icon_gray);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.olive, "Olive", R.drawable.icon_olive);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.purple, "Purple", R.drawable.icon_purple);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.maroon, "Maroon", R.drawable.icon_maroon);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.aqua, "Aqua", R.drawable.icon_aqua);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.lime, "Lime", R.drawable.icon_lime);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.teal, "Teal", R.drawable.icon_teal);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.green, "Green", R.drawable.icon_green);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.blue, "Blue", R.drawable.icon_blue);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.navy, "Navy", R.drawable.icon_navy);
		mColors.add(mColor);
		mColor = new ColorSpinnerItem(R.color.white, "White", R.drawable.icon_white);
		mColors.add(mColor);
	}
	
	public List<ColorSpinnerItem> getColors() {
		return mColors;
	}
}
