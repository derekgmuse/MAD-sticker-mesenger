package edu.northeastern.cs5520_lab6.contacts;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.stickers.Sticker;
/**
 * Represents a user or contact entity within the application. Contains detailed information
 * such as a unique identifier, name, username, email, welcome message, image URL, and
 * a list of contacts. This model is pivotal for managing user data, especially when interfacing
 * with external database services like Firebase.
 *
 * @author Tony Wilson
 * @version 1.1
 */
public class User {
    private String userId; // Unique identifier for Firebase reference
    private String name; // User's full name
    private String username; // User's username
    private String email; // User's email
    private String welcomeMessage; // User set welcome message
    private String imageUrl; // URL of the contact's image
    private Contacts contacts; // List of contacts associated with the user
    private List<Sticker> stickers;

    /**
     * Default constructor for creating a User instance without setting any initial values.
     */
    public User () { }

    /**
     * Constructor for creating a User instance with initial values for user details.
     *
     * @param id Unique identifier for the user, commonly used for database referencing.
     * @param name Full name of the user.
     * @param username Username of the user.
     * @param email Email address of the user.
     * @param welcomeMessage A personal welcome message set by the user.
     * @param imageUrl URL pointing to the user's profile image.
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

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() { return name; }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() { return username; }

    /**
     * Retrieves the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() { return email; }

    /**
     * Retrieves the welcome message set by the user.
     *
     * @return The welcome message of the user.
     */
    public String getWelcomeMessage() { return welcomeMessage; }

    /**
     * Retrieves the URL of the user's profile image.
     *
     * @return The URL of the user's image.
     */
    public String getImageUrl() { return imageUrl; }

    /**
     * Retrieves the list of contacts associated with the user.
     *
     * @return The user's contacts.
     */
    public Contacts getContacts() { return contacts; }

    /**
     * Retrieves the list of stickers the user has interacted with.
     *
     * @return A list of stickers.
     */
    public List<Sticker> getStickers() { return stickers; }


    // Setters

    /**
     * Sets the user's contacts.
     *
     * @param contacts The new contacts to be set.
     */
    public void setContacts(Contacts contacts) { this.contacts = contacts; }

    /**
     * Sets the list of stickers the user has interacted with.
     *
     * @param stickers The new list of stickers.
     */
    public void setStickers(List<Sticker> stickers) { this.stickers = new ArrayList<Sticker>(stickers); }

    /**
     * Adds a sticker to the user's list of interacted stickers. If the sticker already exists,
     * increments its count; otherwise, adds it to the list.
     *
     * @param newSticker The sticker to be added or incremented.
     */
    public void addSticker(Sticker newSticker) {
        if (this.stickers == null) {
            this.stickers = new ArrayList<Sticker>();
        }
        if(this.stickers.contains(newSticker)) {
            for (Sticker sticker : this.stickers) {
                if (sticker.equals(newSticker)) {
                    sticker.incrementCount();
                    return;
                }
            }
        } else {
            this.stickers.add(newSticker);
        }
    }
}