package com.ifocus.papple.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifocus.papple.R;
import com.ifocus.papple.activity.ContractorHomeActivity;
import com.ifocus.papple.activity.HomeActivity;
import com.ifocus.papple.adapter.EventsViewAdapter;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment implements View.OnClickListener {


    private DBUserDetailsHelper dbHelper;
    private OnFragmentInteractionListener mListener;
    private View view;
    private HomeActivity homeActivity;
    private ContractorHomeActivity contractorHomeActivity;
    private CardView announcementCard, alertCard, peopleSpeakCard, videoCard, aboutCard, wardDetailsCard;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            homeActivity = (HomeActivity) getActivity();
            contractorHomeActivity = (ContractorHomeActivity) getActivity();
            dbHelper = new DBUserDetailsHelper(getActivity(), null, null, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        announcementCard = (CardView) view.findViewById(R.id.announcementCard);
        alertCard = (CardView) view.findViewById(R.id.alertCard);
        peopleSpeakCard = (CardView) view.findViewById(R.id.peopleSpeakCard);
        videoCard = (CardView) view.findViewById(R.id.videoCard);
        aboutCard = (CardView) view.findViewById(R.id.aboutCard);
        wardDetailsCard = (CardView) view.findViewById(R.id.wardDetailsCard);

        announcementCard.setOnClickListener(this);
        alertCard.setOnClickListener(this);
        peopleSpeakCard.setOnClickListener(this);
        videoCard.setOnClickListener(this);
        aboutCard.setOnClickListener(this);
        wardDetailsCard.setOnClickListener(this);
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
    /**
     * Changing the fragment inside Fragment layout
     *//**//**/
    public void changeFragment(Fragment targetFragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.announcementCard:
                changeFragment(new ViewAnnouncement(), getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.announcement));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.announcement));
                break;
            case R.id.alertCard:
                changeFragment(new ViewAnnouncement(), getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.home));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.home));
                break;
            case R.id.peopleSpeakCard:
                HomeFragment frag = new HomeFragment();
                changeFragment(frag, getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.home));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.home));
                break;
            case R.id.videoCard:
                changeFragment(new ViewAnnouncement(), getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.home));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.home));
                break;
            case R.id.aboutCard:
                changeFragment(new ViewAnnouncement(), getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.home));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.home));
                break;
            case R.id.wardDetailsCard:
                changeFragment(new ViewAnnouncement(), getString(R.string.home));
                if (homeActivity != null)
                    homeActivity.changeTitle(getString(R.string.home));

                if (contractorHomeActivity != null)
                    contractorHomeActivity.changeTitle(getString(R.string.home));
                break;


        }
    }
}
