package com.cs5103.project.calendar;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CalendarGridCell {
	private Calendar mCellDate;
	private boolean mIsHoliday;
	private boolean mIsWeekend;
	private boolean mIsCurrentMonth;
	private boolean mIsSelected;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	private String event;

	public CalendarGridCell(Calendar mDate, Context context) {
		setCellDate((Calendar)mDate.clone());
		int mDayOfWeek = mCellDate.get(Calendar.DAY_OF_WEEK);
		if ((mDayOfWeek == 1) | (mDayOfWeek == 7))
		{
			mIsWeekend = true;
		}
		else
		{
			mIsWeekend = false;
		}
		if(Holiday.isHoliday(mDate.getTime()))
		{
			mIsHoliday = true;
		}
		else
		{
			mIsHoliday = false;
		}

		mIsCurrentMonth = false;
		mIsSelected = false;
		CalendarOperations gCO = new CalendarOperations(context);
		gCO.open();
		List<Event> dayEvents = gCO.getEventsByDate(new SimpleDateFormat("d MMM yy").format(mDate.getTime()));
		if(dayEvents.size()>0){
			setEvent(dayEvents.get(0).getTitle());
		}
	}

	public void setCellDate(Calendar mDate) {
		mCellDate = (Calendar)mDate.clone();
	}

	public Calendar getCellDate() {
		return mCellDate;
	}

	public String getCellDateString() {
		return Integer.toString(mCellDate.get(Calendar.DAY_OF_MONTH));
	}

	public boolean isWeekend() {
		return mIsWeekend;
	}

	public void setWeekend(boolean is) {
		mIsWeekend = is;
	}

	public boolean isHoliday() {
		return mIsHoliday;
	}

	public void setHoliday(boolean is) {
		mIsHoliday = is;
	}

	public boolean isCurrentMonth() {
		return mIsCurrentMonth;
	}

	public void setCurrentMonth(boolean is) {
		mIsCurrentMonth = is;
	}

	public boolean isSelected() {
		return mIsSelected;
	}

	public void setSelected(boolean is) {
		mIsSelected = is;
	}
}
