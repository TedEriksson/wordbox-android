package uk.co.vism.wordbox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.RealmList;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.models.User;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private RealmList<User> friends;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            this.image = (ImageView) v.findViewById(R.id.friendImage);
            this.name = (TextView) v.findViewById(R.id.friendName);
        }
    }

    public FriendsAdapter(RealmList<User> friends) {
        this.friends = friends;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_row, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        User friend = friends.get(i);
        viewHolder.name.setText(friend.getUsername());

        Picasso.with(viewHolder.name.getContext())
                .load(R.mipmap.ic_launcher)
                .resize(60, 60).centerCrop()
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}