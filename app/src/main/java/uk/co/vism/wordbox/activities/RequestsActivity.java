package uk.co.vism.wordbox.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.RequestsAdapter;
import uk.co.vism.wordbox.managers.RestClientManager;
import uk.co.vism.wordbox.models.FriendRequest;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.models.Word;

@EActivity(R.layout.activity_requests)
public class RequestsActivity extends ActionBarActivity {
    @ViewById(R.id.requestsRecyclerView)
    RecyclerView requestsList;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private Realm realm = null;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getInstance(RequestsActivity.this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        requestsList.setLayoutManager(layoutManager);

        adapter = new RequestsAdapter(getRequests(), RequestsActivity.this);
        requestsList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private ArrayList<? extends RealmObject> getRequests() {
        ArrayList<RealmObject> requests = new ArrayList<>();

        Word word = new Word(0, "Oh");
        requests.add(word);
        word = new Word(0, "Banana");
        requests.add(word);

        for(FriendRequest request : realm.allObjects(FriendRequest.class)) {
            requests.add(request);
        }

        return requests;
    }

    @Background
    public void acceptFriendRequest(int requestID) {
        Realm realm = null;
        try {
            realm = Realm.getInstance(RequestsActivity.this);
            FriendRequest request = realm.where(FriendRequest.class).equalTo("id", requestID).findFirst();

            if(RestClientManager.updateFriendRequest(RequestsActivity.this, request.getId(), true)) {
                realm.beginTransaction();
                request.removeFromRealm();
                realm.commitTransaction();
            }
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }
}