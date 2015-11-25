package com.cs5103.project.calendar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 *
 *
 */
public class CalendarWrapper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Calendar.db";

    public static final String EVENTS = "events";
    public static final String HOLIDAYS = "holidays";
    public static final String CATEGORIES = "categories";

    public static final String EVENT_ID = "id";
    public static final String EVENT_START_DAY = "eventstart";
    public static final String EVENT_END_DAY = "eventend";
    public static final String EVENT_START_TIME = "eventstarttime";
    public static final String EVENT_END_TIME = "eventendtime";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_DESC = "desc";
    public static final String EVENT_WK_REPEAT = "weekrepeat";
    public static final String EVENT_MNTH_REPEAT = "monthrepeat";
    public static final String EVENT_YR_REPEAT = "yearrepeat";
    public static final String EVENT_LCTN = "location";
    public static final String EVENT_CTGRY = "category";

    public static final String CATEGORY_NAME = "_categoryname";
    public static final String CATEGORY_ID = "_categoryid";
    public static final String CATEGORY_COLOR = "_color";

    public static final String HOLIDAY_TITLE = "holidayname";
    public static final String HOLIDAY_DATE = "datestart";
    public static final String HOLIDAY_TYPE = "type";
    public static final String HOLIDAY_ID = "id";

    private String[] HOLIDAY_TABLE_COLUMNS =
            {CalendarWrapper.HOLIDAY_TITLE, CalendarWrapper.HOLIDAY_DATE,
                    CalendarWrapper.HOLIDAY_TYPE};

    private static final String EVENT_TABLE_CREATE = "create table " + EVENTS
            + "(" + EVENT_ID + " integer primary key autoincrement, "
            + EVENT_START_DAY + " text, " + EVENT_END_DAY + " text, "
            + EVENT_START_TIME + " text, " + EVENT_END_TIME + " text, "
            + EVENT_TITLE + " text not null, " + EVENT_DESC + " text, "
            + EVENT_WK_REPEAT + " integer default 0, " + EVENT_MNTH_REPEAT
            + " integer default 0, " + EVENT_YR_REPEAT + " integer default 0, "
            + EVENT_LCTN + " text, " + EVENT_CTGRY + " integer default -1 "
            + ", unique("+EVENT_START_DAY+", "+EVENT_START_TIME+"));";

    private static final String CATEGORY_TABLE_CREATE = "create table "
            + CATEGORIES + "(" + CATEGORY_ID
            + " integer primary key autoincrement, " + CATEGORY_NAME
            + " text, " + CATEGORY_COLOR + " text "
            + ");";

    private static final String HOLIDAY_TABLE_CREATE = "create table "
            + HOLIDAYS + "(" + HOLIDAY_ID
            + " integer primary key autoincrement, " + HOLIDAY_TITLE
            + " text, " + HOLIDAY_DATE + " bigint, " + HOLIDAY_TYPE
            + " integer);";

    private String HOLIDAY_TABLE_STRING = "(" + HOLIDAY_TITLE + ","
            + HOLIDAY_DATE + "," + HOLIDAY_TYPE + ")";
    private String EVENT_TABLE_STRING = "(" + EVENT_TITLE + ","
            + EVENT_START_DAY + "," + EVENT_END_DAY + "," + EVENT_DESC + ","
            + EVENT_WK_REPEAT + "," + EVENT_MNTH_REPEAT + "," + EVENT_YR_REPEAT
            + "," + EVENT_LCTN + "," + EVENT_CTGRY + "," + ")";
    private String CATEGORY_TABLE_STRING = "(" + CATEGORY_NAME + ","
            + CATEGORY_COLOR + ")";

    public CalendarWrapper(Context context, String name, CursorFactory factory,
                           int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    public CalendarWrapper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(EVENT_TABLE_CREATE);
            db.execSQL(CATEGORY_TABLE_CREATE);

//            //insert categories
//            for (Category category : CalendarData.getColors()) {
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(CATEGORY_COLOR, category.getColor());
//                contentValues.put(CATEGORY_NAME, category.getName());
//                db.insert(CATEGORY_TABLE_CREATE, null, contentValues);
//
//
//            }

            db.execSQL(HOLIDAY_TABLE_CREATE);


            enter2014Holdays(db);
        } catch (SQLException e) {
            Log.e("SQLException", "CalendarWrapper.java : onCreate()", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);
            db.execSQL("DROP TABLE IF EXISTS " + HOLIDAYS);
            onCreate(db);
        } catch (SQLException e) {
            Log.e("SQLException", "CalendarWrapper.java : onUpgrade()", e);
        }
    }

    /** Method to populate remaining 2014 USA Holidays
     *
     * @param db
     */
    private void enter2014Holdays(SQLiteDatabase db) {
        GregorianCalendar temp = new GregorianCalendar();
        long longDate;

        try {
            // Thanksgiving
            temp.set(2014, 10, 27);
            longDate = temp.getTimeInMillis();
            db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING
                    + " VALUES ('Thanksgiving','" + longDate + "' , '-1')");

            // Christmas
            temp.set(2014, 11, 25);
            longDate = temp.getTimeInMillis();
            db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING
                    + " VALUES ('Christmas','" + longDate + "' , '-1')");

            // New Years
            temp.set(2015, 0, 01);
            longDate = temp.getTimeInMillis();
            db.execSQL("INSERT INTO Holidays" + HOLIDAY_TABLE_STRING
                    + " VALUES ('New Years','" + longDate + "' , '-1')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
