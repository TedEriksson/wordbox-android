package uk.co.vism.wordbox.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
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

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {
    @ViewById
    EditText email;
    @ViewById
    EditText password;
    private ProgressDialog dialog;

    @AfterViews
    void init() {
        if(getSharedPreferences("wordbox", 0).contains("userid")) {
            HomeActivity_.intent(LoginActivity.this).start();
        }

        dialog = new ProgressDialog(LoginActivity.this);
    }

    @Click
    void register() {
        RegisterActivity_.intent(LoginActivity.this).start();
    }

    @Click
    void login() {
        if(email.getText().length() == 0) {
            email.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if(password.getText().length() == 0) {
            password.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }

        if(email.getText().length() > 0 && password.getText().length() > 0) {
            dialog.setTitle("Logging in");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(0);
            dialog.show();

            clear();
            attemptLogin();
        }
    }

    @Background
    void attemptLogin() {
        Realm realm = null;

        try {
            realm = Realm.getInstance(LoginActivity.this);

            // update user on signing in
            boolean loggedIn = RestClientManager.loginUser(LoginActivity.this, realm, email.getText().toString(), password.getText().toString());

            // if unsuccessful, error the fuck out
            if(!loggedIn) {
                error();
            } else {        // otherwise we've got a successful login, redirect to home
                HomeActivity_.intent(LoginActivity.this).start();
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @UiThread
    void error() {
        dialog.dismiss();
        Toast.makeText(LoginActivity.this, "Invalid login credentials. Please try again.", Toast.LENGTH_SHORT).show();
    }

    protected void clear() {
        Realm.deleteRealmFile(LoginActivity.this);
        Realm.deleteRealmFile(LoginActivity.this, "temp.realm");

        getSharedPreferences("wordbox", 0).edit().clear().apply();
    }
}