package uk.co.vism.wordbox.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.HomeActivity_;
import uk.co.vism.wordbox.adapters.BoxesAdapter;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.Sentence;

@EFragment(R.layout.fragment_boxes)
public class BoxesFragment extends WordBoxFragment {
    public static final String NAME = "Boxes";

    @ViewById(R.id.boxesRecyclerView)
    RecyclerView recyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sentence> sentences;

    @AfterViews
    void init() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeActivity_ activity = (HomeActivity_) getActivity();
                activity.downloadUser();
            }
        });

        sentences = new ArrayList<>();
        adapter = new BoxesAdapter(sentences);
        adapter.setHasStableIds(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    @UiThread
    public void updateData() {
        // called by update
        refreshLayout.setRefreshing(false);

        sentences.clear();
        sentences.addAll(user.getSentences());

        // notify that we've updated
        adapter.notifyItemRangeChanged(0, sentences.size());
    }
}