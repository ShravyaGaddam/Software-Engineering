package com.cs5103.project.calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint(
{"SimpleDateFormat", "CutPasteId"})
public class EventDialog extends DialogFragment
{
	private static int titleResourceID;
	private static SharedPreferences mPreferences;
	private static List<CategoryItem> categoryList;
	private String categoryFile;

	private final static int END_DATE = 0;
	private final static int START_DATE = 1;
	private final static int DATE_DEFAULT = -1;
	protected static final Context Context = null;
	private static int mDateCallerID = DATE_DEFAULT;

	private static TextView startDateText;
	private static TextView startTimeText;
	private static TextView endDateText;
	private static TextView endTimeText;
	private static String eventNameValue, decValue, startDateValue,
			endDateValue, startTimeValue, endTimeValue,locationValue;
	private static int savedEventId=0;

	private static EditText eventName, eventDescription, eventLocation;

	private static SimpleDateFormat dateFormat;
	private static SimpleDateFormat timeFormat;

	private CalendarOperations gCO;

	static EventDialog newInstance(int titleResourceString, int eventId,
			String eventName1, String desc, String startDate, String startTime,
			String endDate, String endTime, String location)
	{
		EventDialog dlg = new EventDialog();
		titleResourceID = titleResourceString;
		categoryList = new ArrayList<CategoryItem>();
		dateFormat = new SimpleDateFormat("d MMM yy");
		timeFormat = new SimpleDateFormat("h:mm a");
		eventNameValue = eventName1;
		decValue = desc;
		startDateValue = startDate;
		endDateValue = endDate;
		startTimeValue = startTime;
		endTimeValue = endTime;
		savedEventId = eventId;
		locationValue = location;
		return dlg;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gCO = new CalendarOperations(getActivity());
		mPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		mPreferences.edit();
		categoryFile = getActivity().getResources().getString(
				R.string.categoryFileName);
		ReadCategoryList();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.event_dialog_layout, null);
		builder.setView(v);

		eventName = (EditText) v.findViewById(R.id.eventName);
		if (eventNameValue != null)
		{
			eventName.setText(eventNameValue);
		}
		eventDescription = (EditText) v.findViewById(R.id.eventDescription);
		if (decValue != null)
		{
			eventDescription.setText(decValue);
		}
		
		eventLocation = (EditText) v.findViewById(R.id.eventLocation);
		if(locationValue!=null)
		{
			eventLocation.setText(locationValue);
		}
		Calendar startDate = (Calendar) CalendarData.get(getActivity())
				.getDateSelected().clone();

		startDateText = (TextView) v.findViewById(R.id.startDate);
		startDateText.setText(dateFormat.format(startDate.getTime()));
		if(startDateValue!=null)
		{
			startDateText.setText(startDateValue);
		}
		
