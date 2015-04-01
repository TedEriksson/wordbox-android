package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.managers.SentenceManager;
import uk.co.vism.wordbox.models.Word;

@EActivity(R.layout.activity_create_box)
public class CreateBoxActivity extends Activity
{
    private static final int ID_PREFIX = 10000;

    @ViewById(R.id.wordList)
    LinearLayout wordList;

    @ViewById(R.id.wordNumber)
    TextView wordNumber;

    private EditText word;
    private SentenceManager box;

    private TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if(actionId == EditorInfo.IME_ACTION_NEXT)
            {
                box.addWord(new Word(v.getId() - ID_PREFIX, v.getText().toString()));
                createEditWordRow();
                return true;
            }

            return false;
        }
    };

    @AfterViews
    void init()
    {
        box = new SentenceManager();
        createEditWordRow();
    }

    private void createEditWordRow()
    {
        wordNumber.setText(box.count() + " words");

        word = (EditText)LayoutInflater.from(CreateBoxActivity.this).inflate(R.layout.new_word, null);

        word.setId(ID_PREFIX + box.count());
        word.requestFocus();
        word.requestFocusFromTouch();
        word.setOnEditorActionListener(actionListener);

        wordList.addView(word);
    }
}