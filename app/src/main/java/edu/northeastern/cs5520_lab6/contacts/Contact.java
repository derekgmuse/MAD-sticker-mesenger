package edu.northeastern.cs5520_lab6.contacts;

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
public class Contact {
    private String id; // Unique identifier for Firebase reference
    private String name;
    private String welcomeMessage;
    private String imageUrl; // URL of the contact's image

    /**
     * Constructs a new Contact instance with specified details.
     *
     * @param id The unique identifier for the contact, often used as a reference in databases.
     * @param name The name of the contact.
     * @param welcomeMessage A personalized welcome message associated with the contact.
     * @param imageUrl The URL pointing to the contact's image, used for display purposes.
     */
    public Contact(String id, String name, String welcomeMessage, String imageUrl) {
        this.id = id;
        this.name = name;
        this.welcomeMessage = welcomeMessage;
        this.imageUrl = imageUrl;
    }

    // Getter for the contact's unique identifier.
    public String getId() { return id; }

    // Getter for the contact's name.
    public String getName() { return name; }

    // Getter for the contact's welcome message.
    public String getWelcomeMessage() { return welcomeMessage; }

    // Getter for the URL of the contact's image.
    public String getImageUrl() { return imageUrl; }
}