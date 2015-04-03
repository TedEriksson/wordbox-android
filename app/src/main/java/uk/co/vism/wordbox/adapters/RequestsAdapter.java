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
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.models.Word;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder>
{
    private ArrayList<? extends RealmObject> requests;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView description;
        public Button accept, reject;

        public ViewHolder(View v)
        {
            super(v);
            title = (TextView)v.findViewById(R.id.requestTitle);
            description = (TextView)v.findViewById(R.id.requestDescription);
            accept = (Button)v.findViewById(R.id.acceptRequest);
            reject = (Button)v.findViewById(R.id.rejectRequest);
        }
    }

    public RequestsAdapter(ArrayList<? extends RealmObject> requests)
    {
        this.requests = requests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.requests_card, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i)
    {
        RealmObject obj = requests.get(i);

        if(obj.getClass().equals(Word.class))
        {
            Word word = (Word)obj;
            viewHolder.title.setText(word.getText());
            viewHolder.title.setBackgroundColor(viewHolder.title.getResources().getColor(R.color.orange));
            viewHolder.description.setText("Word from Botond");
            viewHolder.accept.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    RecordWordActivity_.intent(viewHolder.title.getContext()).start();
                }
            });
        }
        else if(obj.getClass().equals(User.class))
        {
            User user = (User)obj;
            viewHolder.title.setText(user.getUsername());
            viewHolder.title.setBackgroundColor(viewHolder.title.getResources().getColor(R.color.green));
            viewHolder.description.setText("Add friend?");
        }
    }

    @Override
    public int getItemCount()
    {
        return requests.size();
    }
}