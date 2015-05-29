package uk.co.vism.wordbox.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.HomeActivity;
import uk.co.vism.wordbox.activities.LoginActivity;
import uk.co.vism.wordbox.activities.LoginActivity_;
import uk.co.vism.wordbox.models.FriendRequest;
import uk.co.vism.wordbox.models.TempSentence;
import uk.co.vism.wordbox.models.TempWord;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.network.RestClient;
import uk.co.vism.wordbox.network.RestClient_;

public class RestClientManager {
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final String BASE_URL = "https://wordbox.herokuapp.com";

    private Context context;
    private OkHttpClient client;
    private Gson gson;

    private static RestClientManager instance = null;

    protected RestClientManager(Context context) {
        this.context = context;
        client = new OkHttpClient();
        gson = new Gson();
    }

    public static RestClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new RestClientManager(context);
        }
        return instance;
    }

    public static boolean loginUser(Context context, Realm realm, String email, String password) {
        JSONObject json = new JSONObject();
        try {
            // create the post body
            json.put("email", email);
            json.put("password", password);

            // submit, get response
            String response = getInstance(context).post(json.toString(), "/auth/sign_in");
            json = new JSONObject(response);

            // check for errors
            if(json.has("data")) {
                json = json.getJSONObject("data");

                // save the user id in sharedprefs
                SharedPreferences.Editor editor = context.getSharedPreferences("wordbox", 0).edit();
                editor.putInt("userid", json.getInt("id"));
                editor.apply();

                UserManager.updateUserByJson(realm, json.toString());
                return true;
            }

            return false;
        } catch(Exception e) {
            Log.d("loginUser:" + e.getClass(), e.getMessage());
            return false;
        }
    }

    public static String[] register(Context context, Realm realm, String email, String username, String password) {
        JSONObject json = new JSONObject();
        try {
            // create the post body
            json.put("email", email);
            json.put("username", username);
            json.put("password", password);

            // submit, get response
            String response = getInstance(context).post(json.toString(), "/auth");
            Log.d("register", response);
            json = new JSONObject(response);

            // check for errors
            if(json.getString("status").equals("success")) {
                json = json.getJSONObject("data");

                // save the user id in sharedprefs
                SharedPreferences.Editor editor = context.getSharedPreferences("wordbox", 0).edit();
                editor.putInt("userid", json.getInt("id"));
                editor.apply();

                UserManager.updateUserByJson(realm, json.toString());
                return new String[0];
            }

            JSONArray jsonErrors = json.getJSONObject("errors").getJSONArray("full_messages");
            String[] errors = new String[jsonErrors.length()];
            for(int i = 0; i < jsonErrors.length(); i++) {
                errors[i] = jsonErrors.getString(i);
            }
            return errors;

        } catch(Exception e) {
            Log.d("loginUser:" + e.getClass(), e.getMessage());
            return new String[] { "Unable to register" };
        }
    }

    public static void friendRequestByUsername(Context context, String username) {
        int userid = context.getSharedPreferences("wordbox", 0).getInt("userid", 0);
        getInstance(context).post("", "/users/" + userid + "/add_friend/" + username);
    }

    /**
     * Queries the network for friend requests, and saves them in the realm,
     * returning the number of pending requests
     * @param context
     * @param realm
     * @return number of pending requests
     */
    public static int getFriendRequests(Context context, Realm realm) {
        int userID = context.getSharedPreferences("wordbox", 0).getInt("userid", 0);
        String json = getInstance(context).get("/users/" + userID + "/friend_requests");

        try {
            // create the request objects in the realm
            JSONArray array = new JSONArray(json);
            for(int i = 0; i < array.length(); i++) {
                realm.beginTransaction();
                realm.createOrUpdateObjectFromJson(FriendRequest.class, array.getJSONObject(i));
                realm.commitTransaction();
            }
            return array.length();
        } catch(Exception e) {
            Log.d("getFriendRequests:" + e.getClass(), e.getMessage());
            return 0;
        }
    }

    public static boolean updateFriendRequest(Context context, int requestID, boolean accepted) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pending", accepted ? false : null);

            String json = getInstance(context).put(jsonObject.toString(), "/friends/" + requestID);
            Log.d("requests", json);

            return json.length() == 0;
        } catch(Exception e) {
            Log.d("updateFriendRequest:" + e.getClass(), e.getMessage());
            return false;
        }
    }

    public static void updateUser(Context context, Realm realm, int id) {
        String json = getInstance(context).get("/users/" + id);
        User user = UserManager.updateUserByJson(realm, json);
    }

    public static void uploadSentence(Context context, Realm realm, TempSentence sentence) {
        try {
            JSONArray words = new JSONArray();
            for(TempWord word : sentence.getWords()) {
                words.put(word.getText());
            }

            JSONObject json = new JSONObject();
            json.put("user_id", sentence.getUser_id());
            json.put("words", words);

            String result = getInstance(context).post(json.toString(), "/sentences");
        } catch(JSONException e) {
            Log.d("uploadSentence", e.getMessage());
        }
    }

    protected String get(String url) {
        Request.Builder builder = new Request.Builder().url(BASE_URL + url);
        builder = addHeaders(builder);

        try {
            Response response = client.newCall(builder.build()).execute();
            saveHeaders(response);
            return response.body().string();
        } catch(Exception e) {
            Log.d("RestClient:get:" + e.getClass(), e.getMessage());
            return "";
        }
    }

    protected String post(String json, String url) {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(BASE_URL + url).post(body);
        builder = addHeaders(builder);

        try {
            Response response = client.newCall(builder.build()).execute();
            saveHeaders(response);
            return response.body().string();
        } catch(Exception e) {
            Log.d("RestClient:post:" + e.getClass(), e.getMessage());
            return "";
        }
    }

    protected String put(String json, String url) {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(BASE_URL + url).put(body);
        builder = addHeaders(builder);

        try {
            Response response = client.newCall(builder.build()).execute();
            saveHeaders(response);
            return response.body().string();
        } catch(Exception e) {
            Log.d("RestClient:put:" + e.getClass(), e.getMessage());
            return "";
        }
    }

    protected Request.Builder addHeaders(Request.Builder builder) {
        SharedPreferences prefs = context.getSharedPreferences("wordbox", 0);

        builder.addHeader("Access-Token", prefs.getString("access-token", ""));
        builder.addHeader("Uid", prefs.getString("uid", ""));
        builder.addHeader("token-type", prefs.getString("token-type", ""));
        builder.addHeader("client", prefs.getString("client", ""));
        builder.addHeader("expiry", prefs.getString("expiry", ""));

//        Log.d("header", "access " + prefs.getString("access-token", ""));
//        Log.d("header", "uid " + prefs.getString("uid", ""));
//        Log.d("header", "token " + prefs.getString("token-type", ""));
//        Log.d("header", "client " + prefs.getString("client", ""));
//        Log.d("header", "expiry " + prefs.getString("expiry", ""));

        return builder;
    }

    /**
     * Saves the required headers to make authenticated calls to the server
     * @param response
     */
    protected void saveHeaders(Response response) {
        // unauthorized?
        if(response.code() == 401) {
            HomeActivity activity = (HomeActivity)context;
            activity.logout();
        }

        // no headers? ignore save request
        if(response.header("Access-Token") == null)
            return;

        // finally, save headers
        SharedPreferences.Editor editor = context.getSharedPreferences("wordbox", 0).edit();

        editor.putString("access-token", response.header("Access-Token"));
        editor.putString("uid", response.header("Uid"));
        editor.putString("token-type", response.header("token-type"));
        editor.putString("client", response.header("client"));
        editor.putString("expiry", response.header("expiry"));

        editor.apply();
    }
}