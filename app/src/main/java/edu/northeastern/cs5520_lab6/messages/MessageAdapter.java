package edu.northeastern.cs5520_lab6.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_lab6.R;

/**
 * An adapter for the RecyclerView in MessageActivity, responsible for binding message data
 * to views for display. This adapter supports different message layouts based on whether the
 * message was sent by the current user or a contact, enabling a visually distinct conversation
 * experience.
 *
 * Messages sent by the user are distinguished from those sent by contacts through layout inflation
 * logic, based on the sender's ID comparison with the current user's ID.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages;
    private String currentUserId;

    public MessageAdapter(List<Message> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        // Determine the view type based on the sender
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)) {
            return R.layout.item_message_user; // Assuming this layout is for the user's messages
        } else {
            return R.layout.item_message_contact; // Assuming this layout is for the contact's messages
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        ((MessageViewHolder) holder).bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * A ViewHolder for message items within the RecyclerView. It contains a TextView to display
     * the message text. The ViewHolder pattern provides a convenient way to minimize layout inflation
     * calls and improve performance in scrolling the RecyclerView.
     */
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }

        void bind(Message message) {
            messageTextView.setText(message.getText());
        }
    }
}