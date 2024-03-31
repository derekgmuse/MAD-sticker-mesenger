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
    private String messageType; // "text" or "sticker"
    private String stickerId; // Used if messageType is "sticker"

    /**
     * Default constructor for creating an instance of Message. This is particularly used
     * for data retrieval operations where an empty object is populated from database records.
     */
    public Message() { }

    /**
     * Initializes a new Message object with specified parameters. Suitable for creating
     * a message ready to be sent or stored.
     *
     * @param chatId The chat's ID to which this message belongs.
     * @param id The unique message ID within the chat.
     * @param text The message's text content.
     * @param senderId The ID of the user sending this message.
     * @param timestamp The timestamp indicating when the message was sent.
     * @param messageType The type of the message, distinguishing between text and sticker messages.
     * @param stickerId The ID of the sticker, relevant for sticker messages.
     */
    public Message(String chatId, String id, String text, String senderId, String timestamp, String messageType, String stickerId) {
        this.chatId = chatId;
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.messageType = messageType;
        this.stickerId = stickerId;
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

    /**
     * Retrieves the type of the message, indicating whether it is a text message or a sticker.
     *
     * @return A string representing the message type ("text" or "sticker").
     */
    public String getMessageType() { return messageType; }

    /**
     * Retrieves the unique identifier of the sticker, applicable if the message type is "sticker".
     *
     * @return The sticker ID as a string, or null if the message type is not a sticker.
     */
    public String getStickerId() { return stickerId; }





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

    /**
     * Sets the type of the message. This method allows changing the message's type after it has
     * been created, either to "text" or "sticker".
     *
     * @param messageType The new message type to be set.
     */
    public void setMessageType(String messageType) { this.messageType = messageType; }

    /**
     * Sets the unique identifier for the sticker, applicable if the message type is "sticker".
     * This method allows associating a different sticker with the message after it has been created.
     *
     * @param stickerId The new sticker ID to be set.
     */
    public void setStickerId(String stickerId) { this.stickerId = stickerId; }
}