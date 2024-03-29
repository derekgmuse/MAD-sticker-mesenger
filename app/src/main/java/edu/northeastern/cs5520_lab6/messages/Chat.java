package edu.northeastern.cs5520_lab6.messages;

import java.util.ArrayList;
import java.util.List;

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
    private String chatId;
    private List<String> userIds;
    private String name;
    private String lastMessage;
    private String timestamp;
    private String avatarUrl; // Optional URL for the avatar image

    public Chat() { }

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
    public Chat(String id, List<String> userIds, String name, String lastMessage, String timestamp, String avatarUrl) {
        this.chatId = id;
        this.userIds = new ArrayList<>(userIds);
        this.name = name;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.avatarUrl = avatarUrl;
    }

    // Getters for the class properties

    /**
     * Returns the id of the chat conversation.
     * @return The id as an String.
     */
    public String getId() { return chatId; }

    /**
     * Returns the userIds of all users in the conversation.
     * @return The List of userids in the conversation as a List<String>
     */
    public List<String> getUserIds() { return userIds; }

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

    /**
     * Sets the id for the chat
     * @param key String id from the database
     */
    public void setId(String key) { this.chatId=key; }

    // Setters if needed
    // ...
}