package com.cs5103.project.calendar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**Class name ColorspinnerAdapter
 *
 *
 */

public class ColorSpinnerAdapter extends ArrayAdapter<ColorSpinnerItem> {
	private Context mActivity;
	
	public ColorSpinnerAdapter(Context context, int layoutResource,
			List<ColorSpinnerItem> objects) {
		super(context, layoutResource, objects);
		// TODO Auto-generated constructor stub
		mActivity = context;
	}

	/**Method name getCustomView
	 *
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return customised view
	 */
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		View v = inflater.inflate(R.layout.color_spinner_layout, parent, false);
	
		ImageView icon = (ImageView)v.findViewById(R.id.spinnerItemColor);
		TextView text = (TextView)v.findViewById(R.id.spinnerItemText);
		icon.setImageResource(getItem(position).getIconId());
		text.setText(getItem(position).getColorString());

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
