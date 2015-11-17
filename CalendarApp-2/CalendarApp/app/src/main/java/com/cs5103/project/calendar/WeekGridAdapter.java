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

	@Override
	public View getView(int mPosition, View convertView, ViewGroup parent) {
		final CalendarGridCell mCell;
		int mColor;
		if (convertView == null)
			convertView = mActivity.getLayoutInflater().inflate(R.layout.calendar_cell_layout, null);
		TextView mCellDate = (TextView)convertView.findViewById(R.id.cellDate);
		mCell = getItem(mPosition);
		mCellDate.setText(Integer.toString(mCell.getCellDate().get(Calendar.DAY_OF_MONTH)));
		TextView cellList = (TextView) convertView.findViewById(R.id.cellList);
		cellList.setText(mCell.getEvent());
		convertView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
		if (mCell.isWeekend()) {
			if (mPreferences.getBoolean("WeekendFlag", false)) {
				mColor = mPreferences.getInt("WeekendColor", 4);
				int mColorId = getContext().getResources().getColor(mColors.get(mColor).getColorId());
				convertView.setBackgroundColor(mColorId);
			}
		}
		if (mCell.isHoliday()) {
			if (mPreferences.getBoolean("HolidayFlag", false)) {
				mColor = mPreferences.getInt("HolidayColor", 9);
				int mColorId = getContext().getResources().getColor(mColors.get(mColor).getColorId());
				convertView.setBackgroundColor(mColorId);
			}
		}
		if (mCell.isSelected())
			convertView.setBackgroundColor(mActivity.getResources().getColor(R.color.red));
		if (mCell.isCurrentMonth())
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
				mCell.setSelected(true);
				CalendarData.get(getContext()).setDateSelected(mCell.getCellDate());
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
	
	public void update() {
		notifyDataSetChanged();
	}
}
