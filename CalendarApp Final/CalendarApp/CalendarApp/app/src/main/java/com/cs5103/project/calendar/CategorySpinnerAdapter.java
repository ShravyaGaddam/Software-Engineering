package com.cs5103.project.calendar;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
	private Context mActivity;
	
	public CategorySpinnerAdapter(Context context, int layoutResource,
			List<Category> objects) {
		super(context, layoutResource, objects);
		// TODO Auto-generated constructor stub
		mActivity = context;
	}
	
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		View v = inflater.inflate(R.layout.category_spinner_item, parent, false);
	
		View icon = (View)v.findViewById(R.id.spinnerItem1);
		TextView text = (TextView)v.findViewById(R.id.spinnerItem2);
		icon.setBackgroundColor(Color.parseColor(getItem(position).getColor()));
		text.setText(getItem(position).getName());

		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	// It gets a View that displays the data at the specified position
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
}
