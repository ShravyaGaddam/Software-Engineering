/**
 * 
 */
package com.cs5103.project.calendar;

import java.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DayFragment extends Fragment {
	
	private TextView mScrollPrevious;
	private TextView mScrollNext;
	private TextView mCalendarHeader;
	@SuppressWarnings("unused")
	private ListView mEventList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View mDayView = inflater.inflate(R.layout.grid_calendar_fragment, parent, false);
		
		mScrollPrevious = (TextView)mDayView.findViewById(R.id.scrollPrevious);
		mScrollPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mDate = CalendarData.get(getActivity()).getDateSelected();
				mDate.add(Calendar.DAY_OF_MONTH, -1);
				CalendarData.get(getActivity()).setDateSelected(mDate);
				CalendarData.get(getActivity()).setDateCurrent(mDate);
				mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());
			}
		});
		mScrollNext =(TextView)mDayView.findViewById(R.id.scrollNext);
		mScrollNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mDate = CalendarData.get(getActivity()).getDateSelected();
				mDate.add(Calendar.DAY_OF_MONTH, 1);
				CalendarData.get(getActivity()).setDateSelected(mDate);
				CalendarData.get(getActivity()).setDateCurrent(mDate);
				mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());
			}			
		});
		mCalendarHeader = (TextView)mDayView.findViewById(R.id.calendarHeader);
		mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());
		
		mEventList = (ListView)mDayView.findViewById(R.id.eventList);
		
		return mDayView;
	}
}
