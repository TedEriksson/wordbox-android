package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject
{
    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private RealmList<User> friends;
    private RealmList<Sentence> sentences;

    public int getId()                          { return id; }
    public String getFirstName()                { return firstName; }
    public String getLastName()                 { return lastName; }
    public RealmList<User> getFriends()         { return friends; }
    public RealmList<Sentence> getSentences()   { return sentences; }

    public void setId(int id)                               { this.id = id; }
    public void setFirstName(String firstName)              { this.firstName = firstName; }
    public void setLastName(String lastName)                { this.lastName = lastName; }
    public void setFriends(RealmList<User> friends)         { this.friends = friends; }
    public void setSentences(RealmList<Sentence> sentences) { this.sentences = sentences; }
}
