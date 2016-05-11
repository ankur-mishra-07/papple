package com.ifocus.papple.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.adapter.CardListAdapter;
import com.ifocus.papple.interfaces.IUserAuthApi;
import com.ifocus.papple.model.Announcement;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAnnouncement extends Fragment implements TabLayout.OnTabSelectedListener, Callback<List<Announcement>> {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CardListAdapter adapter;
    private IUserAuthApi iUserAuthApi;
    private View view;
    private List<Announcement> data;
    // Retrofit object for initializing the base url
    private Retrofit retrofit;
    private Activity activity;
    private Dialog dialog;

    public ViewAnnouncement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_announcement, container, false);
        initViews();
        initListeners();
        if (AndroidUtils.isNetworkAvailable(activity)) {
            callRetriveData();
        } else {
        }

        return view;
    }

    private void callRetriveData() {
        try {
            initAdapter();
           /* //RetroFit Creation
            retrofit = RetroService.returnRestadapter(getActivity());
            retrofit = RetroService.returnRestadapter(getActivity());
            iUserAuthApi = retrofit.create(IUserAuthApi.class);
            Call<List<Announcement>> call = iUserAuthApi.getAnnouncement();
            call.enqueue(this);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    private void initViews() {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.upcomingAnnouncement)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.done)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param tab The tab that was reselected.
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * Successful HTTP response.
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<List<Announcement>> response, Retrofit retrofit) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initAdapter() {
        adapter = new CardListAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
    }

    /**
     * Invoked when a network or unexpected exception occurred during the HTTP request.
     *
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {

    }
}
