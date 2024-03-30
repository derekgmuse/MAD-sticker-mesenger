package edu.northeastern.cs5520_lab6.contacts;

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