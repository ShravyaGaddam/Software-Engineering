package com.cs5103.project.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/**Class Description Holiday
 * Holidays dates are defined in this class
 *
 */

public class Holiday
{
	private int id;
	private String holiday;
	private long holiday_date;
	private int type;

	public void setID(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return id;
	}

	public String getHoliday()
	{
		return holiday;
	}

	public void setHoliday(String holiday)
	{
		this.holiday = holiday;
	}
	public long getHoliday_date()
	{
		return holiday_date;
	}

	public void setHoliday_date(long holiday_date)
	{
		this.holiday_date = holiday_date;
	}

	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public static Date NewYearsDay(int nYear)
	{
		// January 1st
		int nMonth = 0; // January
		return new Date(nYear, nMonth, 1);
	}

	/**
	 * This method checks whether a given date is holiday or not
	 * @param currentDate is the date passed into method
	 * @return boolean value based on the date passed
	 */
	public static boolean isHoliday(Date currentDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("d MM");
		String dateString = sdf.format(currentDate);
		String dateComponents[] = dateString.split(" ");
		int date = Integer.parseInt(dateComponents[0]);
		int month = Integer.parseInt(dateComponents[1]);
		if((date == 1 && month == 1))  //New Year
			return true;
		else if(date == 25 && month == 12)  //Christmas
			return true;
		else if(date == 26 && month == 11)  //Thanks giving day
			return true;
		else if(date  == 7 && month == 9)  //Labour day
			return true;
		else if(date == 25 && month == 5)  //Memorial day
			return true;
		else
			return false;
//		if(currentDate.getDay()==NewYearsDayObserved(currentDate.getYear()).getDay())
//		{
//			return true;
//		}
//		else if(currentDate==PresidentsDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==MemorialDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//
//		else if(currentDate==IndependenceDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//
//		else if(currentDate==IndependenceDay(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==LaborDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==ColumbusDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==VeteransDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==ThanksgivingObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==ChristmasDayObserved(currentDate.getYear()))
//		{
//			return true;
//		}
//		else if(currentDate==ChristmasDay(currentDate.getYear()))
//		{
//			return true;
//		}
//			return false;
	}

