package uk.co.vism.wordbox;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import uk.co.vism.wordbox.fragments.DiscoverFragment;
import uk.co.vism.wordbox.fragments.DiscoverFragment_;
import uk.co.vism.wordbox.fragments.EarnFragment;
import uk.co.vism.wordbox.fragments.EarnFragment_;
import uk.co.vism.wordbox.fragments.YoursFragment;
import uk.co.vism.wordbox.fragments.YoursFragment_;
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
        adapter = new ViewPagerAdapter(HomeActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(adapter);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        ActionBar.Tab tab;

        tab = adapter.addTab(bar.newTab().setText(DiscoverFragment.NAME), DiscoverFragment_.class, null);
        bar.addTab(tab);
        tab = adapter.addTab(bar.newTab().setText(EarnFragment.NAME), EarnFragment_.class, null);
        bar.addTab(tab);
        tab = adapter.addTab(bar.newTab().setText(YoursFragment.NAME), YoursFragment_.class, null);
        bar.addTab(tab);

        viewPager.setCurrentItem(1);
        adapter.notifyDataSetChanged();

        downloadUser();
    }

    @Background
    void downloadUser()
    {
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