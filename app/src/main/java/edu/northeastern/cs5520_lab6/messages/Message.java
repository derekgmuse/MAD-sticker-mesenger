package edu.northeastern.cs5520_lab6.messages;

/**
 * Encapsulates the details of a message exchanged in a chat conversation. This class includes
 * information about the message's content, the sender's identifier, the chat it belongs to, and
 * the time at which it was sent. It serves as a fundamental component in the messaging feature,
 * facilitating the display, storage, and retrieval of messages within the app.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class Message {
    private String chatId;  // Identifier of the chat to which the message belongs
    private String id;      // Unique identifier of the message within its chat
    private String text;    // Text content of the message
    private String senderId; // Identifier of the user who sent the message
    private String timestamp;  // Epoch timestamp indicating when the message was sent

    /**
     * Default constructor for creating an instance of Message. This is particularly used
     * for data retrieval operations where an empty object is populated from database records.
     */
    public Message() { }

    /**
     * Constructs a Message instance with all necessary details provided. This constructor
     * initializes a message ready for sending, displaying, or storing.
     *
     * @param chatId    The identifier of the chat to which the message belongs.
     * @param id        Unique identifier for the message within its chat.
     * @param text      The text content of the message.
     * @param senderId  The identifier of the user who sent the message.
     * @param timestamp Epoch timestamp representing when the message was sent.
     */
    public Message(String chatId, String id, String text, String senderId, String timestamp) {
        this.chatId = chatId;
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    // Getters

    /**
     * Retrieves the chatId parent
     * @return The id of the parent chat as a String.
     */
    public String getChatId() { return chatId; }

    /**
     * Retrieves the id of the message inside this chat.
     * @return the id of the message unique to this chat as a String.
     */
    public String getId() { return id; }

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
    public String getTimestamp() { return timestamp; }

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
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}