package uk.co.botondbutuza.wordbox;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import uk.co.botondbutuza.wordbox.fragments.DiscoverFragment;
import uk.co.botondbutuza.wordbox.fragments.DiscoverFragment_;
import uk.co.botondbutuza.wordbox.fragments.EarnFragment;
import uk.co.botondbutuza.wordbox.fragments.EarnFragment_;
import uk.co.botondbutuza.wordbox.fragments.YoursFragment;
import uk.co.botondbutuza.wordbox.fragments.YoursFragment_;
import uk.co.botondbutuza.wordbox.managers.RestClientManager;
import uk.co.botondbutuza.wordbox.models.User;

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

        background();
    }

    @Background
    void background()
    {
        // Update user on app launch (this will get all info about them
        RestClientManager.updateUser(this, 1);

        // Get updated User from database. Later this will be changed to be easier.
        Realm realm = Realm.getInstance(this);
        User user = realm.where(User.class).equalTo("id", 1).findFirst();
        // Showing that the User is usable
        Log.d("eeee", "Friend id: " + user.getFriends().get(0).getId());
        realm.close();
    }
}