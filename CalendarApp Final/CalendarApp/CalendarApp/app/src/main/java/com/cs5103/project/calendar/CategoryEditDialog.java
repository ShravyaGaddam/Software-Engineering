package com.cs5103.project.calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by anil on 10/11/15.
 */
public class CategoryEditDialog extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.category_edit_layout, null);


        getDialog().setTitle("Update Category");

//        final Bundle arg = getArguments();
        final CalendarOperations gco = new CalendarOperations(getActivity());
        gco.open();

        Category category = gco.getCategories(CalendarWrapper.CATEGORY_ID + "=" + categoryId).get(0);


        final EditText edtCategoryname = (EditText) view.findViewById(R.id.edtCategoryname);
        edtCategoryname.setText(category.getName() + "");

        Button btnUpdate = (Button) view.findViewById(R.id.btnupdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category mCategory = new Category();
                mCategory.setName(edtCategoryname.getText().toString());
                mCategory.setId(categoryId);
                mCategory.setColor(mcolor);
                gco.updateCategory(mCategory);


                if (mCategoryAdapter != null)
                    mCategoryAdapter.updateReceiptsList(gco.getCategories());
                dismiss();
            }
        });

        return view;

    }

    private static String mcategoryname, mcolor;
    private static int categoryId;
    private static CategoryAdapter mCategoryAdapter;

    public static CategoryEditDialog newInstance(CategoryAdapter categoryAdapter, String color, String name, int id) {
        CategoryEditDialog dlg = new CategoryEditDialog();
        mcategoryname = name;
        categoryId = id;
        mcolor = color;
        mCategoryAdapter = categoryAdapter;
        return dlg;
    }
}