		startTimeText = (TextView) v.findViewById(R.id.startTime);
		startTimeText.setText(timeFormat.format(startDate.getTime()));
		if(startTimeValue!=null)
		{
			startTimeText.setText(startTimeValue);
		}
		Button eventStart = (Button) v.findViewById(R.id.setStart);
		eventStart.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View startView)
			{
				PickerDialog mStart = PickerDialog.newInstance(
						R.string.eventTimeBegin, Calendar.getInstance());
				mDateCallerID = START_DATE;
				mStart.show(getFragmentManager(), "pickerDialog");
			}
		});

		
		endDateText = (TextView) v.findViewById(R.id.endDate);
		endDateText.setText(dateFormat.format(startDate.getTime()));
		if(endDateValue!=null)
		{
			endDateText.setText(endDateValue);
		}
		
		endTimeText = (TextView) v.findViewById(R.id.endTime);
		endTimeText.setText(timeFormat.format(startDate.getTime()));
		if(endTimeValue!=null)
		{
			endTimeText.setText(endTimeValue);
		}
		Button eventEnd = (Button) v.findViewById(R.id.setEnd);
		eventEnd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View endView)
			{
				PickerDialog mEnd = PickerDialog.newInstance(
						R.string.eventTimeEnd, Calendar.getInstance());
				mDateCallerID = END_DATE;
				mEnd.show(getFragmentManager(), "pickerDialog");

			}
		});

		@SuppressWarnings("unused")
		TextView eventEndDate = (TextView) v.findViewById(R.id.endDate);

		Spinner eventCategory = (Spinner) v
				.findViewById(R.id.eventCategorySpinner);
		eventCategory.setAdapter(new CategorySpinnerAdapter(getActivity(),
				R.layout.color_spinner_layout, categoryList));

		builder.setTitle(titleResourceID)
				.setIcon(R.drawable.ic_menu_add)
				.setTitle(titleResourceID)
				.setPositiveButton(R.string.buttonOK,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								gCO.open();
								if (savedEventId >0)
								{
									Event mEvent = new Event();
									mEvent.setId(savedEventId);
									Log.d("LogEntry-startDateText.getText().toString()",
											startDateText.getText().toString());
									Log.d("LogEntry-endDateText.getText().toString()",
											endDateText.getText().toString());
									mEvent.setTitle(eventName.getText()
											.toString());
									mEvent.setDescription(eventDescription
											.getText().toString());
									mEvent.setStart_day(startDateText.getText()
											.toString());
									mEvent.setEnd_day(endDateText.getText()
											.toString());
									mEvent.setLocation(eventLocation.getText()
											.toString());
									mEvent.setStart_time(startTimeText
											.getText().toString());
									mEvent.setEnd_time(endTimeText.getText()
											.toString());
									gCO.updateEvent(mEvent);
									
								}
								else
								{
									Event mEvent = new Event();
									Log.d("LogEntry-startDateText.getText().toString()",
											startDateText.getText().toString());
									Log.d("LogEntry-endDateText.getText().toString()",
											endDateText.getText().toString());
									mEvent.setTitle(eventName.getText()
											.toString());
									mEvent.setDescription(eventDescription
											.getText().toString());
									mEvent.setStart_day(startDateText.getText()
											.toString());
									mEvent.setEnd_day(endDateText.getText()
											.toString());
									mEvent.setLocation(eventLocation.getText()
											.toString());
									mEvent.setStart_time(startTimeText
											.getText().toString());
									mEvent.setEnd_time(endTimeText.getText()
											.toString());

									gCO.addEvent(mEvent);
								}
								gCO.close();
								getDialog().dismiss();
							}
						})
				.setNegativeButton(R.string.buttonCancel,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								getDialog().dismiss();
							}
						});

		return builder.create();
	}

	public static void updateDates(Calendar mNewTime)
	{
		switch (mDateCallerID)
		{
			case START_DATE :
			{
				startDateText.setText(dateFormat.format(mNewTime.getTime()));
				startTimeText.setText(timeFormat.format(mNewTime.getTime()));
				break;
			}
			case END_DATE :
			{
				endDateText.setText(dateFormat.format(mNewTime.getTime()));
				endTimeText.setText(timeFormat.format(mNewTime.getTime()));
				break;
			}
		}
		mDateCallerID = DATE_DEFAULT;
	}

	private void ReadCategoryList()
	{
		ObjectInput in = null;
		CategoryItem categoryItem = null;
		CategoryItem categoryTemp = null;

		try
		{
			in = new ObjectInputStream(new FileInputStream(new File(new File(
					getActivity().getFilesDir(), "")
					+ File.separator
					+ categoryFile)));
			while ((categoryTemp = (CategoryItem) in.readObject()) != null)
			{
				categoryItem = new CategoryItem(
						categoryTemp.getCategoryColorID(),
						categoryTemp.getCategoryName(),
						categoryTemp.getCategoryColorIconID());
				categoryList.add(categoryItem);
			}
			in.close();
		}
		catch (StreamCorruptedException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
