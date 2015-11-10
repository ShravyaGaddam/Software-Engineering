package com.cs5103.project.calendar;

import java.util.Calendar;

public class CalendarGridCell {
	private Calendar mCellDate;
	private boolean mIsHoliday;
	private boolean mIsWeekend;
	private boolean mIsCurrentMonth;
	private boolean mIsSelected;
	
	public CalendarGridCell(Calendar mDate) {
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
