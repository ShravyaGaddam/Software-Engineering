package com.cs5103.project.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CategoryActivity extends FragmentActivity {
    private EditText edtCategory;
    private View viewColor;
    private ListView listView;
    private List<Category> categoryList;
    private CalendarOperations gco;
    private CategoryAdapter categoryAdapter;
    private final int initialColor = 0x00ff00;
    private String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        edtCategory = (EditText) findViewById(R.id.edt_category);
        viewColor = findViewById(R.id.viewColor);
        listView = (ListView) findViewById(R.id.mListView);
        gco = new CalendarOperations(getApplicationContext());
        gco.open();
        categoryList = gco.getCategories();
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList);
        registerForContextMenu(listView);
        if (categoryList.size() > 0) {
            listView.setAdapter(categoryAdapter);
            listView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
        selectedColor = String.format("#%06X", initialColor);
    }

    private AmbilWarnaDialog ambilWarnaDialog;
    public void pickColor(View v) {
        ambilWarnaDialog = new AmbilWarnaDialog(CategoryActivity
                .this, initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                selectedColor = String.format("#%06X", color);
                viewColor.setBackgroundColor(Color.parseColor(selectedColor));
            }
        });
        ambilWarnaDialog.show();
    }

    public void addCategory(View v) {
        //notify;
        Category category = new Category();
        category.setName(edtCategory.getText().toString().trim());
        category.setColor(selectedColor);
        gco.addCategory(category);
        edtCategory.getText().clear();
        categoryList.clear();
        categoryList = gco.getCategories();
        categoryAdapter.updateReceiptsList(categoryList);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (edtCategory != null)
            imm.hideSoftInputFromWindow(edtCategory.getWindowToken(), 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 1, 0, "Edit");
        menu.add(1, 2, 1, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CategoryActivity.this);
        switch (item.getItemId()) {
            case 1://edit
                // AlertDialog to delete the event
                alertDialogBuilder.setTitle("Update Category");
                alertDialogBuilder
                        .setMessage(
                                "Do you want to update the selected category?")
                        .setPositiveButton(R.string.deleteEvent,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        CategoryEditDialog categoryEditDialog = CategoryEditDialog.newInstance(categoryAdapter, categoryAdapter.getItem(position).getColor(), categoryAdapter.getItem(position).getName(), categoryAdapter.getItem(position).getId());
                                        categoryEditDialog.show(getSupportFragmentManager(), "categoryEditDialog");
                                        dialog.dismiss();
                                        categoryList.clear();
                                        categoryList = gco.getCategories();
                                        categoryAdapter.updateReceiptsList(categoryList);
                                    }
                                })
                        .setNegativeButton(R.string.buttonCancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
            case 2://delete
// AlertDialog to delete the event
                alertDialogBuilder.setTitle("Delete Category");
                alertDialogBuilder
                        .setMessage(
                                "Selected category will be deleted. Are you sure you want to delete this category?")
                        .setPositiveButton(R.string.deleteEvent,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // delete the event based on event id passed
                                        gco.removeCategory(categoryAdapter.getItem(position));
                                        categoryList.clear();
                                        categoryList = gco.getCategories();
                                        categoryAdapter.updateReceiptsList(categoryList);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(R.string.buttonCancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog1 = alertDialogBuilder.create();
                // show it
                alertDialog1.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gco != null)
            gco.close();
    }
}
