package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.RealmObject;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.RequestsAdapter;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.models.Word;

@EActivity(R.layout.activity_requests)
public class RequestsActivity extends Activity
{
    @ViewById(R.id.requestsRecyclerView)
    RecyclerView requestsList;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init()
    {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        requestsList.setLayoutManager(layoutManager);

        adapter = new RequestsAdapter(getRequests());
        requestsList.setAdapter(adapter);
    }

    private ArrayList<? extends RealmObject> getRequests()
    {
        ArrayList<RealmObject> requests = new ArrayList<>();

        Word word = new Word(0, "Oh");
        requests.add(word);

        User user = new User();
        user.setUsername("Sophie");
        requests.add(user);

        return requests;
    }
}