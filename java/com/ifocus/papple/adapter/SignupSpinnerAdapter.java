package com.ifocus.papple.adapter;

/**
 * Created by iFocus_2 on 20-01-2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifocus.papple.R;


/**
 * Created by Abhishek on 20-01-2016.
 */
public class SignupSpinnerAdapter extends ArrayAdapter<String> {

    private String[] items = {};
    private Context context;

    public SignupSpinnerAdapter(Context context, int resource, String[] items) {
        super(context, resource);
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public String getItem(int position) {
        return items[position];
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent, boolean isView) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.signup_list_item, parent, false);
        if (isView) {
            layout.setBackgroundColor(Color.TRANSPARENT);
        }
        TextView tv = (TextView) layout.findViewById(R.id.signupListItemTextView);
        tv.setText(items[position]);

        return layout;
    }


}
