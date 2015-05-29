package uk.co.vism.wordbox.managers;

import io.realm.Realm;
import uk.co.vism.wordbox.models.User;

public class UserManager {
    public static User getUserById(Realm realm, int id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    public static User updateUserByJson(Realm realm, String json) {
        realm.beginTransaction();
        User user = realm.createOrUpdateObjectFromJson(User.class, json);
        realm.commitTransaction();

        return user;
    }
}