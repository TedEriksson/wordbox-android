package uk.co.vism.wordbox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmObject;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.activities.RecordWordActivity_;
import uk.co.vism.wordbox.activities.RequestsActivity;
import uk.co.vism.wordbox.models.FriendRequest;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.models.Word;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    private ArrayList<? extends RealmObject> requests;
    private RequestsActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public Button accept, reject;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.requestTitle);
            description = (TextView) v.findViewById(R.id.requestDescription);
            accept = (Button) v.findViewById(R.id.acceptRequest);
            reject = (Button) v.findViewById(R.id.rejectRequest);
        }
    }

    public RequestsAdapter(ArrayList<? extends RealmObject> requests, RequestsActivity activity) {
        this.requests = requests;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.requests_card, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        RealmObject obj = requests.get(i);

        if (obj instanceof Word) {
            final Word word = (Word) obj;

            viewHolder.title.setText(word.getText());
            viewHolder.title.setBackgroundColor(viewHolder.title.getResources().getColor(R.color.orange));
            viewHolder.description.setText("Word from Botond");
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecordWordActivity_.intent(activity).extra("word_id", word.getId()).start();
                }
            });
        } else if (obj instanceof FriendRequest) {
            final FriendRequest request = (FriendRequest) obj;

            viewHolder.title.setText(request.getUser_two() + "");
            viewHolder.title.setBackgroundColor(viewHolder.title.getResources().getColor(R.color.green));
            viewHolder.description.setText("Add friend?");
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.acceptFriendRequest(request.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}