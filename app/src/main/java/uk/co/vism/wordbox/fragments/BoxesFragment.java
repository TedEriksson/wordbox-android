package uk.co.vism.wordbox.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

import io.realm.RealmList;
import uk.co.vism.wordbox.adapters.BoxesAdapter;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.models.Sentence;
import uk.co.vism.wordbox.models.Word;

@EFragment(R.layout.fragment_boxes)
public class BoxesFragment extends Fragment
{
    public static final String NAME = "Boxes";

    @ViewById(R.id.boxesRecyclerView)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @AfterViews
    void init()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoxesAdapter(getSentences());
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Sentence> getSentences()
    {
        ArrayList<Sentence> sentences = new ArrayList<>();
        Random rand = new Random(42);

        for(int i = 0; i < 50; i ++)
        {
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

        return sentences;
    }
}