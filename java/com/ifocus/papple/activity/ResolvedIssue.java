package com.ifocus.papple.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ifocus.papple.R;
import com.ifocus.papple.fragments.ResolvedFragment;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;
import com.ifocus.papple.model.PostResult;

public class ResolvedIssue extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolved_issue);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeFragment(new ResolvedFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Changing the fragment inside Fragment layout
     */
    public void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(PostResult item, View view) {

    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            finish();
            //additional code
        } else if (count == 1) {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }


    }
}
