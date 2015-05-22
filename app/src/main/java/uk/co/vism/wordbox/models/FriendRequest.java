package uk.co.vism.wordbox.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FriendRequest extends RealmObject {
    @PrimaryKey
    private int id;
    private String created_at;
    private String updated_at;
    private int user_one;
    private int user_two;
    private boolean pending;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUser_one() {
        return user_one;
    }

    public void setUser_one(int user_one) {
        this.user_one = user_one;
    }

    public int getUser_two() {
        return user_two;
    }

    public void setUser_two(int user_two) {
        this.user_two = user_two;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
