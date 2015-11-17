package com.cs5103.project.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.menu;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AgendaDialog extends DialogFragment
		implements
			OnItemClickListener,
			OnItemLongClickListener
{
	private static int titleResourceID;

	private CalendarOperations gCO;

	private ListView agendaList;

	private ArrayList<HashMap<String, String>> alAgendaList;

	static AgendaDialog newInstance(int titleResourceString)
	{
		AgendaDialog dlg = new AgendaDialog();
		titleResourceID = titleResourceString;
		return dlg;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gCO = new CalendarOperations(getActivity());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.agenda_dialog_layout, null);
		builder.setView(v);

		agendaList = (ListView) v.findViewById(R.id.agendaList);
		agendaList.setOnItemClickListener(this);
		agendaList.setOnItemLongClickListener(this);

		populateList();

		builder.setTitle(titleResourceID)
				.setIcon(R.drawable.ic_menu_agenda)
				.setTitle(titleResourceID)
				.setPositiveButton(R.string.buttonOK,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{

							}
						});

		return builder.create();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id)
	{
		final int eventId = Integer.parseInt(alAgendaList.get(position).get(
				"event_Id"));
		// AlertDialog to delete the event
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder.setTitle("Delete Event");
		alertDialogBuilder
				.setMessage(
						"Selected event will be deleted. Are you sure you want to delete this event?")
				.setPositiveButton(R.string.deleteEvent,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								// delete the event based on event id passed
								gCO.open();
								gCO.removeEvent(eventId);
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
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id)
	{
		// AlertDialog to delete the event
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setTitle("Update Event");
				alertDialogBuilder
						.setMessage(
								"Do you want to update the selected event?")
						.setPositiveButton(R.string.deleteEvent,
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
										Event mEvent = new Event();
										mEvent.setId(Integer.parseInt(alAgendaList.get(position)
												.get("event_Id")));
										mEvent.setTitle(alAgendaList.get(position).get("event_Name"));
										mEvent.setDescription(alAgendaList.get(position).get(
												"event_Description"));
										mEvent.setId(Integer.parseInt(alAgendaList.get(position)
												.get("event_Id")));
										mEvent.setStart_day(alAgendaList.get(position).get("event_startday"));
										mEvent.setEnd_day(alAgendaList.get(position).get("event_endday"));
										mEvent.setStart_time(alAgendaList.get(position).get("event_starttime"));
										mEvent.setEnd_time(alAgendaList.get(position).get("event_endtime"));
										mEvent.setLocation(alAgendaList.get(position).get("event_location"));
										mEvent.setEvent_category(Integer.parseInt(alAgendaList.get(position).get("event_cat_id")));
										DialogFragment eventDialog = EventDialog.newInstance(
												R.string.eventNameHeader, mEvent.getId(), mEvent.getTitle(),
												mEvent.getDescription(), mEvent.getStart_day(),
												mEvent.getStart_time(), mEvent.getEnd_day(),
												mEvent.getEnd_time(), mEvent.getLocation(), mEvent.getEvent_category());

										Log.e("attributes", mEvent.getTitle() + mEvent.getDescription());

										eventDialog.show(getFragmentManager(), "eventDialog");
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
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

		

	}

	private void populateList()
	{
		alAgendaList = new ArrayList<HashMap<String, String>>();
		ListAdapter mAdapter = null;
		gCO.open();
		List<Event> events_list = gCO.getAllEvents();
		gCO.close();

		if (events_list.isEmpty())
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("NoEvents", "You don't have any saved events");

			alAgendaList.add(map);
			mAdapter = new SimpleAdapter(getActivity(), alAgendaList,
					R.layout.no_events_agenda, new String[]
					{"NoEvents"}, new int[]
					{R.id.NoEvents});
		}
		else
		{
			for (Event event : events_list)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("event_Id", String.valueOf(event.getId()));
				map.put("event_Name", event.getTitle().toString());
				map.put("event_Description", event.getDescription().toString()
						.trim());
				map.put("event_loctime", "@"+event.getLocation()+" From " + event.getStart_day() + "-"
						+ event.getStart_time() + "To " + event.getEnd_day()
						+ "-" + event.getEnd_time());
				map.put("event_startday", event.getStart_day());
				map.put("event_endday", event.getEnd_day());
				map.put("event_starttime", event.getStart_time());
				map.put("event_endtime", event.getEnd_time());
				map.put("event_location", event.getLocation());
				map.put("event_cat_id", ""+event.getEvent_category());
				alAgendaList.add(map);
			}
			mAdapter = new SimpleAdapter(getActivity(), alAgendaList,
					R.layout.event_list_row, new String[]
					{"event_Id", "event_Name", "event_Description",
							"event_loctime","event_location"}, new int[]
					{R.id.event_Id, R.id.event_Name, R.id.event_Description,
							R.id.event_loctime,R.id.event_location});
		}

		agendaList.setAdapter(mAdapter);
	}
}