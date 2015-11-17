package com.cs5103.project.calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EventListAdapter extends ArrayAdapter<Event> {
    @SuppressWarnings("unused")
    private List<Event> mEvents;
    TextView mEventTitle;
    TextView mEventStartTime;
    TextView mEventEndTime;
    ImageView mCategoryIcon, ivShare;

    CalendarOperations gco;

    public EventListAdapter(Context context, int viewResource,
                            List<Event> mEvents) {
        super(context, viewResource, mEvents);
        this.mEvents = mEvents;
        gco = new CalendarOperations(context);
        gco.open();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.event_item_layout, null);
        }

        final Event mEvent = getItem(position);
        mEventTitle = (TextView) v.findViewById(R.id.eventTitle);
        mEventTitle.setText(mEvent.getTitle());
        mEventStartTime = (TextView) v.findViewById(R.id.startTime);
        mEventStartTime.setText(mEvent.getStart_day());
        mEventEndTime = (TextView) v.findViewById(R.id.endTime);
        mEventEndTime.setText(mEvent.getEnd_day());
        mCategoryIcon = (ImageView) v.findViewById(R.id.eventCategoryIcon);
        ivShare = (ImageView) v.findViewById(R.id.share);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "Title:" + mEvent.getTitle() + ",\n " + "Description:" + mEvent.getDescription() + ",\nLocation:" + mEvent.getLocation();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setType("text/plain");
                getContext().startActivity(sendIntent);
            }
        });
        List<Category> cats = gco.getCategories(CalendarWrapper.CATEGORY_ID + "="+mEvent.getEvent_category());
        if (cats.size() > 0)
            v.setBackgroundColor(Color.parseColor(cats.get(0).getColor()));
        return v;
    }
}
