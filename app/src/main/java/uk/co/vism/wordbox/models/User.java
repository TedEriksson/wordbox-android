package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private String email;
    private String uid;
    private String username;
    private String image;
    private String provider;
    private String created_at;
    private String updated_at;
    private RealmList<User> friends;
    private RealmList<Sentence> sentences;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RealmList<User> getFriends() {
        return friends;
    }

    public RealmList<Sentence> getSentences() {
        return sentences;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFriends(RealmList<User> friends) {
        this.friends = friends;
    }

    public void setSentences(RealmList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}