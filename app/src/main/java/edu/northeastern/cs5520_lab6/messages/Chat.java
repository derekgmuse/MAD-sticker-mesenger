package edu.northeastern.cs5520_lab6.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the concept of a chat conversation within the application, encapsulating information
 * such as the participants' identifiers, the conversation's display name (often derived from
 * the participants' names), the last message exchanged within the chat, the timestamp for the
 * last message, and optionally, a URL to an avatar image representing the chat or the other party.
 *
 * This class is designed to facilitate easy management and display of chat conversations, allowing
 * for a structured approach to storing chat metadata.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class Chat {
    private String chatId; // Unique identifier for the chat
    private List<String> userIds; // Identifiers for all users participating in the chat
    private String name; // Display name for the chat, often based on participants' names
    private String lastMessage; // Content of the last message sent within the chat
    private String timestamp; // Timestamp for when the last message was sent
    private String avatarUrl; // URL to an avatar image associated with the chat

    /**
     * Default constructor for creating an empty Chat instance. This is used primarily
     * for data retrieval from databases that require a no-argument constructor.
     */
    public Chat() { }

    /**
     * Constructs a new Chat instance with specified details. This constructor initializes
     * the chat with all necessary information, making the object ready for use in displaying
     * chat data or storing it in a database.
     *
     * @param id Unique identifier for the chat.
     * @param userIds List of user identifiers who are participants of the chat.
     * @param name Display name for the chat.
     * @param lastMessage Content of the last message exchanged.
     * @param timestamp Timestamp for the last message exchange.
     * @param avatarUrl URL to an optional avatar image for the chat.
     */
    public Chat(String id, List<String> userIds, String name, String lastMessage, String timestamp, String avatarUrl) {
        this.chatId = id;
        this.userIds = new ArrayList<>(userIds);
        this.name = name;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.avatarUrl = avatarUrl;
    }

    // Accessor methods for class properties

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



    // Mutator methods if modification of chat details is required after initial construction

    /**
     * Sets the id for the chat
     * @param key String id from the database
     */
    public void setId(String key) { this.chatId=key; }

    /**
     * Sets the name associated with the chat or user. This method allows updating the name
     * attribute, which could represent the name of a chat group or the name of a user, depending
     * on the context in which it is used.
     *
     * @param name The new name to be set.
     */
    public void setName(String name) { this.name=name; }

    // Setters if needed
    // ...
}