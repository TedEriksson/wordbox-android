package uk.co.vism.wordbox.managers;

import io.realm.Realm;
import uk.co.vism.wordbox.models.User;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class UserManager {
    public static User getUserById(Realm realm, int id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }
}
