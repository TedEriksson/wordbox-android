package uk.co.vism.wordbox.fragments;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;

import uk.co.vism.wordbox.R;

@EFragment(R.layout.fragment_earn)
public class EarnFragment extends Fragment
{
    public static final String NAME = "Earn";

    @ViewById(R.id.earnListView)
    ListView list;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @AfterViews
    void init()
    {
        String[] values = new String[]{ "Hello", "Yes", "Balls", "Programming", "Phone", "Can", "Cannot", "No", "Fuck", "Off", "This", "Is", "Getting", "Boring", "And", "Tedious" };
        items = new ArrayList<>();
        items.addAll(Arrays.asList(values));

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}