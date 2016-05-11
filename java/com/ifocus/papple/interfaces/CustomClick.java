package com.ifocus.papple.interfaces;

import android.view.View;

import com.ifocus.papple.adapter.EventsViewAdapter;
import com.ifocus.papple.adapter.ResolvedEventsViewAdapter;

/**
 * Created by iFocus_2 on 18-01-2016.
 */
public interface CustomClick {
    // TODO: Update argument type and name
    void onCustomClick(int position, View view, EventsViewAdapter.ViewHolder holder);
    // TODO: Update argument type and name
    void onCustomClick(int position, View view, ResolvedEventsViewAdapter.ViewHolder holder);
}
