package uk.co.vism.wordbox.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmList;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.RequestsActivity_;
import uk.co.vism.wordbox.adapters.FriendsAdapter;
import uk.co.vism.wordbox.models.User;

@EFragment(R.layout.fragment_friends)
public class FriendsFragment extends Fragment
{
    public static final String NAME = "Friends";

    @ViewById(R.id.friendsList)
    RecyclerView friendsList;
    @ViewById(R.id.requestRow)
    RelativeLayout requestRow;
    @ViewById(R.id.requestCount)
    TextView requestCount;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        friendsList.setLayoutManager(layoutManager);

        // if there are any pending requests
        if(true)
            requestCount.setText("You have 2 requests pending");
        else
            requestRow.setVisibility(LinearLayout.GONE);

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

    @Click
    public void viewRequests()
    {
        RequestsActivity_.intent(getActivity()).start();
    }

    @Click
    public void friendRow()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Add friend by...")
                .setItems(new CharSequence[]{ "Username", "Email", "Contact" }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch(which)
                        {
                            case 0:
                                addByUsername();
                                break;
                            case 1:
                                addByEmail();
                                break;
                            case 2:
                                addByContact();
                                break;
                        }
                    }
                })
                .show();
    }

    private void addByUsername()
    {
        final EditText username = new EditText(getActivity());

        new AlertDialog.Builder(getActivity())
                .setTitle("Username")
                .setView(username)
                .setPositiveButton("Send request", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getActivity(), "" + username.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void addByEmail()
    {

    }

    private void addByContact()
    {

    }
}