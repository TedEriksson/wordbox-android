package uk.co.vism.wordbox.activities;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.ViewPagerAdapter;
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
public class HomeActivity extends FragmentActivity
{
    @ViewById(R.id.viewPager)
    ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @AfterViews
    void init()
    {
        adapter = new ViewPagerAdapter(viewPager, HomeActivity.this, getSupportFragmentManager());
        setupActionBar();

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(adapter);
        viewPager.setCurrentItem(1);

        adapter.notifyDataSetChanged();

        //downloadUser();
    }

    private void setupActionBar()
    {
        ActionBar.Tab tab;

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        tab = adapter.addTab(bar.newTab().setText(BoxesFragment.NAME), BoxesFragment_.class, null);
        bar.addTab(tab);
        tab = adapter.addTab(bar.newTab().setText(FriendsFragment.NAME), FriendsFragment_.class, null);
        bar.addTab(tab);
        tab = adapter.addTab(bar.newTab().setText(MineFragment.NAME), MineFragment_.class, null);
        bar.addTab(tab);
    }

    @Click
    public void addNewBox()
    {
        CreateBoxActivity_.intent(HomeActivity.this).start();
    }

    @Background
    void downloadUser()
    {
        Realm.deleteRealmFile(this);

        // This is the database instance
        Realm realm = Realm.getInstance(this);

        // Update user on app launch (this will get all info about them
        RestClientManager.updateUser(this, realm, 1);

        // Get updated User from database.
        User user = UserManager.getUserById(realm, 1);
        // Showing that the User is usable
        Log.d("eeee", "Friend id: " + user.getFriends().get(0).getId());

        // MUST close the instance when finished with it
        realm.close();
    }
}