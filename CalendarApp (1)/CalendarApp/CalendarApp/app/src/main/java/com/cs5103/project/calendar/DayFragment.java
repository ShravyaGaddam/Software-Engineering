/**
 *
 */
package com.cs5103.project.calendar;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**Class name DayFragment
 *This class is to edit an event, delete an event also includes share functionality
 *
 */
public class DayFragment extends Fragment{

    private TextView mScrollPrevious;
    private TextView mScrollNext;
    private TextView mCalendarHeader;
    @SuppressWarnings("unused")
    private ListView mEventList;

    private CalendarOperations gCO;
    private EventListAdapter eventListAdapter;
    private List<Event> dayEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        gCO = new CalendarOperations(getActivity());
        gCO.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View mDayView = inflater.inflate(R.layout.grid_calendar_fragment, parent, false);


        mScrollPrevious = (TextView) mDayView.findViewById(R.id.scrollPrevious);
        mScrollPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mDate = CalendarData.get(getActivity()).getDateSelected();
                mDate.add(Calendar.DAY_OF_MONTH, -1);
                CalendarData.get(getActivity()).setDateSelected(mDate);
                CalendarData.get(getActivity()).setDateCurrent(mDate);
                mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());
                dayEvents.clear();
                dayEvents = gCO.getEventsByDate(new SimpleDateFormat("d MMM yy").format(CalendarData.get(getActivity()).getDateSelected().getTime()));
                eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), R.layout.simple_list, dayEvents);
                mEventList.setAdapter(eventListAdapter);

                eventListAdapter.notifyDataSetChanged();

            }
        });
        mScrollNext = (TextView) mDayView.findViewById(R.id.scrollNext);
        mScrollNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mDate = CalendarData.get(getActivity()).getDateSelected();
                mDate.add(Calendar.DAY_OF_MONTH, 1);
                CalendarData.get(getActivity()).setDateSelected(mDate);
                CalendarData.get(getActivity()).setDateCurrent(mDate);
                mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());
                dayEvents.clear();
                dayEvents = gCO.getEventsByDate(new SimpleDateFormat("d MMM yy").format(CalendarData.get(getActivity()).getDateSelected().getTime()));
                eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), R.layout.simple_list, dayEvents);
                mEventList.setAdapter(eventListAdapter);
                eventListAdapter.notifyDataSetChanged();


            }
        });
        mCalendarHeader = (TextView) mDayView.findViewById(R.id.calendarHeader);
        mCalendarHeader.setText(CalendarData.get(getActivity()).getCompleteCalendarHeader());


        mEventList = (ListView) mDayView.findViewById(R.id.eventList);
        registerForContextMenu(mEventList);

        //Not sure whether this is going to work with data in sqlite, have to check\
        try {
            String sss = new SimpleDateFormat("d MMM yy").format(CalendarData.get(getActivity()).getDateSelected().getTime());
            dayEvents = gCO.getEventsByDate(new SimpleDateFormat("d MMM yy").format(CalendarData.get(getActivity()).getDateSelected().getTime()));

            Collections.sort(dayEvents, new SortByStartingTime());
            eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), R.layout.simple_list, dayEvents);
            mEventList.setAdapter(eventListAdapter);
        } catch (
                Exception e
                ) {
            e.printStackTrace();
        }
        return mDayView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        menu.add(1, 1, 0, "Edit");
        menu.add(1, 2, 1, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        switch (item.getItemId()) {


            case 1://edit

                // AlertDialog to delete the event

                alertDialogBuilder.setTitle("Update Event");
                alertDialogBuilder
                        .setMessage(
                                "Do you want to update the selected event?")
                        .setPositiveButton(R.string.deleteEvent,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Event mEvent = eventListAdapter.getItem(position);
                                        DialogFragment eventDialog = EventDialog.newInstance(
                                                R.string.eventNameHeader, mEvent.getId(), mEvent.getTitle(),
                                                mEvent.getDescription(), mEvent.getStart_day(),
                                                mEvent.getStart_time(), mEvent.getEnd_day(),
                                                mEvent.getEnd_time(), mEvent.getLocation(), mEvent.getEvent_category());

                                        Log.e("attributes", mEvent.getTitle() + mEvent.getDescription());

                                        eventDialog.show(getActivity().getFragmentManager(), "eventDialog");
                                        dialog.dismiss();


                                    }
                                })
                        .setNegativeButton(R.string.buttonCancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
            case 2://delete
// AlertDialog to delete the event
                alertDialogBuilder.setTitle("Delete Event");
                alertDialogBuilder
                        .setMessage(
                                "Selected event will be deleted. Are you sure you want to delete this event?")
                        .setPositiveButton(R.string.deleteEvent,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // delete the event based on event id passed
                                        gCO.open();
                                        gCO.removeEvent(eventListAdapter.getItem(position).getId());
                                        update();
                                        dialog.dismiss();

                                    }
                                })
                        .setNegativeButton(R.string.buttonCancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog1 = alertDialogBuilder.create();
                // show it
                alertDialog1.show();


                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    /**Method name update()
     * this method update event
     */
    public void update() {
        try {
            dayEvents = gCO.getEventsByDate(new SimpleDateFormat("d MMM yy").format(CalendarData.get(getActivity()).getDateSelected().getTime()));
            Collections.sort(dayEvents, new SortByStartingTime());
            eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), R.layout.simple_list, dayEvents);
            mEventList.setAdapter(eventListAdapter);
            eventListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Class name SortByStartingTime
     *
     * This class sorts the events based on starting time
     */
    private static class SortByStartingTime implements Comparator<Event> {
        @Override
        public int compare(Event event1, Event event2) {
            DateFormat formatter = new SimpleDateFormat("h:mm a");
            try {
                Date start_time1 = formatter.parse(event1.getStart_time());
                Date start_time2 = formatter.parse(event2.getStart_time());
                return start_time1.compareTo(start_time2);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gCO != null)
            gCO.close();
    }
}
