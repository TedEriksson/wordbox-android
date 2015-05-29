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

    private EditText word;
    private ArrayList<EditText> editTexts;

    @AfterViews
    void init() {
        editTexts = new ArrayList<>();
        createEditWordRow();
    }

    private void createEditWordRow() {
        // create new textfield
        word = (EditText) LayoutInflater.from(CreateBoxActivity.this).inflate(R.layout.new_word, null);
        word.setId(ID_PREFIX + editTexts.size());
        word.requestFocus();
        word.requestFocusFromTouch();
        word.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    createEditWordRow();
                    return true;
                }

                return false;
            }
        });

        // add to layout
        wordList.addView(word);
        // update counter
        wordNumber.setText(editTexts.size() + " words");
        // add to list of words
        editTexts.add(word);
    }

    /**
     * This method is automatically attached to the done button
     * It puts the new TempSentence into the temporary realm to be uploaded
     */
    @Click(R.id.wordDone)
    @Background
    void clickDone() {
        Realm tempRealm = null;
        try {
            tempRealm = Realm.getInstance(CreateBoxActivity.this, "temp.realm");

            // create sentence
            tempRealm.beginTransaction();
            TempSentence sentence = tempRealm.createObject(TempSentence.class);
            sentence.setUser_id(getSharedPreferences("wordbox", 0).getInt("userid", 0));

            for (EditText text : editTexts) {
                TempWord word = new TempWord(text.getId() - ID_PREFIX, text.getText().toString().trim());
                word = tempRealm.copyToRealm(word);
                sentence.getWords().add(word);
            }
            tempRealm.commitTransaction();

            // upload sentence
            RestClientManager.uploadSentence(CreateBoxActivity.this, tempRealm, sentence);
        } finally {
            if(tempRealm != null) {
                tempRealm.close();
            }
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}