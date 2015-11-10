package com.cs5103.project.calendar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<Event> {
	@SuppressWarnings("unused")
	private List<Event> mEvents;
	TextView mEventTitle;
	TextView mEventStartTime;
	TextView mEventEndTime;
	ImageView mCategoryIcon;
	
	
	public EventListAdapter(Context context, int viewResource,
			List<Event> mEvents) {
		super(context, viewResource, mEvents);
		this.mEvents = mEvents;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v =inflater.inflate(R.layout.event_item_layout, null);
		}
		
		Event mEvent = getItem(position);
		
		mEventTitle = (TextView)v.findViewById(R.id.eventTitle);
		mEventTitle.setText(mEvent.getTitle());
		mEventStartTime = (TextView)v.findViewById(R.id.startTime);
		mEventStartTime.setText(mEvent.getStart_day());
		mEventEndTime = (TextView)v.findViewById(R.id.endTime);
		mEventEndTime.setText(mEvent.getEnd_day());
		mCategoryIcon = (ImageView)v.findViewById(R.id.eventCategoryIcon);
		
		return v;
	}
}
