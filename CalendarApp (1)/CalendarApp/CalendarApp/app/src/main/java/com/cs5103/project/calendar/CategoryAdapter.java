package com.cs5103.project.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**Class name CategoryAdapter
 *
 */
public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Category getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = View.inflate(context, R.layout.category_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.categoryName.setText(categoryList.get(position).getName());
        viewHolder.colorView.setBackgroundColor(Color.parseColor(categoryList.get(position).getColor()));




        return convertView;
    }

    /**Method name updateReceiptsList
     *
     * @param newlist
     */
    public void updateReceiptsList(List<Category> newlist) {
        this.categoryList.clear();
        this.categoryList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    /**Class name ViewHolder
     *
     *
     */
    class ViewHolder {
        public ViewHolder(View v) {
            categoryName = (TextView) v.findViewById(R.id.txtCategoryName);
            colorView = v.findViewById(R.id.colorView);
        }

        TextView categoryName;
        View colorView;
    }

}
