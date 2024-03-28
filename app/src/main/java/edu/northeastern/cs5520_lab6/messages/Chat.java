package edu.northeastern.cs5520_lab6.messages;

/**
 * Represents a single chat conversation, including information about the other party's name,
 * the last message exchanged, the timestamp of the last message, and optionally, an avatar URL.
 * This class is used to encapsulate the data for a chat in a structured way, making it easier
 * to manage and display chat information within the application.
 *
 * The Chat class provides getters (and potentially setters) for accessing chat details. It's
 * intended for use in list structures where multiple chat conversations are managed and displayed.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class Chat {
    private String senderId;
    private String name;
    private String lastMessage;
    private String timestamp;
    private String avatarUrl; // Optional URL for the avatar image

    /**
     * Constructs a Chat instance with specified details.
     *
     * @param id The id of the sender
     * @param name The name of the other party in the chat conversation.
     * @param lastMessage The last message exchanged in this chat.
     * @param timestamp The timestamp of the last message, typically in a readable format.
     * @param avatarUrl The URL of the avatar image for the other party. This parameter is optional
     *                  and can be omitted if avatar images are not being used.
     */
    public Chat(String id, String name, String lastMessage, String timestamp, String avatarUrl) {
        this.senderId=id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.avatarUrl = avatarUrl;
    }

    // Getters for the class properties

    /**
     * Returns the id of the other party in the chat conversation.
     * @return The id as an int.
     */
    public String getSenderId() { return senderId; }

    /**
     * Returns the name of the other party in the chat conversation.
     * @return The name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the last message exchanged in the chat.
     * @return The last message as a String.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Returns the timestamp of the last message.
     * @return The timestamp as a String.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the URL for the avatar image of the other party, if available.
     * @return The avatar URL as a String, or null if not set.
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    // Setters if needed
    // ...
}