package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.models.TempSentence;
import uk.co.vism.wordbox.models.TempWord;

@EActivity(R.layout.activity_create_box)
public class CreateBoxActivity extends Activity {
    private static final int ID_PREFIX = 10000;

    @ViewById(R.id.wordList)
    LinearLayout wordList;

    @ViewById(R.id.wordNumber)
    TextView wordNumber;

    private EditText word;
    private ArrayList<TempWord> words;

    private TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                words.add(new TempWord(v.getId() - ID_PREFIX, v.getText().toString()));
                createEditWordRow();
                return true;
            }

            return false;
        }
    };

    @AfterViews
    void init() {
        words = new ArrayList<>();
        createEditWordRow();
    }

    private void createEditWordRow() {
        wordNumber.setText(words.size() + " words");

        word = (EditText) LayoutInflater.from(CreateBoxActivity.this).inflate(R.layout.new_word, null);

        word.setId(ID_PREFIX + words.size());
        word.requestFocus();
        word.requestFocusFromTouch();
        word.setOnEditorActionListener(actionListener);

        wordList.addView(word);
    }

    /**
     * This method is automatically attached to the done button
     * It puts the new TempSentence into the temporary realm to be uploaded
     */
    @Click(R.id.wordDone)
    void clickDone() {
        Realm realm = null;
        try {
            realm = Realm.getInstance(this, "temp.realm");

            realm.beginTransaction();
            //Create Sentence
            TempSentence sentence = realm.createObject(TempSentence.class);

            for (TempWord wordToCopy: words) {
                TempWord realmWord = realm.copyToRealm(wordToCopy);

                sentence.getWords().add(realmWord);
            }

            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
            finish();
        }
    }
}