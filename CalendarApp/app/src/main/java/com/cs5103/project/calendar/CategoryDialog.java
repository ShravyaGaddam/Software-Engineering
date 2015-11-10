package com.cs5103.project.calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class CategoryDialog extends DialogFragment {
	private static int titleResourceID;
	private static List<CategoryItem> categoryList;
	private String categoryFile;
	
	static CategoryDialog newInstance(int titleResourceString, 
			List<CategoryItem> categories) {
		CategoryDialog dlg = new CategoryDialog();
		titleResourceID = titleResourceString;
		categoryList = categories;
		return dlg;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.category_dialog_layout, null);
		builder.setView(v);

		final EditText mCategoryName = (EditText)v.findViewById(R.id.categoryTitle);
		final Spinner mCategoryColor = (Spinner)v.findViewById(R.id.colorCategorySpinner);
		mCategoryColor.setAdapter(new ColorSpinnerAdapter(getActivity(),
				R.layout.color_spinner_layout,
				CalendarData.get(getActivity()).getColors()));
		
		builder.setTitle(titleResourceID)
			.setIcon(R.drawable.ic_menu_settings)
			.setTitle(titleResourceID)
			.setPositiveButton(R.string.buttonApply, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ColorSpinnerItem categoryColor = (ColorSpinnerItem) mCategoryColor.getSelectedItem();
						String mText = mCategoryName.getText().toString();
						CategoryItem categoryItem = new CategoryItem(
								categoryColor.getColorId(),
								mText,
								categoryColor.getIconId());
						categoryList.add(categoryItem);
						WriteCategoryList();
						SettingsDialog.updateCategories();
					}
				})
			.setNegativeButton(R.string.buttonCancel, 
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		
		return builder.create();
	}
	
    private void WriteCategoryList() {
    	ObjectOutput out = null;
    	CategoryItem nullCategory = null;
    	categoryFile = getActivity().getResources().getString(R.string.categoryFileName);	
    	if (categoryList.size() > 0) {
    		try {
    			out = new ObjectOutputStream(new FileOutputStream(new File(getActivity().getFilesDir(), "")
    									+ File.separator + categoryFile, false));
 
    			for (int i = 0; i < categoryList.size(); i++)
    				out.writeObject(categoryList.get(i));
    			out.writeObject(nullCategory);
    			out.close();
    		}
    		catch (FileNotFoundException e){
    			e.printStackTrace();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
}