package com.ifocus.papple.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.adapter.EventsViewAdapter;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;
import com.ifocus.papple.interfaces.iFeedData;
import com.ifocus.papple.manager.Eventmanager;
import com.ifocus.papple.manager.FeedManager;
import com.ifocus.papple.model.FeedData;

import java.util.HashMap;
import java.util.Map;

import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment implements IRetroTaskCompleteListener, iFeedData {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private DBUserDetailsHelper dbHelper;
    private OnFragmentInteractionListener mListener;
    /**
     * Interface for result
     */
    private IRetroTaskCompleteListener taskCompleted;

    /**
     * Interface for FeedData
     */
    private iFeedData ifeed;

    // Retrofit object for initializing the base url
    private Retrofit retrofit;

    /**
     * PostParams
     */
    private Map<String, String> postParams = new HashMap<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private  RecyclerView recyclerView;

    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dbHelper = new DBUserDetailsHelper(getActivity(), null, null, 1);
            if (getArguments() != null) {
                mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            }

            //RetroFit Creation
            retrofit = RetroService.returnRestadapter(getActivity());
            taskCompleted = this;
            ifeed = this;
            getFeedList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFeedList() {

        if (AndroidUtils.isNetworkAvailable(getActivity())) {
            //Making Retro Fit call
            getActivity().setProgressBarIndeterminateVisibility(true);
            new FeedManager(getActivity(), Constant.GET_FEED, taskCompleted, retrofit, postParams, true, ifeed);

        } else {
            AndroidUtils.showToast(getString(R.string.networkError), getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResponseLanded(int serviceCode, String Response) {

    }

    @Override
    public void onFailureLanded(int serviceCode, String Failure) {

    }

    @Override
    public void onFeedLanded(FeedData feedData) {
        if(feedData.getData()!=null && feedData.getData().size()>0) {
            recyclerView.setAdapter(new EventsViewAdapter(feedData.getData(),dbHelper.getAllPosts(), mListener, getActivity()));
        }
//        }else{
//            recyclerView.setAdapter(new EventsViewAdapter(dbHelper.getAllPosts(), mListener,getActivity()));
//        }
    }
}
