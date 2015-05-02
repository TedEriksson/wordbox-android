package uk.co.vism.wordbox.managers;

import android.content.Context;
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

import io.realm.Realm;
import uk.co.vism.wordbox.models.TempSentence;
import uk.co.vism.wordbox.models.TempWord;
import uk.co.vism.wordbox.network.RestClient;
import uk.co.vism.wordbox.network.RestClient_;

public class RestClientManager {
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final String BASE_URL = "https://wordbox.herokuapp.com";

    private OkHttpClient client;
    private Gson gson;

    private static RestClientManager instance = null;

    protected RestClientManager(Context context) {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public static RestClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new RestClientManager(context);
        }
        return instance;
    }

    public static void updateUser(Context context, Realm realm, int id) {
        String json = getInstance(context).get("/users/" + id);
        UserManager.updateUserByJson(context, realm, id, json);
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
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch(Exception e) {
            Log.d("RestClient:get:" + e.getClass(), e.getMessage());
            return "";
        }
    }

    protected String post(String json, String url) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch(Exception e) {
            Log.d("RestClient:post:" + e.getClass(), e.getMessage());
            return "";
        }
    }
}