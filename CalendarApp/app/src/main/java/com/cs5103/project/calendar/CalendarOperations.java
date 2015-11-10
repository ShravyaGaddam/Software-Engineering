package com.cs5103.project.calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CalendarOperations
{

	private CalendarWrapper dbHelper;
	private SQLiteDatabase database;

	private String[] HOLIDAY_TABLE_COLUMNS =
	{CalendarWrapper.HOLIDAY_TITLE, CalendarWrapper.HOLIDAY_DATE,
			CalendarWrapper.HOLIDAY_TYPE, CalendarWrapper.HOLIDAY_ID};
	private String[] EVENT_TABLE_COLUMNS =
	{CalendarWrapper.EVENT_ID, CalendarWrapper.EVENT_TITLE,
			CalendarWrapper.EVENT_START_DAY, CalendarWrapper.EVENT_END_DAY,
			CalendarWrapper.EVENT_START_TIME, CalendarWrapper.EVENT_END_TIME,
			CalendarWrapper.EVENT_WK_REPEAT, CalendarWrapper.EVENT_MNTH_REPEAT,
			CalendarWrapper.EVENT_YR_REPEAT, CalendarWrapper.EVENT_LCTN,
			CalendarWrapper.EVENT_DESC, CalendarWrapper.EVENT_CTGRY,};

	private String[] CATEGORY_TABLE_COLUMNS =
	{CalendarWrapper.CATEGORY_ID, CalendarWrapper.CATEGORY_NAME,
			CalendarWrapper.CATEGORY_COLOR};

	public CalendarOperations(Context context)
	{
		dbHelper = new CalendarWrapper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}
	// ///////////////////////////////////////////////////
	// /// HOLIDAY TABLE SECTION
	// ///////////////////////////////////////////////////
	public void removeHolday(Holiday hldy)
	{
		database.delete(CalendarWrapper.HOLIDAYS, CalendarWrapper.HOLIDAY_ID
				+ "=" + hldy.getID(), null);
	}

	public Holiday addHoliday(Holiday hldy)
	{
		ContentValues values = new ContentValues();

		values.put(CalendarWrapper.HOLIDAY_TITLE, hldy.getHoliday());
		values.put(CalendarWrapper.HOLIDAY_DATE, hldy.getHoliday_date());
		values.put(CalendarWrapper.HOLIDAY_TYPE, hldy.getType());

		database.insert(CalendarWrapper.HOLIDAYS, null, values);

		return hldy;
	}
	private Holiday parseHoliday(Cursor cursor)
	{
		Holiday hldy = new Holiday();
		hldy.setHoliday((cursor.getString(0)));
		hldy.setHoliday_date(cursor.getLong(1));
		hldy.setType(cursor.getInt(2));
		hldy.setID(cursor.getInt(3));
		return hldy;
	}

	public List getAllHolidays()
	{
		List holidays = new ArrayList();
		Cursor cursor = database.query(CalendarWrapper.HOLIDAYS,
				HOLIDAY_TABLE_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Holiday holiday = parseHoliday(cursor);
			holidays.add(holiday.getHoliday());
			cursor.moveToNext();
		}
		cursor.close();

		return holidays;
	}
	public List getHolidays(long start, long end)
	{
		List holidays = new ArrayList();
		String whereClause = CalendarWrapper.HOLIDAY_DATE + ">=" + start
				+ "AND" + CalendarWrapper.HOLIDAY_DATE + "<=" + end;
		Cursor cursor = database.query(CalendarWrapper.HOLIDAYS,
				HOLIDAY_TABLE_COLUMNS, whereClause, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Holiday holiday = parseHoliday(cursor);
			holidays.add(holiday.getHoliday());
			cursor.moveToNext();
		}
		cursor.close();

		return holidays;
	}
	// ///////////////////////////////////////////////////
	// /// EVENT TABLE SECTION
	// ///////////////////////////////////////////////////

	private Event parseEvent(Cursor cursor)
	{
		Event event = new Event();
		event.setId(cursor.getInt(0));
		event.setTitle(cursor.getString(1));
		event.setStart_day(cursor.getString(2));
		event.setEnd_day(cursor.getString(3));
		event.setStart_time(cursor.getString(4));
		event.setEnd_time(cursor.getString(5));
		event.setWeek_repeat(cursor.getInt(6));
		event.setMonth_repeat(cursor.getInt(7));
		event.setYear_repeat(cursor.getInt(8));
		event.setLocation(cursor.getString(9));
		event.setDescription(cursor.getString(10));
		event.setEvent_category(cursor.getInt(11));
		return event;
	}

	public List<Event> getEvents(String start, String end)
	{
		List<Event> events = new ArrayList<Event>();
		String whereClause = CalendarWrapper.EVENT_START_DAY + ">=" + start
				+ "AND" + CalendarWrapper.EVENT_END_DAY + "<=" + end;
		Cursor cursor = database.query(CalendarWrapper.EVENTS,
				EVENT_TABLE_COLUMNS, whereClause, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Event event = parseEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		cursor.close();

		return events;
	}
	public List<Event> getEventsByDate(String currentDay)
	{
		List<Event> events = new ArrayList<Event>();
		String whereClause = CalendarWrapper.EVENT_START_DAY + ">=" +currentDay;
		Cursor cursor = database.query(CalendarWrapper.EVENTS,
				EVENT_TABLE_COLUMNS, whereClause, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Event event = parseEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		cursor.close();

		return events;
	}
	public List<Event> getAllEvents()
	{
		List<Event> events = new ArrayList<Event>();
		Cursor cursor = database.query(CalendarWrapper.EVENTS,
				EVENT_TABLE_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Event event = parseEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		cursor.close();

		return events;
	}

	public List<Event> getEventById(String eventID)
	{
		List<Event> events = new ArrayList<Event>();
		String whereClause = CalendarWrapper.EVENT_ID + "=" + eventID;
		Cursor cursor = database.query(CalendarWrapper.EVENTS,
				EVENT_TABLE_COLUMNS, whereClause, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Event event = parseEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		cursor.close();

		return events;
	}
	public Event addEvent(Event event)
	{
		ContentValues values = new ContentValues();
		values.put(CalendarWrapper.EVENT_TITLE, event.getTitle());
		values.put(CalendarWrapper.EVENT_START_DAY, event.getStart_day());
		values.put(CalendarWrapper.EVENT_END_DAY, event.getEnd_day());
		values.put(CalendarWrapper.EVENT_START_TIME, event.getStart_time());
		values.put(CalendarWrapper.EVENT_END_TIME, event.getEnd_time());
		values.put(CalendarWrapper.EVENT_WK_REPEAT, event.getWeek_repeat());
		values.put(CalendarWrapper.EVENT_MNTH_REPEAT, event.getMonth_repeat());
		values.put(CalendarWrapper.EVENT_YR_REPEAT, event.getYear_repeat());
		values.put(CalendarWrapper.EVENT_LCTN, event.getLocation());
		values.put(CalendarWrapper.EVENT_DESC, event.getDescription());
		values.put(CalendarWrapper.EVENT_CTGRY, event.getEvent_category());

		database.insert(CalendarWrapper.EVENTS, null, values);

		return event;
	}
	public Event updateEvent(Event event)
	{
		ContentValues values = new ContentValues();

		values.put(CalendarWrapper.EVENT_TITLE, event.getTitle());
		values.put(CalendarWrapper.EVENT_START_DAY, event.getStart_day());
		values.put(CalendarWrapper.EVENT_END_DAY, event.getEnd_day());
		values.put(CalendarWrapper.EVENT_START_TIME, event.getStart_time());
		values.put(CalendarWrapper.EVENT_END_TIME, event.getEnd_time());
		values.put(CalendarWrapper.EVENT_WK_REPEAT, event.getWeek_repeat());
		values.put(CalendarWrapper.EVENT_MNTH_REPEAT, event.getMonth_repeat());
		values.put(CalendarWrapper.EVENT_YR_REPEAT, event.getYear_repeat());
		values.put(CalendarWrapper.EVENT_LCTN, event.getLocation());
		values.put(CalendarWrapper.EVENT_DESC, event.getDescription());
		values.put(CalendarWrapper.EVENT_CTGRY, event.getEvent_category());

		database.update(CalendarWrapper.EVENTS, values,
				CalendarWrapper.EVENT_ID + "=" + event.getId(), null);

		return event;
	}
	public void removeEvent(int id)
	{
		database.delete(CalendarWrapper.EVENTS, CalendarWrapper.EVENT_ID + "="
				+ id, null);
	}
	// ///////////////////////////////////////////////////
	// /// CATEGORY TABLE SECTION
	// ///////////////////////////////////////////////////

	private Category parseCategory(Cursor cursor)
	{
		Category category = new Category();
		category.setId(cursor.getInt(0));
		category.setName(cursor.getString(1));
		category.setColor(cursor.getInt(2));
		return category;
	}

	public List getCategories()
	{
		List categories = new ArrayList();
		Cursor cursor = database.query(CalendarWrapper.CATEGORIES,
				CATEGORY_TABLE_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Event event = parseEvent(cursor);
			categories.add(event);
			cursor.moveToNext();
		}
		cursor.close();

		return categories;
	}

	public Category addCategory(Category category)
	{
		ContentValues values = new ContentValues();

		values.put(CalendarWrapper.CATEGORY_NAME, category.getName());
		values.put(CalendarWrapper.CATEGORY_COLOR, category.getColor());

		database.insert(CalendarWrapper.CATEGORIES, null, values);

		return category;
	}

	public Category updateEvent(Category category)
	{
		ContentValues values = new ContentValues();

		values.put(CalendarWrapper.CATEGORY_NAME, category.getName());
		values.put(CalendarWrapper.CATEGORY_COLOR, category.getColor());

		database.update(CalendarWrapper.CATEGORIES, values,
				CalendarWrapper.CATEGORY_ID + "=" + category.getId(), null);

		return category;
	}

	public void removeCategory(Category category)
	{
		database.delete(CalendarWrapper.CATEGORIES, CalendarWrapper.CATEGORY_ID
				+ "=" + category.getId(), null);
	}
}
