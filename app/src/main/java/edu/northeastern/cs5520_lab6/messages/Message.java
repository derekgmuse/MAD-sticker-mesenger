package edu.northeastern.cs5520_lab6.messages;

/**
 * Represents a single message in a conversation, encapsulating the message text, the sender's
 * identifier, and a timestamp indicating when the message was sent. This class forms the core
 * data structure for messaging functionality within the app, allowing for the representation
 * and management of messages exchanged between users.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class Message {
    private String text;  // The actual text content of the message
    private String senderId;  // The unique identifier of the message sender
    private long timestamp;  // The time at which the message was sent, represented as a long value

    /**
     * Constructs a new Message instance with specified text content, sender identifier, and timestamp.
     *
     * @param text The text content of the message.
     * @param senderId The unique identifier of the user who sent the message.
     * @param timestamp The timestamp when the message was sent.
     */
    public Message(String text, String senderId, long timestamp) {
        this.text = text;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    // Getters

    /**
     * Retrieves the text content of the message.
     *
     * @return The text content of the message.
     */
    public String getText() { return text; }

    /**
     * Retrieves the unique identifier of the sender of the message.
     *
     * @return The unique identifier of the sender.
     */
    public String getSenderId() { return senderId; }

    /**
     * Retrieves the timestamp when the message was sent.
     *
     * @return The timestamp when the message was sent.
     */
    public long getTimestamp() { return timestamp; }

    // Setters, if you need to modify the messages after creation

    /**
     * Sets the text content of the message. This method allows modifying the message's text
     * after it has been created.
     *
     * @param text The new text content for the message.
     */
    public void setText(String text) { this.text = text; }

    /**
     * Sets the sender's unique identifier for the message. This method allows changing the
     * sender's identifier after the message has been created.
     *
     * @param senderId The new sender's unique identifier.
     */
    public void setSenderId(String senderId) { this.senderId = senderId; }

    /**
     * Sets the timestamp for when the message was sent. This method allows adjusting the
     * timestamp after the message has been created.
     *
     * @param timestamp The new timestamp for the message.
     */
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}