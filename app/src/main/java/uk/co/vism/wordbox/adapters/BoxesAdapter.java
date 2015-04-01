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
import uk.co.vism.wordbox.models.Sentence;
import uk.co.vism.wordbox.models.Word;

public class BoxesAdapter extends RecyclerView.Adapter<BoxesAdapter.ViewHolder>
{
    private RealmList<Sentence> sentences;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView title;
        public TextView hearts;

        public ViewHolder(View v)
        {
            super(v);
            this.image  = (ImageView)v.findViewById(R.id.boxesCardImage);
            this.title  = (TextView)v.findViewById(R.id.boxesCardTitle);
            this.hearts = (TextView)v.findViewById(R.id.boxesCardHearts);
        }
    }

    public BoxesAdapter(RealmList<Sentence> sentences)
    {
        this.sentences = sentences;
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
        Sentence sentence = sentences.get(i);
        RealmList<Word> words = sentence.getWords();

        StringBuilder title = new StringBuilder();
        for(Word word : words)
        {
            title.append(word.getText());
            title.append(" ");
        }

        viewHolder.title.setText(title.toString());
        viewHolder.hearts.setText(sentence.getHearts() + " hearts");

        Picasso.with(viewHolder.hearts.getContext())
                .load(R.mipmap.ic_launcher)
                .resize(500, 500).centerCrop()
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount()
    {
        return sentences.size();
    }
}