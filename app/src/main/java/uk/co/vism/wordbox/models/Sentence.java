package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sentence extends RealmObject
{
    @PrimaryKey
    private int id;
    private int user_id;
    private RealmList<Word> words;
    private int hearts;

    public int getId()                  { return id; }
    public int getUser_id()             { return user_id; }
    public RealmList<Word> getWords()   { return words; }
    public int getHearts()              { return hearts; }

    public void setId(int id)                   { this.id = id; }
    public void setUser_id(int id)              { this.user_id = id; }
    public void setWords(RealmList<Word> words) { this.words = words; }
    public void setHearts(int hearts)           { this.hearts = hearts; }
}
