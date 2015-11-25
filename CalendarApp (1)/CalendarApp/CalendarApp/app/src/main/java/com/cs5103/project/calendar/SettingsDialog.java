package com.cs5103.project.calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 *
 *
 */
public class SettingsDialog extends DialogFragment {
	private static int titleResourceID;
	private static SharedPreferences mPreferences;
	private static SharedPreferences.Editor mPreferencesEditor;
	private static List<CategoryItem> categoryList;
//	private static Spinner mCategorySpinner;
//	private String categoryFile;

	static SettingsDialog newInstance(int titleResourceString) {
		SettingsDialog dlg = new SettingsDialog();
		titleResourceID = titleResourceString;
		categoryList = new ArrayList<CategoryItem>();
		return dlg;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mPreferencesEditor = mPreferences.edit();
//		categoryFile = getActivity().getResources().getString(R.string.categoryFileName);
//		ReadCategoryList();
//		if (categoryList.size() == 0) {
//			CategoryItem categoryItem;
//			ColorSpinnerItem mColor = CalendarData.get(getActivity()).getColors().get(14);
//			categoryItem = new CategoryItem(mColor.getColorId(), "None", mColor.getIconId());
//			categoryList.add(categoryItem);
//		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.settings_dialog_layout, null);
		builder.setView(v);

		final Spinner mHolidayColor = (Spinner)v.findViewById(R.id.colorHolidaySpinner);
		mHolidayColor.setAdapter(new ColorSpinnerAdapter(getActivity(),
				R.layout.color_spinner_layout,
				CalendarData.get(getActivity()).getColors()));
		mHolidayColor.setSelection(mPreferences.getInt("HolidayColor", 9));
		final CheckBox mShowHoliday = (CheckBox)v.findViewById(R.id.showHolidays);
		mShowHoliday.setChecked(mPreferences.getBoolean("HolidayFlag", false));

		final Spinner mWeekendColor = (Spinner)v.findViewById(R.id.colorWeekendSpinner);
		mWeekendColor.setAdapter(new ColorSpinnerAdapter(getActivity(),
				R.layout.color_spinner_layout,
				CalendarData.get(getActivity()).getColors()));
		mWeekendColor.setSelection(mPreferences.getInt("WeekendColor", 4));
		final CheckBox mShowWeekend = (CheckBox)v.findViewById(R.id.showWeekends);
		mShowWeekend.setChecked(mPreferences.getBoolean("WeekendFlag", false));

//		mCategorySpinner = (Spinner)v.findViewById(R.id.categorySpinner);
//		mCategorySpinner.setAdapter(new CategorySpinnerAdapter(getActivity(),
//				R.layout.color_spinner_layout,
//				categoryList));
//		final Button mAddCategory = (Button)v.findViewById(R.id.addCategory);
//		mAddCategory.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View categoryView) {
//				showCategoryDialog();
//			}
//		});


		builder.setTitle(titleResourceID)
			.setIcon(R.drawable.ic_menu_settings)
			.setTitle(titleResourceID)
			.setPositiveButton(R.string.buttonApply,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mPreferencesEditor.putInt("HolidayColor", mHolidayColor.getSelectedItemPosition());
						mPreferencesEditor.putBoolean("HolidayFlag", mShowHoliday.isChecked());
						mPreferencesEditor.putInt("WeekendColor", mWeekendColor.getSelectedItemPosition());
						mPreferencesEditor.putBoolean("WeekendFlag", mShowWeekend.isChecked());
						mPreferencesEditor.commit();
						((CalendarActivity) getActivity()).settingsOK();
					}
				})
			.setNegativeButton(R.string.buttonCancel,
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((CalendarActivity) getActivity()).settingsCancel();
					}
				});

		return builder.create();
	}

//	public static void updateCategories() {
//		((CategorySpinnerAdapter)mCategorySpinner.getAdapter()).notifyDataSetChanged();
//	}

//	private void showCategoryDialog() {
//		CategoryDialog categoryDialog = CategoryDialog.newInstance(R.string.categories,
//				categoryList);
//		categoryDialog.show(getFragmentManager(), "categoryDialog");
//	}

//    private void ReadCategoryList() {
//    	ObjectInput in = null;
//    	CategoryItem categoryItem = null;
//    	CategoryItem categoryTemp = null;
//
//    	try {
//    		in = new ObjectInputStream(new FileInputStream(new File(new File(getActivity().getFilesDir(), "")
//    							+ File.separator + categoryFile)));
//    		while ((categoryTemp = (CategoryItem) in.readObject()) != null) {
//    			categoryItem = new CategoryItem(categoryTemp.getCategoryColorID(),
//    					categoryTemp.getCategoryName(),
//    					categoryTemp.getCategoryColorIconID());
//    			categoryList.add(categoryItem);
//    		}
//    		in.close();
//    	}
//    	catch (StreamCorruptedException e) {
//    		e.printStackTrace();
//    	}
//    	catch (FileNotFoundException e) {
//    		e.printStackTrace();
//    	}
//    	catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    	catch (ClassNotFoundException e) {
//    		e.printStackTrace();
//    	}
//    }
}