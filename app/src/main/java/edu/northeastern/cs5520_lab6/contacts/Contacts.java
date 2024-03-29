package edu.northeastern.cs5520_lab6.contacts;

import java.util.ArrayList;
import java.util.List;

public class Contacts {
    private List<String> userIDs;

    public Contacts () { }

    public Contacts (String userId) {
        this.userIDs = new ArrayList<>();
        this.userIDs.add(userId);
    }
    public Contacts (List<String> userIDs) { this.userIDs = new ArrayList<String>(userIDs); }

    public List<String> getUserIDs() { return userIDs; }

    public void setUserIDs(List<String> userIDs) { this.userIDs = new ArrayList<String>(userIDs); }

    public void addUserID(String userID) {
        if (this.userIDs ==null) {
            this.userIDs = new ArrayList<>();
        }
        this.userIDs.add(userID);
    }
}