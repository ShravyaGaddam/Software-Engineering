package com.cs5103.project.calendar;

import java.util.Calendar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class PickerDialog extends DialogFragment {
	private static int titleResourceID;
	
	private static TimePicker mTime;
	private static DatePicker mDate;
	private Calendar mSelected;
	
	static PickerDialog newInstance(int titleResourceString, Calendar mInitialDate) {
		PickerDialog dlg = new PickerDialog();
		titleResourceID = titleResourceString;
		return dlg;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.date_time_picker_layout, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(v);
		mSelected = (Calendar) Calendar.getInstance().clone();
		
		mDate = (DatePicker)v.findViewById(R.id.datePicker);
		mDate.setCalendarViewShown(false);
		
		mTime = (TimePicker)v.findViewById(R.id.timePicker);
		
		builder.setTitle(titleResourceID)
		.setIcon(R.drawable.ic_menu_add)
		.setTitle(titleResourceID)
		.setPositiveButton(R.string.buttonOK, 
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mSelected.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),
							mTime.getCurrentHour(), mTime.getCurrentMinute());
					EventDialog.updateDates(mSelected);
				}
			})
		.setNegativeButton(R.string.buttonCancel, 
			new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	
		return builder.create();
	}
}