package uk.co.vism.wordbox.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.ViewPagerAdapter;
import uk.co.vism.wordbox.fragments.WordBoxFragment;
import uk.co.vism.wordbox.managers.RestClientManager;

@EActivity(R.layout.activity_home)
@OptionsMenu(R.menu.menu_home)
public class HomeActivity extends ActionBarActivity implements WordBoxFragment.OnUserLoaded {
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
        if (true)   // intellisense
            requestCount.setText("You have 2 requests pending");
        else
            requestRow.setVisibility(LinearLayout.GONE);

        setupViewPager();
    }

    @OptionsItem(R.id.action_logout)
    void logout() {
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

    @Override
    protected void onResume() {
        super.onResume();

        // Outputs all words from Temporary Realm
        // TODO: Remove example
//        Realm realm = null;
//        try {
//            realm = Realm.getInstance(this, "temp.realm");
//            for (TempSentence sentence : realm.where(TempSentence.class).findAll()) {
//                for (TempWord word : sentence.getWords()) {
//                    Log.d("TempWord", " word: " + word.getText());
//                }
//            }
//        }
//        finally {
//            if (realm != null)
//                realm.close();
//        }
    }

    /**
     * This method updates user 1, this user can be used throughout the app
     * TODO update proper user
     */
    @Background
    void downloadUser() {
        Realm realm = null;

        try {
            realm = Realm.getInstance(HomeActivity.this);
            int id = getSharedPreferences("wordbox", 0).getInt("userid", 0);

            // update user on app launch (this will get all info about them)
            RestClientManager.updateUser(HomeActivity.this, realm, id);

            onUserLoaded();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void onUserLoaded() {
        // grab all attached fragments
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        // update data for each
        for(int i = 0; i < fragments.size(); i++) {
            ((WordBoxFragment) fragments.get(i)).updateData();
        }
    }
}