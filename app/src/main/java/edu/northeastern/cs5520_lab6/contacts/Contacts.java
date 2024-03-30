package edu.northeastern.cs5520_lab6.contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of contacts by storing their unique user IDs. This class provides
 * functionality to manage a list of user IDs, allowing for operations such as adding a new
 * user ID to the list or setting the list to a new collection of user IDs. It serves as a
 * foundational component in managing user contacts within the application.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class Contacts {
    // Stores the user IDs of contacts.
    private List<String> userIDs;

    /**
     * Default constructor initializes an empty list of user IDs.
     */
    public Contacts () { }

    /**
     * Constructs a Contacts object with a single user ID, initializing the list with this ID.
     *
     * @param userId The user ID to be added to the contacts list.
     */
    public Contacts (String userId) {
        this.userIDs = new ArrayList<>();
        this.userIDs.add(userId);
    }

    /**
     * Constructs a Contacts object with a provided list of user IDs.
     *
     * @param userIDs The list of user IDs to initialize the contacts list with.
     */
    public Contacts (List<String> userIDs) { this.userIDs = new ArrayList<String>(userIDs); }


    public List<String> getUserIDs() { return userIDs; }

    /**
     * Sets the contacts list to a new list of user IDs.
     *
     * @param userIDs The new list of user IDs to replace the current list.
     */
    public void setUserIDs(List<String> userIDs) { this.userIDs = new ArrayList<String>(userIDs); }

    /**
     * Adds a new user ID to the contacts list. If the list is null, initializes it before adding
     * the new user ID. This ensures the contacts list is always in a valid state for addition.
     *
     * @param userID The user ID to add to the contacts list.
     */
    public void addUserID(String userID) {
        if (this.userIDs ==null) {
            this.userIDs = new ArrayList<>();
        }
        this.userIDs.add(userID);
    }
}