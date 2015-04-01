package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Sentence extends RealmObject
{
    private RealmList<Word> words;
    private int hearts;

    public RealmList<Word> getWords()   { return words; }
    public int getHearts()              { return hearts; }

    public void setWords(RealmList<Word> words) { this.words = words; }
    public void setHearts(int hearts)           { this.hearts = hearts; }
}
