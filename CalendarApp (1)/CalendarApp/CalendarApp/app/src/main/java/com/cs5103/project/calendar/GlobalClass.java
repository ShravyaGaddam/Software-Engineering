package com.cs5103.project.calendar;

import android.app.Application;

/**
 *
 *
 */

public class GlobalClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalendarOperations gCO = new CalendarOperations(this);
        gCO.open();

        if (gCO.getCategories().size() == 0) {
//            Category category = new Category();
//            category.setName();
//            category.setColor();
//            gCO.addCategory();

        }


    }
}
