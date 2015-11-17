/**
 * 
 */
package com.cs5103.project.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class WeekFragment extends Fragment {
	
	private TextView mScrollPrevious;
	private TextView mScrollNext;
	private TextView mCalendarHeader;
	private GridView mDayGrid;
	private GridView mCalendarGrid;
	@SuppressWarnings("unused")
	private ListView mEventList;
	private List<CalendarGridCell> mWeekCells;
	private DaysOfWeekAdapter mDayGridAdapter;
	private WeekGridAdapter mCalendarGridAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWeekCells = new ArrayList<CalendarGridCell>();
		initCalendarCells();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View mWeekView = inflater.inflate(R.layout.grid_calendar_fragment, parent, false);
		
		mScrollPrevious = (TextView)mWeekView.findViewById(R.id.scrollPrevious);
		mScrollPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mDate = CalendarData.get(getActivity()).getDateCurrent();
				mDate.add(Calendar.DAY_OF_MONTH, -7);
				CalendarData.get(getActivity()).setDateCurrent(mDate);
				mCalendarHeader.setText(CalendarData.get(getActivity()).getCalendarHeader());
				initCalendarCells();
				mCalendarGridAdapter.notifyDataSetChanged();
			}
		});
		mScrollNext =(TextView)mWeekView.findViewById(R.id.scrollNext);
		mScrollNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mDate = CalendarData.get(getActivity()).getDateCurrent();
				mDate.add(Calendar.DAY_OF_MONTH, 7);
				CalendarData.get(getActivity()).setDateCurrent(mDate);
				mCalendarHeader.setText(CalendarData.get(getActivity()).getCalendarHeader());				
				initCalendarCells();
				mCalendarGridAdapter.notifyDataSetChanged();
			}			
		});
		mCalendarHeader = (TextView)mWeekView.findViewById(R.id.calendarHeader);
		mCalendarHeader.setText(CalendarData.get(getActivity()).getCalendarHeader());

		mDayGrid = (GridView)mWeekView.findViewById(R.id.dayHeader);
		mDayGridAdapter = new DaysOfWeekAdapter(getActivity(),
				CalendarData.get(getActivity()).getWeekdays_Abbr());
		mDayGrid.setAdapter(mDayGridAdapter);
		
		mCalendarGrid = (GridView)mWeekView.findViewById(R.id.calendarGrid);
		mCalendarGridAdapter = new WeekGridAdapter(getActivity(), mWeekCells);
		mCalendarGrid.setAdapter(mCalendarGridAdapter);
		
		mEventList = (ListView)mWeekView.findViewById(R.id.eventList);
		
		return mWeekView;
	}
	
	public void update() {
		initCalendarCells();
		mCalendarGridAdapter.setCalendarCells(mWeekCells);
		mCalendarGridAdapter.update();
	}

	private void initCalendarCells() {
		Calendar mStartDate = (Calendar)CalendarData.get(getActivity()).getDateCurrent().clone();
		Calendar mSelectedDate = (Calendar)CalendarData.get(getActivity()).getDateSelected();
		Calendar mCurrentDate = (Calendar)CalendarData.get(getActivity()).getDateCurrent();
		int mCurrentMonth = mCurrentDate.get(Calendar.MONTH);
		int mSelectedYear = mSelectedDate.get(Calendar.YEAR);
		int mSelectedMonth = mSelectedDate.get(Calendar.MONTH);
		int mSelectedDay = mSelectedDate.get(Calendar.DAY_OF_MONTH);
		int mDayOfWeek;
		CalendarGridCell mCell;
		int mCellYear, mCellMonth, mCellDay;
		
		if (mWeekCells.size() > 0)
			mWeekCells.clear();
		mDayOfWeek = mStartDate.get(Calendar.DAY_OF_WEEK);
		mStartDate.add(Calendar.DAY_OF_MONTH, -(mDayOfWeek - 1));
		for (int i = 0; i < 7; i++) {
			mCell = new CalendarGridCell(mStartDate, getActivity());
			mCellYear = mStartDate.get(Calendar.YEAR);
			mCellMonth = mStartDate.get(Calendar.MONTH);
			mCellDay = mStartDate.get(Calendar.DAY_OF_MONTH);
			mDayOfWeek = mStartDate.get(Calendar.DAY_OF_WEEK);
			if (mCellMonth == mCurrentMonth)
				mCell.setCurrentMonth(true);
			else
				mCell.setCurrentMonth(false);
			if ((mDayOfWeek == Calendar.SATURDAY) | (mDayOfWeek == Calendar.SUNDAY))
				mCell.setWeekend(true);
			else
				mCell.setWeekend(false);
			if ((mCellDay == mSelectedDay) & (mCellMonth == mSelectedMonth) 
					& (mCellYear == mSelectedYear))
				mCell.setSelected(true);
			else
				mCell.setSelected(false);
			mWeekCells.add(mCell);
			mStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

}
