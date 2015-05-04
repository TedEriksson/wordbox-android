package uk.co.vism.wordbox.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.HomeActivity_;
import uk.co.vism.wordbox.adapters.FriendsAdapter;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.Sentence;
import uk.co.vism.wordbox.models.User;

@EFragment(R.layout.fragment_friends)
public class FriendsFragment extends WordBoxFragment {
    public static final String NAME = "Friends";
    public static final int PICK_CONTACT = 108;

    @ViewById(R.id.friendsList)
    RecyclerView friendsList;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;

    private ArrayList<User> friends;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeActivity_ activity = (HomeActivity_) getActivity();
                activity.downloadUser();
            }
        });

        friends = new ArrayList<>();
        adapter = new FriendsAdapter(friends);

        layoutManager = new LinearLayoutManager(getActivity());
        friendsList.setLayoutManager(layoutManager);
        friendsList.setAdapter(adapter);
    }

    @Override
    @UiThread
    public void updateData() {
        refreshLayout.setRefreshing(false);
        friends.clear();

        // grab the user for this thread
        user = UserManager.getUserById(realm, 1);

        friends.addAll(user.getFriends());
        adapter.notifyItemRangeChanged(0, friends.size());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CONTACT:
                if (resultCode == Activity.RESULT_OK) {
                    // grab the contact selected
                    Uri contact = data.getData();

                    // grab details about the contact selected
                    Cursor cursor = getActivity().getContentResolver().query(contact, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
                break;
        }
    }

    /**
     * Click handler for the new friend row at the top of the friends list.
     * Creates an alert dialog with three choices of adding a new friend, with action despatched
     * to the below methods
     */
    @Click
    public void friendRow() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Add friend by...")
                .setItems(new CharSequence[]{"Username", "Email", "Contact"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
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

    /**
     * Attempts to find the user by username, and adds it as a friend
     * Needs to query server for user, handle not found, and if found, add it to the list
     */
    @Background
    void addByUsername() {
        final EditText username = new EditText(getActivity());
        username.setTextColor(getResources().getColor(android.R.color.black));

        new AlertDialog.Builder(getActivity())
                .setTitle("Username")
                .setView(username)
                .setPositiveButton("Send request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "" + username.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    /**
     * Prompts the user to enter an email address, and launched the default email client with to,
     * subject, and a default invitation message
     */
    @Background
    void addByEmail() {
        final EditText username = new EditText(getActivity());
        username.setTextColor(getResources().getColor(android.R.color.black));

        new AlertDialog.Builder(getActivity())
                .setTitle("Username")
                .setView(username)
                .setPositiveButton("Send request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{username.getText().toString()});
                        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Let's get Boxy. WordBoxy");
                        i.putExtra(android.content.Intent.EXTRA_TEXT, "Default invitation text");
                        startActivity(Intent.createChooser(i, "Send email"));
                    }
                })
                .show();
    }

    /**
     * Opens the contact picker and returns the selected contact, which is then picked up by onActivityResult
     */
    @Background
    void addByContact() {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT);
    }
}