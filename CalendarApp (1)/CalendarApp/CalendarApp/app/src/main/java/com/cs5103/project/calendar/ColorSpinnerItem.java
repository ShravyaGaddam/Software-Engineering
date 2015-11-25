package com.cs5103.project.calendar;

import java.io.Serializable;

public class ColorSpinnerItem implements Serializable {

	private static final long serialVersionUID = -8269241573340682556L;
	private int mColorResourceId;
	private String mColorName;
	private int mIconResourceId;

	public ColorSpinnerItem(int mColorId, String mName, int mIconId) {
		mColorResourceId = mColorId;
		mColorName = mName;
		mIconResourceId = mIconId;
	}

	public int getColorId() {
		return mColorResourceId;
	}

	public void setColorId(int mColorId) {
		mColorResourceId = mColorId;
	}

	public String getColorString() {
		return mColorName;
	}

	public void setColorString(String mColorString) {
		mColorName = mColorString;
	}

	public int getIconId() {
		return mIconResourceId;
	}

	public void setIconId(int mIconId) {
		mIconResourceId = mIconId;
	}
}
