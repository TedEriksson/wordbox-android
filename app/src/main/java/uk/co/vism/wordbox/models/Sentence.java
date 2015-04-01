package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Sentence extends RealmObject
{
    private RealmList<Word> words;

    public RealmList<Word> getWords()
    {
        return words;
    }

    public void setWords(RealmList<Word> words)
    {
        this.words = words;
    }
}
