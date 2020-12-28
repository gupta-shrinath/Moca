package com.collabapps.moca;

import java.util.List;

public class User {

    private String id;
    private String fullname;
    private String email;
    private boolean isFirstTimeUser;
    private List<String> favTopics;

    public User(String id, String fullname, String email) {
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

    public boolean isFirstTimeUser() {
        return isFirstTimeUser;
    }

    public List<String> getFavTopics() {
        return favTopics;
    }

    public void setFavTopics(List<String> favTopics) {
        this.favTopics = favTopics;
    }
}
