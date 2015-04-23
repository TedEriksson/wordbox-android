package uk.co.vism.wordbox.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.BoxesAdapter;
import uk.co.vism.wordbox.managers.RestClientManager;
import uk.co.vism.wordbox.models.Sentence;

@EFragment(R.layout.fragment_mine)
public class MineFragment extends Fragment {
    public static final String NAME = "Mine";

    @ViewById(R.id.friendImage)
    ImageView myImage;
    @ViewById(R.id.friendName)
    TextView myName;
    @ViewById(R.id.mySentencesList)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sentence> sentences;

    @AfterViews
    void init() {
        sentences = new ArrayList<>();
        myName.setText("Botond");

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoxesAdapter(sentences);
        recyclerView.setAdapter(adapter);

        getSentences();
    }

    @Background
    public void getSentences() {
        Realm realm = null;
        try {
            realm = Realm.getInstance(getActivity());
            //RestClientManager.updateUser(getActivity(), realm, 1);

            // this won't work because fuck you, that's why
            //User user = UserManager.getUserById(realm, 1);
            //for(int i = 0; i < user.getSentences().size(); i++)
            //sentences.add(user.getSentences().get(i));
        }
        finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @UiThread
    void finished() {
        adapter.notifyItemRangeInserted(0, sentences.size());
    }
}