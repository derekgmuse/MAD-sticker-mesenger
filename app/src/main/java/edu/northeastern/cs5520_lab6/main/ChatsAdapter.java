package edu.northeastern.cs5520_lab6.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.messages.Chat;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

/**
 * Adapter for managing a list of chat messages in a RecyclerView. This adapter binds Chat data
 * to views defined in a custom layout for each chat item. It is responsible for creating
 * ViewHolder instances and binding chat data to these holders as needed.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    /**
     * The list of chat messages that the adapter will use to bind to the ViewHolders.
     */
    private List<Chat> chatList;

    /**
     * Initializes the adapter with a list of chat messages.
     *
     * @param chatList A list of Chat objects to be displayed.
     */
    public ChatsAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    /**
     * Called when RecyclerView needs a new {@link ChatViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ChatViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * updates the contents of the {@link ChatViewHolder#itemView} to reflect the item at
     * the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of
     *                 the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.nameTextView.setText(chat.getName());
        holder.lastMessageTextView.setText(chat.getLastMessage());
        holder.timestampTextView.setText(chat.getTimestamp());
        // Load avatar image using stickerId
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * ViewHolder class for chat items. Holds references to the individual views within the
     * layout of a single chat item, including text views for the chat participant's name,
     * the last message, and the timestamp of the last message, as well as an image view for
     * the avatar.
     */
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView lastMessageTextView;
        public TextView timestampTextView;
        public ImageView avatarImageView;

        /**
         * Constructs a new instance of the ViewHolder for a chat item, initializing UI components
         * that will display chat details.
         *
         * @param itemView The root view of the chat item layout.
         */
        public ChatViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }
}