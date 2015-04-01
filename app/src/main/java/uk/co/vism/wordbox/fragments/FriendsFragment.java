package uk.co.vism.wordbox.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

import io.realm.RealmList;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.adapters.FriendsAdapter;
import uk.co.vism.wordbox.models.User;

@EFragment(R.layout.fragment_friends)
public class FriendsFragment extends Fragment
{
    public static final String NAME = "Friends";

    @ViewById(R.id.friendsList)
    public RecyclerView friendsList;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        friendsList.setLayoutManager(layoutManager);

        adapter = new FriendsAdapter(getFriends());
        friendsList.setAdapter(adapter);
    }

    private RealmList<User> getFriends()
    {
        RealmList<User> users = new RealmList<>();
        String[] names = new String[]{ "Ted", "Sophie", "Adam", "John", "Stefan" };

        User user;
        for(String name : names)
        {
            user = new User();
            user.setFirstName(name);
            users.add(user);
        }

        return users;
    }
}