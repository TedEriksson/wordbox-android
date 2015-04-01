package uk.co.vism.wordbox.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

import uk.co.vism.wordbox.BoxesAdapter;
import uk.co.vism.wordbox.Discover;
import uk.co.vism.wordbox.R;

@EFragment(R.layout.fragment_boxes)
public class BoxesFragment extends Fragment
{
    public static final String NAME = "Boxes";

    @ViewById(R.id.boxesRecyclerView)
    public RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    public void init()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoxesAdapter(getDiscovers());
        recyclerView.setAdapter(adapter);
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