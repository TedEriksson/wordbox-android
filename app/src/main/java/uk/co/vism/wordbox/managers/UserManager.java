package uk.co.vism.wordbox.managers;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import uk.co.vism.wordbox.models.User;

public class UserManager {
    public static User getUserById(Realm realm, int id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    public static void updateUserByJson(Realm realm, String json) {
        realm.beginTransaction();
        Log.d("json", json);
        realm.createOrUpdateObjectFromJson(User.class, json);
        realm.commitTransaction();
    }
}