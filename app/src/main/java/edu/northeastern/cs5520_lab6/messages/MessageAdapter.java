package edu.northeastern.cs5520_lab6.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;

/**
 * Adapter responsible for managing and binding message data within a {@link RecyclerView} in
 * {@link MessageActivity}. This adapter supports differentiating messages by their sender,
 * offering unique layouts for messages sent by the current user compared to those received from contacts.
 * This differentiation is achieved through layout inflation based on the sender's identity,
 * enhancing the user experience with a visually distinct chat interface.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GenericAdapterNotifier {
    private List<Message> messages; // List of message objects to display
    private String currentUserId; // ID of the current user to differentiate messages

    /**
     * Constructs a new MessageAdapter instance.
     *
     * @param messages List of {@link Message} objects representing the chat history.
     * @param currentUserId The unique identifier of the current user.
     */
    public MessageAdapter(List<Message> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    /**
     * Determines the type of view to use for a message at a given position based on who sent the message.
     * This method enables the adapter to use different layouts for messages sent by the user and those received from others,
     * enhancing the chat interface with a distinct visual differentiation between the two.
     *
     * @param position The position of the message in the dataset.
     * @return An integer representing the layout resource ID corresponding to the sender of the message.
     *         Returns the layout for the user's messages if the sender is the current user, or the layout
     *         for contact's messages otherwise.
     */
    @Override
    public int getItemViewType(int position) {
        // Determine the view type based on the sender
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)) {
            return R.layout.item_message_user; // This layout is for the user's messages
        } else {
            return R.layout.item_message_contact; // This layout is for the contact's messages
        }
    }

    /**
     * Inflates the appropriate layout for a message based on its sender. Messages from the current
     * user are inflated with a layout that aligns to the right and has a specific background, while
     * messages from contacts are inflated with a different layout aligned to the left.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View, determined by the sender of the message.
     * @return A new instance of MessageViewHolder that holds the View for the message.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MessageViewHolder(view);
    }

    /**
     * Binds the message data to the corresponding ViewHolder, setting the message content.
     * This method is called by the RecyclerView to display the data at the specified position.
     * It updates the contents of the ViewHolder to reflect the message at the given position in the data set.
     *
     * @param holder   The ViewHolder which should be updated to represent the content of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        ((MessageViewHolder) holder).bind(message);
    }

    /**
     * Returns the total number of messages in the data set held by the adapter.
     * This method allows the RecyclerView to determine the number of items it should display.
     *
     * @return The total number of messages in the data set.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * ViewHolder class for displaying individual message items within the RecyclerView. This class
     * efficiently manages view references, reducing the overhead of frequent layout inflation
     * and enabling smoother scrolling performance.
     */
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }

        /**
         * Binds a {@link Message} object's text content to this ViewHolder's TextView.
         *
         * @param message The message object containing the text to be displayed.
         */
        void bind(Message message) {
            messageTextView.setText(message.getText());
        }
    }

    /**
     * Notifies the RecyclerView that the dataset has changed and any View reflecting the dataset
     * should refresh itself.
     */
    @Override
    public void notifyAdapterDataSetChanged() {
        notifyDataSetChanged();
        //scrollToPosition(messages.size() - 1); // Scroll to the last message
    }
}