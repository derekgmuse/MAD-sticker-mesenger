package edu.northeastern.cs5520_lab6.main;

import android.util.Log;
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
import edu.northeastern.cs5520_lab6.stickers.StickerEnum;

/**
 * Adapter class for a RecyclerView that displays a list of chat sessions. Each chat session is
 * represented by a {@link Chat} object and displayed using a custom layout. This adapter is
 * responsible for managing chat data and binding these data to the view holders.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    /**
     * The list of chat messages that the adapter will use to bind to the ViewHolders.
     */
    private List<Chat> chatList;

    /**
     * The listen for clicking on a chat.
     */
    private  ChatItemClickListener chatItemClickListener;

    /**
     * Constructs a new ChatsAdapter instance.
     *
     * @param chatList A list of {@link Chat} objects to be displayed in the RecyclerView.
     * @param chatItemClickListener Listener for handling clicks on chat items.
     */
    public ChatsAdapter(List<Chat> chatList, ChatItemClickListener chatItemClickListener) {
        this.chatList = chatList;
        this.chatItemClickListener = chatItemClickListener;
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
        holder.binder(chat);
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
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView lastMessageTextView;
        ImageView stickerImageView; // ImageView for displaying stickers
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
            stickerImageView = itemView.findViewById(R.id.stickerImageView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chatItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            chatItemClickListener.onChatClick(chatList.get(position).getId());
                        }
                    }
                }
            });
        }

        /**
         * Binds chat data to the ViewHolder, setting the chat name, timestamp, and the last message
         * or sticker. This method differentiates between text messages and sticker messages by checking
         * the prefix of the last message. If the last message indicates a sticker, it extracts the
         * sticker ID and displays the corresponding sticker image; otherwise, it displays the text
         * message.
         *
         * @param chat The chat object containing details about the conversation, including the last
         *             message sent or received in this chat.
         */
        void binder(Chat chat) {
            nameTextView.setText(chat.getName());
            timestampTextView.setText(chat.getTimestamp());
            if (chat.getLastMessage().startsWith("%sticker%:")) {
                // This is a sticker message, extract the sticker ID
                String stickerId = chat.getLastMessage().substring(10); // Remove the "%sticker%:" part
                // Now, use stickerId to display the sticker image
                lastMessageTextView.setVisibility(View.GONE);
                stickerImageView.setVisibility(View.VISIBLE);
                int stickerResId = StickerEnum.getResourceIdById(stickerId);
                if (stickerResId != -1) {
                    stickerImageView.setImageResource(stickerResId);
                } else {
                    // Handle unknown sticker
                    stickerImageView.setImageResource(R.drawable.default_sticker);
                }
            } else {
                // This is a regular text message, display it as such
                lastMessageTextView.setVisibility(View.VISIBLE);
                stickerImageView.setVisibility(View.GONE);
                lastMessageTextView.setText(chat.getLastMessage());
            }
        }
    }

    /**
     * Interface for defining the click behavior on chat items.
     */
    public interface ChatItemClickListener {
        /**
         * Called when a chat item is clicked.
         *
         * @param chatId The ID of the clicked chat.
         */
        void onChatClick(String chatId);
    }
}