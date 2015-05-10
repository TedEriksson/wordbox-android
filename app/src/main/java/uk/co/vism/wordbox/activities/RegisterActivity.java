package uk.co.vism.wordbox.activities;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.vism.wordbox.R;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends ActionBarActivity {
    @ViewById
    EditText email;
    @ViewById
    EditText password;

    @Click
    void login() {
        if(email.getText().length() == 0)
            email.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        if(password.getText().length() == 0)
            password.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));

        if(email.getText().length() > 0 && password.getText().length() > 0) {
            attemptLogin();
        }
    }

    @Click
    void register() {

    }

    @Background
    void attemptLogin() {

    }
}