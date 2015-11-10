package com.cs5103.project.calendar;

import java.io.Serializable;

public class CategoryItem implements Serializable {

	private static final long serialVersionUID = -8690737009348875546L;
	ColorSpinnerItem categoryItem;
	
	public CategoryItem(int mColorID, String mText, int mIconID ) {
		categoryItem = new ColorSpinnerItem(mColorID, mText, mIconID);
	}
	
	public String getCategoryName() {
		return categoryItem.getColorString();
	}
	
	public int getCategoryColorID() {
		return categoryItem.getColorId();
	}
	
	public int getCategoryColorIconID() {
		return categoryItem.getIconId();
	}
}
