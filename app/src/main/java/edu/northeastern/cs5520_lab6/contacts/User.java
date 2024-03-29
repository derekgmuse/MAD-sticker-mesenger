package edu.northeastern.cs5520_lab6.contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a contact entity within the application. This class stores information about a
 * contact including their unique identifier, name, a welcome message, and an image URL.
 * The unique identifier is typically used for database references, especially when integrating
 * with Firebase or similar services.
 *
 * The contact's image URL points to an image resource that can be loaded to visually represent
 * the contact, often used in lists or profiles within the app.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class User {
    private String userId; // Unique identifier for Firebase reference
    private String name; // User's full name
    private String username; // User's username
    private String email; // User's email
    private String welcomeMessage; // User set welcome message
    private String imageUrl; // URL of the contact's image
    private Contacts contacts;

    /**
     * No-argument constructor
     */
    public User () { }

    /**
     * Constructs a new User instance with specified details.
     *
     * @param id The unique identifier for the contact, often used as a reference in databases.
     * @param name The name of the contact.
     * @param welcomeMessage A personalized welcome message associated with the contact.
     * @param imageUrl The URL pointing to the contact's image, used for display purposes.
     */
    public User(String id, String name, String username, String email, String welcomeMessage, String imageUrl) {
        this.userId = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.welcomeMessage = welcomeMessage;
        this.imageUrl = imageUrl;
    }

    // Getter for the contact's unique identifier.
    public String getUserId() { return userId; }

    // Getter for the contact's name.
    public String getName() { return name; }

    // Getter for the user's username
    public String getUsername() { return username; }

    // Getter for the user's email
    public String getEmail() { return email; }

    // Getter for the contact's welcome message.
    public String getWelcomeMessage() { return welcomeMessage; }

    // Getter for the URL of the contact's image.
    public String getImageUrl() { return imageUrl; }

    // Getter for the user's contacts
    public Contacts getContacts() { return contacts; }

    // Setters

    // For setting a user's contacts via a whole list
    public void setContacts(Contacts contacts) { this.contacts = contacts; }
}