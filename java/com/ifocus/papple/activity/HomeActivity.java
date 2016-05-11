package com.ifocus.papple.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.ifocus.papple.R;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.fragments.AboutFragment;
import com.ifocus.papple.fragments.Dashboard;
import com.ifocus.papple.fragments.EventCreation;
import com.ifocus.papple.fragments.HomeFragment;
import com.ifocus.papple.fragments.Roles;
import com.ifocus.papple.helpers.Utils;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;
import com.ifocus.papple.model.PostResult;
import com.ifocus.papple.model.UserDetails;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, View.OnClickListener, android.support.v4.app.FragmentManager.OnBackStackChangedListener {
    private String userid;
    //    private TextView textView;
    private DBUserDetailsHelper helper;
    private UserDetails userDetails;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        callDashboard();
    }

    private void callDashboard() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.main_fragment, new Dashboard(), "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void initViews() {
        userid = getIntent().getStringExtra("userid");
//        textView = (TextView) findViewById(R.id.signedInText);
        helper = new DBUserDetailsHelper(this, null, null, 1);
//        userDetails = helper.getUserDetail(DBUserDetailsHelper.USER_COLUMN_ID, userid);
//        textView.setText(getString(R.string.welcome) + userDetails.getFirstName() + " " + userDetails.getLastName());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                finish();
                //additional code
            } else if (count == 1) {
                getFragmentManager().popBackStack();
                finish();
            } else {
                super.onBackPressed();
            }
            // getSupportFragmentManager().executePendingTransactions();
        }
        getSupportActionBar().setTitle(getString(R.string.home));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    public void changeTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            callDashboard();
            getSupportActionBar().setTitle(getString(R.string.home));
        } else if (id == R.id.events) {
            changeFragment(new EventCreation(), getString(R.string.addEvents));
            getSupportActionBar().setTitle(getString(R.string.addEvents));
        } else if (id == R.id.roles) {
            changeFragment(new Roles(), getString(R.string.addRoles));
            getSupportActionBar().setTitle(getString(R.string.addRoles));
        } else if (id == R.id.status) {
            HomeFragment frag = new HomeFragment();
            changeFragment(frag, getString(R.string.home));
            getSupportActionBar().setTitle(getString(R.string.home));
        } else if (id == R.id.about) {
            changeFragment(new AboutFragment(), getString(R.string.about));
            getSupportActionBar().setTitle(getString(R.string.about));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * Changing the fragment inside Fragment layout
     */
    public void changeFragment(Fragment targetFragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // getSupportFragmentManager().executePendingTransactions();
        // getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    /**
     * Floating button instance
     */
    public FloatingActionButton getFAB() {
        return fab;
    }


    @Override
    public void onListFragmentInteraction(PostResult item, View view) {
        Snackbar.make(view, item.getUSERNAME(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Utils.doIntent(this, ResolvedIssue.class);
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName());
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
            if (backStackEntryCount != 0) {
                getSupportActionBar().setTitle(getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName());
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

}
