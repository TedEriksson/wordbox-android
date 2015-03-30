package uk.co.botondbutuza.wordbox;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.botondbutuza.wordbox.fragments.DiscoverFragment;
import uk.co.botondbutuza.wordbox.fragments.DiscoverFragment_;
import uk.co.botondbutuza.wordbox.fragments.EarnFragment;
import uk.co.botondbutuza.wordbox.fragments.EarnFragment_;
import uk.co.botondbutuza.wordbox.fragments.YoursFragment;
import uk.co.botondbutuza.wordbox.fragments.YoursFragment_;

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

    }
}