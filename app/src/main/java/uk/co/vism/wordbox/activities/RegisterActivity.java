package uk.co.vism.wordbox.activities;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.managers.RestClientManager;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends ActionBarActivity {
    @ViewById
    EditText email;
    @ViewById
    EditText username;
    @ViewById
    EditText password;
    @ViewById(R.id.password_confirm)
    EditText passwordConfirm;

    @Click
    void register() {
        boolean canContinue = true;
        if(email.getText().length() == 0) {
            email.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            canContinue = false;
        }
        if(username.getText().length() == 0) {
            username.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            canContinue = false;
        }
        if(password.getText().length() == 0) {
            password.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            canContinue = false;
        }
        if(passwordConfirm.getText().length() == 0) {
            passwordConfirm.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            canContinue = false;
        } else if(!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            password.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            passwordConfirm.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
            canContinue = false;
        }

        if(canContinue) {
            attemptRegister();
        }
    }

    @Background
    void attemptRegister() {
        Realm realm = null;

        try {
            realm = Realm.getInstance(RegisterActivity.this);

            // update user on signing in
            String[] errors = RestClientManager.register(
                    RegisterActivity.this,
                    realm,
                    email.getText().toString(),
                    username.getText().toString(),
                    password.getText().toString()
            );

            // if unsuccessful, error the fuck out
            if(errors.length > 0) {
                error(errors);
            } else {        // otherwise we've got a successful register, redirect to home
                HomeActivity_.intent(RegisterActivity.this).start();
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @UiThread
    void error(String[] errors) {
        Toast.makeText(RegisterActivity.this, errors[0], Toast.LENGTH_SHORT).show();
    }
}