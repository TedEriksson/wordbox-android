package uk.co.vism.wordbox.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.ViewPagerAdapter;
import uk.co.vism.wordbox.fragments.WordBoxFragment;
import uk.co.vism.wordbox.managers.RestClientManager;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.User;

@EActivity(R.layout.activity_home)
@OptionsMenu(R.menu.menu_home)
public class HomeActivity extends ActionBarActivity implements WordBoxFragment.OnDataLoaded {
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
    void init() {
        adapter = new ViewPagerAdapter(viewPager, HomeActivity.this, getSupportFragmentManager());

        // if there are any pending requests
        //getRequestCount();
        setupViewPager();
    }

    @OptionsItem(R.id.action_logout)
    public void logout() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if(fragments != null) {
            // update data for each
            for (int i = 0; i < fragments.size(); i++) {
                ((WordBoxFragment) fragments.get(i)).closeRealm();
            }
        }

        getSharedPreferences("wordbox", 0).edit().clear().apply();
        LoginActivity_.intent(HomeActivity.this).start();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        ((WordBoxFragment) fragment).updateData();
    }

    private void setupViewPager() {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(1);

        tabs.setViewPager(viewPager);
    }

    @Click
    public void addNewBox() {
        CreateBoxActivity_.intent(HomeActivity.this).start();
    }

    @Click
    public void viewRequests() {
        RequestsActivity_.intent(HomeActivity.this).start();
    }

    /**
     * Updates the user and any other data, propagating the change to the fragments
     */
    @Background
    void updateData() {
        Realm realm = null;

        try {
            realm = Realm.getInstance(HomeActivity.this);
            int id = getSharedPreferences("wordbox", 0).getInt("userid", 0);

            RestClientManager.updateUser(HomeActivity.this, realm, id);
            int count = RestClientManager.getFriendRequests(HomeActivity.this, realm);
            updateRequestRow(count);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        onDataLoaded();
    }

    @Background
    void getRequestCount() {
        Realm realm = null;
        try {
            realm = Realm.getInstance(HomeActivity.this);
            int count = RestClientManager.getFriendRequests(HomeActivity.this, realm);
            updateRequestRow(count);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @UiThread
    void updateRequestRow(int count) {
        if (count > 0) {
            requestCount.setText("You have " + count + " requests pending");
            requestRow.setVisibility(View.VISIBLE);
        } else {
            requestRow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataLoaded() {
        // grab all attached fragments
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        // update data for each
        for(int i = 0; i < fragments.size(); i++) {
            ((WordBoxFragment) fragments.get(i)).updateData();
        }
    }
}