	/** Method description NewYearsObserved
	 *
	 *
	 * @param nYear
	 * @return
	 */
	public static Date NewYearsDayObserved(int nYear)
	{
		int nX;
		int nMonth = 0; // January
		int nMonthDecember = 11; // December
		Date dtD;
		dtD = new Date(nYear, nMonth, 1);
		nX = dtD.getDay();
		if (nYear > 1900)
		{
			nYear -= 1900;
		}
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 2);
			case 1 : // Monday
			case 2 : // Tuesday
			case 3 : // Wednesday
			case 4 : // Thursday
			case 5 : // Friday
				return new Date(nYear, nMonth, 1);
			default :
				// Saturday, then observe on friday of previous year
				return new Date(--nYear, nMonthDecember, 31);
		}
	}
	public static Date PresidentsDayObserved(int nYear)
	{
		// Third Monday in February
		int nX;
		int nMonth = 1; // February
		Date dtD;
		dtD = new Date(nYear, nMonth, 1);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 16);
			case 1 : // Monday
				return new Date(nYear, nMonth, 15);
			case 2 : // Tuesday
				return new Date(nYear, nMonth, 21);
			case 3 : // Wednesday
				return new Date(nYear, nMonth, 20);
			case 4 : // Thursday
				return new Date(nYear, nMonth, 19);
			case 5 : // Friday
				return new Date(nYear, nMonth, 18);
			default : // Saturday
				return new Date(nYear, nMonth, 17);
		}
	}

	public static Date MemorialDayObserved(int nYear)
	{
		// Last Monday in May
		int nX;
		int nMonth = 4; // May
		Date dtD;
		dtD = new Date(nYear, nMonth, 31);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 25);
			case 1 : // Monday
				return new Date(nYear, nMonth, 31);
			case 2 : // Tuesday
				return new Date(nYear, nMonth, 30);
			case 3 : // Wednesday
				return new Date(nYear, nMonth, 29);
			case 4 : // Thursday
				return new Date(nYear, nMonth, 28);
			case 5 : // Friday
				return new Date(nYear, nMonth, 27);
			default : // Saturday
				return new Date(nYear, nMonth, 26);
		}
	}
	public static Date IndependenceDayObserved(int nYear)
	{
		int nX;
		int nMonth = 6; // July
		Date dtD;
		dtD = new Date(nYear, nMonth, 4);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 5);
			case 1 : // Monday
			case 2 : // Tuesday
			case 3 : // Wednesday
			case 4 : // Thursday
			case 5 : // Friday
				return new Date(nYear, nMonth, 4);
			default :
				// Saturday
				return new Date(nYear, nMonth, 3);
		}
	}
	public static Date IndependenceDay(int nYear)
	{
		int nMonth = 6; // July
		// July 4th
		return new Date(nYear, nMonth, 4);
	}
	public static Date LaborDayObserved(int nYear)
	{
		// The first Monday in September
		int nX;
		int nMonth = 8; // September
		Date dtD;
		dtD = new Date(nYear, 9, 1);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 2);
			case 1 : // Monday
				return new Date(nYear, nMonth, 7);
			case 2 : // Tuesday
				return new Date(nYear, nMonth, 6);
			case 3 : // Wednesday
				return new Date(nYear, nMonth, 5);
			case 4 : // Thursday
				return new Date(nYear, nMonth, 4);
			case 5 : // Friday
				return new Date(nYear, nMonth, 3);
			default : // Saturday
				return new Date(nYear, nMonth, 2);
		}
	}
	public static Date ColumbusDayObserved(int nYear)
	{
		// Second Monday in October
		int nX;
		int nMonth = 9; // October
		Date dtD;
		dtD = new Date(nYear, nMonth, 1);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 9);
			case 1 : // Monday
				return new Date(nYear, nMonth, 15);
			case 2 : // Tuesday
				return new Date(nYear, nMonth, 14);
			case 3 : // Wednesday
				return new Date(nYear, nMonth, 13);
			case 4 : // Thursday
				return new Date(nYear, nMonth, 12);
			case 5 : // Friday
				return new Date(nYear, nMonth, 11);
			default : // Saturday
				return new Date(nYear, nMonth, 10);
		}
	}
	public static Date VeteransDayObserved(int nYear)
	{
		// November 11th
		int nMonth = 10; // November
		return new Date(nYear, nMonth, 11);
	}
	public static Date ThanksgivingObserved(int nYear)
	{
		int nX;
		int nMonth = 10; // November
		Date dtD;
		dtD = new Date(nYear, nMonth, 1);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 26);
			case 1 : // Monday
				return new Date(nYear, nMonth, 25);
			case 2 : // Tuesday
				return new Date(nYear, nMonth, 24);
			case 3 : // Wednesday
				return new Date(nYear, nMonth, 23);
			case 4 : // Thursday
				return new Date(nYear, nMonth, 22);
			case 5 : // Friday
				return new Date(nYear, nMonth, 28);
			default : // Saturday
				return new Date(nYear, nMonth, 27);
		}
	}
	public static Date ChristmasDayObserved(int nYear)
	{
		int nX;
		int nMonth = 11; // December
		Date dtD;
		dtD = new Date(nYear, nMonth, 25);
		nX = dtD.getDay();
		switch (nX)
		{
			case 0 : // Sunday
				return new Date(nYear, nMonth, 26);
			case 1 : // Monday
			case 2 : // Tuesday
			case 3 : // Wednesday
			case 4 : // Thursday
			case 5 : // Friday
				return new Date(nYear, nMonth, 25);
			default :
				// Saturday
				return new Date(nYear, nMonth, 24);
		}
	}
	public static Date ChristmasDay(int nYear)
	{
		int nMonth = 11; // December
		// December 25th
		return new Date(nYear, nMonth, 25);
	}
}
