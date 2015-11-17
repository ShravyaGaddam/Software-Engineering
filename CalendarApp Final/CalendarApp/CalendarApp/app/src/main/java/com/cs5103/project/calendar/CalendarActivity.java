package com.cs5103.project.calendar;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.Calendar;

public class CalendarActivity extends FragmentActivity implements
        ActionBar.OnNavigationListener, IEventDataChenged {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final int DISPLAY_MONTH = 0;
    private static final int DISPLAY_WEEK = 1;
    private static final int DISPLAY_DAY = 2;
    private MonthFragment mMonthFragment = new MonthFragment();
    private WeekFragment mWeekFragment = new WeekFragment();
    private DayFragment mDayFragment = new DayFragment();

    private int mActiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Calendar mToday = CalendarData.get(getApplicationContext()).getToday();
        CalendarData.get(getApplicationContext()).setDateCurrent(mToday);
        CalendarData.get(getApplicationContext()).setDateSelected(mToday);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(getActionBarThemedContextCompat(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, new String[]{
                        getString(R.string.menuMonth),
                        getString(R.string.menuWeek),
                        getString(R.string.menuDay),}), this);
    }

    /**
     * Backward-compatible version of {@link ActionBar#getThemedContext()} that
     * simply returns the {@link android.app.Activity} if
     * <code>getThemedContext</code> is unavailable.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Context getActionBarThemedContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getActionBar().getThemedContext();
        } else {
            return this;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean mOption = false;
        switch (item.getItemId()) {
            case R.id.addEvent: {
                mOption = true;
                showEvent();
                break;
            }
            case R.id.action_settings: {
                mOption = true;
                showSettings();
                break;
            }
            case R.id.showAgenda: {
                mOption = true;
                showAgenda();
                break;
            }
            case R.id.showCategories: {
                Intent categoryIntent= new Intent(this,CategoryActivity.class);
                startActivity(categoryIntent);
                break;
            }
            default:
                mOption = super.onOptionsItemSelected(item);
        }
        return mOption;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        Fragment fragment = null;
        boolean mValidNavigation = false;

        switch (position) {
            case DISPLAY_MONTH: {
                fragment = mMonthFragment;
                mActiveFragment = DISPLAY_MONTH;
                mValidNavigation = true;
                break;
            }
            case DISPLAY_WEEK: {
                fragment = mWeekFragment;
                mActiveFragment = DISPLAY_WEEK;
                mValidNavigation = true;
                break;
            }
            case DISPLAY_DAY: {
                fragment = mDayFragment;
                mActiveFragment = DISPLAY_DAY;
                mValidNavigation = true;
                break;
            }
            default:
                mValidNavigation = false;
        }
        if (mValidNavigation) {
            Bundle args = new Bundle();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        return mValidNavigation;
    }

    public void showSettings() {
        DialogFragment settingsDialog = SettingsDialog.newInstance(R.string.settings);
        settingsDialog.show(getFragmentManager(), "settingsDialog");
    }

    public void settingsOK() {
        if (mActiveFragment == DISPLAY_MONTH)
            mMonthFragment.update();
        if (mActiveFragment == DISPLAY_WEEK)
            mWeekFragment.update();
    }

    public void settingsCancel() {
    }

    public void showAgenda() {
        DialogFragment agendaDialog = AgendaDialog.newInstance(R.string.agenda);
        agendaDialog.show(getFragmentManager(), "agendaDialog");
    }

    public void showEvent() {
        DialogFragment eventDialog = EventDialog.newInstance(R.string.eventNameHeader, 0, null, null, null, null, null, null, null, -1);
        eventDialog.show(getFragmentManager(), "eventDialog");
    }

    @Override
    public void changeInEventData(){
        switch (mActiveFragment){
            case DISPLAY_DAY:
                mDayFragment.update();
                break;
            case DISPLAY_MONTH:
                mMonthFragment.update();
                break;
            case DISPLAY_WEEK:
                mWeekFragment.update();
                break;
        }
    }

}
