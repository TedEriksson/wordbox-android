package uk.co.vism.wordbox.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.ViewPagerAdapter;
import uk.co.vism.wordbox.fragments.BoxesFragment;

import uk.co.vism.wordbox.fragments.BoxesFragment_;
import uk.co.vism.wordbox.fragments.FriendsFragment;

import uk.co.vism.wordbox.fragments.FriendsFragment_;
import uk.co.vism.wordbox.fragments.MineFragment;

import uk.co.vism.wordbox.fragments.MineFragment_;
import uk.co.vism.wordbox.managers.RestClientManager;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.User;

@EActivity(R.layout.activity_home)
public class HomeActivity extends ActionBarActivity
{
    @ViewById(R.id.tabs)
    PagerSlidingTabStrip tabs;

    @ViewById(R.id.viewPager)
    ViewPager viewPager;

    @ViewById(R.id.requestRow)
    RelativeLayout requestRow;

    @ViewById(R.id.requestCount)
    TextView requestCount;

    private ViewPagerAdapter adapter;

    @AfterViews
    void init()
    {


        adapter = new ViewPagerAdapter(viewPager, HomeActivity.this, getSupportFragmentManager());

        // if there are any pending requests
        if(true)
            requestCount.setText("You have 2 requests pending");
        else
            requestRow.setVisibility(LinearLayout.GONE);

        setupViewPager();
        downloadUser();
    }

    private void setupViewPager()
    {
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
    }

    @Click
    public void addNewBox()
    {
        CreateBoxActivity_.intent(HomeActivity.this).start();
    }

    @Click
    public void viewRequests()
    {
        RequestsActivity_.intent(HomeActivity.this).start();
    }

    @Background
    void downloadUser()
    {
        Realm.deleteRealmFile(this);

        // try with resources - the .close() method will automatically be called after the code block terminates
        try(Realm realm = Realm.getInstance(this))
        {
            // Update user on app launch (this will get all info about them)
            RestClientManager.updateUser(this, realm, 1);

            // Get updated User from database
            User user = UserManager.getUserById(realm, 1);

            // Showing that the User is usable
            Log.d("eeee", "Friend id: " + user.getFriends().get(0).getId());
            Log.d("eeee", "sentence: " + user.getSentences().get(0).getId());
        }
    }
}