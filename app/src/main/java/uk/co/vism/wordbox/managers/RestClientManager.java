package uk.co.vism.wordbox.managers;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.network.RestClient;
import uk.co.vism.wordbox.network.RestClient_;

public class RestClientManager {
    RestClient restClient;

    private static RestClientManager instance = null;

    protected RestClientManager(Context context) {
        restClient = new RestClient_(context);
    }

    public static RestClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new RestClientManager(context);
        }
        return instance;
    }

    public static void updateUser(Context context, Realm realm, int id) {
        RestClientManager instance = getInstance(context);

        UserManager.updateUserByJson(context, realm, id, instance.restClient.getUser(id).toString());
    }
}