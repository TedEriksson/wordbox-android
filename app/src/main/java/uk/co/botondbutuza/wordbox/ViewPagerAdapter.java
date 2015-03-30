package uk.co.botondbutuza.wordbox;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener
{
    private ArrayList<TabInfo> tabs;
    private Context context;

    final class TabInfo
    {
        private final Class<?> aClass;
        private final Bundle args;

        TabInfo(Class<?> aClass, Bundle args)
        {
            this.aClass = aClass;
            this.args = args;
        }
    }

    public ViewPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        this.context = context;

        tabs = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position)
    {
        TabInfo info = tabs.get(position);
        return Fragment.instantiate(context, info.aClass.getName(), info.args);
    }

    public ActionBar.Tab addTab(ActionBar.Tab tab, Class<?> clss, Bundle args)
    {
        TabInfo info = new TabInfo(clss, args);
        tab.setTag(info); tab.setTabListener(this);

        tabs.add(info);
        return tab;
    }

    @Override
    public int getCount()
    {
        return tabs.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position)
    {
        //getActionBar().setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {
//        Object tag = tab.getTag();
//        for(int i = 0; i < tabs.size(); i++)
//        {
//            if(tabs.get(i) == tag) viewPager.setCurrentItem(i);
//        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
}
