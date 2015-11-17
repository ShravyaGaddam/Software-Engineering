package com.cs5103.project.calendar;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WeekGridAdapter extends ArrayAdapter<CalendarGridCell> {
	private Activity mActivity;
	private List<CalendarGridCell> mCells;
	private SharedPreferences mPreferences;
	@SuppressWarnings("unused")
	private SharedPreferences.Editor mPreferencesEditor;
	private List<ColorSpinnerItem> mColors;
	
	public WeekGridAdapter(Activity mApp, List<CalendarGridCell> mCells) {
		super(mApp, 0, mCells);
		mActivity = mApp;
		this.mCells = mCells;
		mColors = CalendarData.get(getContext()).getColors();
		mColors = CalendarData.get(getContext()).getColors();
		mPreferences = PreferenceManager.getDefaultSharedPreferences(mApp.getApplicationContext());
		mPreferencesEditor = mPreferences.edit();
	}

	public void setCalendarCells(List<CalendarGridCell> mCells){
		this.mCells = mCells;
	}

	@Override
	public View getView(int mPosition, View convertView, ViewGroup parent) {
		final CalendarGridCell mCell;
		int mColor;
		if (convertView == null)
			convertView = mActivity.getLayoutInflater().inflate(R.layout.calendar_cell_layout, null);
		TextView mCellDate = (TextView)convertView.findViewById(R.id.cellDate);
		final CalendarGridCell cellData = mCells.get(mPosition);
		mCellDate.setText(Integer.toString(cellData.getCellDate().get(Calendar.DAY_OF_MONTH)));
		TextView cellList = (TextView) convertView.findViewById(R.id.cellList);
		cellList.setText(cellData.getEvent());
		convertView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
		if (cellData.isWeekend()) {
			if (mPreferences.getBoolean("WeekendFlag", false)) {
				mColor = mPreferences.getInt("WeekendColor", 4);
				int mColorId = getContext().getResources().getColor(mColors.get(mColor).getColorId());
				convertView.setBackgroundColor(mColorId);
			}
		}
		if (cellData.isHoliday()) {
			if (mPreferences.getBoolean("HolidayFlag", false)) {
				mColor = mPreferences.getInt("HolidayColor", 9);
				int mColorId = getContext().getResources().getColor(mColors.get(mColor).getColorId());
				convertView.setBackgroundColor(mColorId);
			}
		}
		if (cellData.isSelected())
			convertView.setBackgroundColor(mActivity.getResources().getColor(R.color.red));
		if (cellData.isCurrentMonth())
			mCellDate.setTextColor(mActivity.getResources().getColor(R.color.black));
		else
			mCellDate.setTextColor(mActivity.getResources().getColor(R.color.LightGrey));

		TextView mCellEvents = (TextView)convertView.findViewById(R.id.cellList);

		// Temporary place holder for number of events to show in cell		
		// mCellEvents.setText("1");
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < mCells.size(); i++) {
					CalendarGridCell tempCell = mCells.get(i);
					tempCell.setSelected(false);
				}
				cellData.setSelected(true);
				CalendarData.get(getContext()).setDateSelected(cellData.getCellDate());
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
	
	public void update() {
		notifyDataSetChanged();
	}
}
