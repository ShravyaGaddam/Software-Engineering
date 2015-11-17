package com.cs5103.project.calendar;

import java.util.Date;

public class Event
{

	private int id;
	private String start_day;
	private String end_day;
	private String start_time;
	private String end_time;
	public String getStart_time()
	{
		return start_time;
	}

	public void setStart_time(String start_time)
	{
		this.start_time = start_time;
	}

	public String getEnd_time()
	{
		return end_time;
	}

	public void setEnd_time(String end_time)
	{
		this.end_time = end_time;
	}

	private String title;
	private String description;
	private int week_repeat = 0;
	private int month_repeat = 0;
	private int year_repeat = 0;
	private int event_category = -1;
	private String location;
	
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String name)
	{
		this.title = name;
	}

	public String getStart_day()
	{
		return start_day;
	}

	
	public void setStart_day(String start_day)
	{
		this.start_day = start_day;
	}

	public String getEnd_day()
	{
		return end_day;
	}

	public void setEnd_day(String end_day)
	{
		this.end_day = end_day;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getWeek_repeat()
	{
		return week_repeat;
	}

	public void setWeek_repeat(int week_repeat)
	{
		this.week_repeat = week_repeat;
	}

	public int getMonth_repeat()
	{
		return month_repeat;
	}

	public void setMonth_repeat(int month_repeat)
	{
		this.month_repeat = month_repeat;
	}

	public int getYear_repeat()
	{
		return year_repeat;
	}

	public void setYear_repeat(int year_repeat)
	{
		this.year_repeat = year_repeat;
	}


	public int getEvent_category()
	{
		return event_category;
	}

	public void setEvent_category(int string)
	{
		this.event_category = string;
	}

}
