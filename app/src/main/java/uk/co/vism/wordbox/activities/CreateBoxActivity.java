package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.managers.RestClientManager;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.TempSentence;
import uk.co.vism.wordbox.models.TempWord;
import uk.co.vism.wordbox.models.User;

@EActivity(R.layout.activity_create_box)
public class CreateBoxActivity extends Activity {
    private static final int ID_PREFIX = 10000;

    @ViewById(R.id.wordList)
    LinearLayout wordList;

    @ViewById(R.id.wordNumber)
    TextView wordNumber;

    private Realm tempRealm;
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
    @Background
    void clickDone() {
        tempRealm = Realm.getInstance(CreateBoxActivity.this, "temp.realm");

        // create sentence
        tempRealm.beginTransaction();
        TempSentence sentence = tempRealm.createObject(TempSentence.class);
        sentence.setUser_id(getSharedPreferences("wordbox", 0).getInt("userid", 0));
        
        for (TempWord wordToCopy : words) {
            TempWord realmWord = tempRealm.copyToRealm(wordToCopy);
            sentence.getWords().add(realmWord);
        }
        tempRealm.commitTransaction();

        // upload sentence
        RestClientManager.uploadSentence(CreateBoxActivity.this, tempRealm, sentence);
        tempRealm.close();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}