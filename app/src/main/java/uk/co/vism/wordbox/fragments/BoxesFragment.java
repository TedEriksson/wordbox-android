package uk.co.vism.wordbox.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.BoxesAdapter;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.Sentence;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.models.Word;

@EFragment(R.layout.fragment_boxes)
public class BoxesFragment extends WordBoxFragment {
    public static final String NAME = "Boxes";

    @ViewById(R.id.boxesRecyclerView)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sentence> sentences;

    @AfterViews
    void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        sentences = new ArrayList<>();
        adapter = new BoxesAdapter(sentences);
        recyclerView.setAdapter(adapter);
    }

    @Override
    @UiThread
    public void updateData() {
        user = UserManager.getUserById(realm, 1);
        sentences.addAll(user.getSentences());
        adapter.notifyItemRangeInserted(0, sentences.size());
    }

    public ArrayList<Sentence> getSentences() {


        /*
        Random rand = new Random(42);
        for (int i = 0; i < 50; i++) {
            RealmList<Word> words = new RealmList<>();
            words.add(new Word(0, "hey"));
            words.add(new Word(1, "botond"));
            words.add(new Word(2, "you're"));
            words.add(new Word(3, "cool"));

            Sentence sentence = new Sentence();
            sentence.setWords(words);
            sentence.setHearts(rand.nextInt(50));

            sentences.add(sentence);
        }
        */

        return sentences;
    }
}