package uk.co.vism.wordbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>
{
    private ArrayList<Discover> discovers;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView desc;
        public TextView hearts;

        public ViewHolder(View v)
        {
            super(v);
            this.image  = (ImageView)v.findViewById(R.id.discoverCardImage);
            this.desc   = (TextView)v.findViewById(R.id.discoverCardDescription);
            this.hearts = (TextView)v.findViewById(R.id.discoverCardHearts);
        }
    }

    public DiscoverAdapter(ArrayList<Discover> discovers)
    {
        this.discovers = discovers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        Discover act = discovers.get(i);
        viewHolder.desc.setText(act.desc);
        viewHolder.hearts.setText(act.hearts + " hearts");

        Picasso.with(viewHolder.hearts.getContext())
                .load(R.mipmap.ic_launcher)
                .resize(300, 300).centerCrop()
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount()
    {
        return discovers.size();
    }
}