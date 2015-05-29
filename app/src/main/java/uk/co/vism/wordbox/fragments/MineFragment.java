package uk.co.vism.wordbox.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.HomeActivity_;
import uk.co.vism.wordbox.adapters.BoxesAdapter;
import uk.co.vism.wordbox.models.Sentence;

@EFragment(R.layout.fragment_mine)
public class MineFragment extends WordBoxFragment {
    public static final String NAME = "Mine";

    @ViewById(R.id.friendImage)
    ImageView myImage;
    @ViewById(R.id.friendName)
    TextView myName;
    @ViewById(R.id.mySentencesList)
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
                activity.updateData();
            }
        });

        myName.setText(user.getUsername());

        sentences = new ArrayList<>();
        adapter = new BoxesAdapter(sentences);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    @UiThread
    public void updateData() {
        refreshLayout.setRefreshing(false);

        sentences.clear();
        sentences.addAll(user.getSentences());
        adapter.notifyItemRangeChanged(0, sentences.size());
    }
}