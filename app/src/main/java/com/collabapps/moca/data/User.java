package com.collabapps.moca.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.List;

public class User {

    private String id;
    private String fullname;
    private String email;

    @PropertyName("fav_topics")
    private List<String> favTopics;

    public User() {

    }

    public User(String id, String fullname, String email) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public List<String> getFavTopics() {
        return favTopics;
    }

    @Exclude
    public void setFavTopics(List<String> favTopics) {
        this.favTopics = favTopics;
    }
}
