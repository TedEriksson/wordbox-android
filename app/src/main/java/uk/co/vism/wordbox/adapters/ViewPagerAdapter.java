package uk.co.vism.wordbox.adapters;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import uk.co.vism.wordbox.fragments.BoxesFragment;
import uk.co.vism.wordbox.fragments.BoxesFragment_;
import uk.co.vism.wordbox.fragments.FriendsFragment;
import uk.co.vism.wordbox.fragments.FriendsFragment_;
import uk.co.vism.wordbox.fragments.MineFragment;
import uk.co.vism.wordbox.fragments.MineFragment_;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private Context context;
    private ViewPager viewPager;

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return BoxesFragment.NAME;
            case 1:
                return FriendsFragment.NAME;
            case 2:
                return MineFragment.NAME;
        }

        return "";
    }

    public ViewPagerAdapter(ViewPager viewPager, Context context, FragmentManager fm)
    {
        super(fm);
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                return new BoxesFragment_();
            case 1:
                return new FriendsFragment_();
            case 2:
                return new MineFragment_();
        }

        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}