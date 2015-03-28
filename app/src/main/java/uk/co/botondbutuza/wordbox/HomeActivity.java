package uk.co.botondbutuza.wordbox;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HomeActivity extends FragmentActivity
{
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        adapter.addTab(bar.newTab().setText(DiscoverFragment.NAME), DiscoverFragment.class, null);
        adapter.addTab(bar.newTab().setText(EarnFragment.NAME), EarnFragment.class, null);
        adapter.addTab(bar.newTab().setText(YoursFragment.NAME), YoursFragment.class, null);

        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener
    {
        private ArrayList<TabInfo> tabs;

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

        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);

            tabs = new ArrayList<>();
            viewPager.setOnPageChangeListener(this);
        }

        @Override
        public Fragment getItem(int position)
        {
            TabInfo info = tabs.get(position);
            return Fragment.instantiate(HomeActivity.this, info.aClass.getName(), info.args);
        }

        public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args)
        {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info); tab.setTabListener(this);

            tabs.add(info);

            getActionBar().addTab(tab);
            notifyDataSetChanged();
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
            getActionBar().setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
        {
            Object tag = tab.getTag();
            for(int i = 0; i < tabs.size(); i++)
            {
                if(tabs.get(i) == tag) viewPager.setCurrentItem(i);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
    }

    public static class EarnFragment extends Fragment
    {
        public static final String NAME = "Earn";

        private ListView list;
        private ArrayList<String> items;
        private ArrayAdapter<String> adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_earn, container, false);

            String[] values = new String[]{ "Hello", "Yes", "Balls", "Programming", "Phone", "Can", "Cannot", "No", "Fuck", "Off", "This", "Is", "Getting", "Boring", "And", "Tedious" };
            items = new ArrayList<>();
            items.addAll(Arrays.asList(values));

            adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, items);
            list = (ListView)rootView.findViewById(R.id.earnListView);
            list.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            return rootView;
        }
    }

    public static class DiscoverFragment extends Fragment
    {
        public static final String NAME = "Discover";

        private RecyclerView recyclerView;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager layoutManager;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_discover, container, false);

            super.onViewCreated(rootView, savedInstanceState);
            recyclerView = (RecyclerView)rootView.findViewById(R.id.discoverRecyclerView);

            layoutManager = new LinearLayoutManager(rootView.getContext());
            recyclerView.setLayoutManager(layoutManager);

            adapter = new DiscoverAdapter(getDiscovers());
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            return rootView;
        }

        private ArrayList<Discover> getDiscovers()
        {
            ArrayList<Discover> disc = new ArrayList<>();
            Random rand = new Random(42);

            for(int i = 0; i < 50; i ++)
            {
                disc.add(new Discover("aaaa", rand.nextInt(30)));
            }

            return disc;
        }
    }

    public static class YoursFragment extends Fragment
    {
        public static final String NAME = "Yours";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_yours, container, false);
            return rootView;
        }
    }
}

class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>
{
    private ArrayList<Discover> discovers;
    private Picasso picasso = null;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView desc;
        public TextView hearts;

        public ViewHolder(View v)
        {
            super(v);
            this.image  = (ImageView)v.findViewById(R.id.discoverCardImage);
            this.desc   = (TextView)v.findViewById(R.id.discoverCardDescription);
            this.hearts = (TextView)v.findViewById(R.id.discoverCardHearts);
        }
    }

    public DiscoverAdapter(ArrayList<Discover> discovers)
    {
        this.discovers = discovers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        if(picasso == null)
            picasso = Picasso.with(viewGroup.getContext());

        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        Discover act = discovers.get(i);
        viewHolder.desc.setText(act.desc);
        viewHolder.hearts.setText(act.hearts + " hearts");

        picasso
                .load(R.mipmap.ic_launcher)
                .resize(300, 300).centerCrop()
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount()
    {
        return discovers.size();
    }
}