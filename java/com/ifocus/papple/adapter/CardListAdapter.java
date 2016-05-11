package com.ifocus.papple.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ifocus.papple.fragments.FinishedEvent;
import com.ifocus.papple.fragments.UpcomingEvent;

public class CardListAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CardListAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UpcomingEvent tab1 = new UpcomingEvent();
                return tab1;
            case 1:
                FinishedEvent tab2 = new FinishedEvent();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}