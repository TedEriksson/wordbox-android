package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.vism.wordbox.R;

@EActivity(R.layout.activity_create_box)
public class CreateBoxActivity extends Activity
{
    @ViewById(R.id.enterWord)
    EditText word;

    @AfterViews
    void init()
    {

    }
}
