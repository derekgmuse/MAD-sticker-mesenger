package edu.northeastern.cs5520_lab6.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_lab6.R;

/**
 * Adapter for the RecyclerView in the NewGroupActivity, displaying a list of all users from which
 * a new group chat can be created. It provides functionality to select users for inclusion in the
 * group chat. Each user item displays the user's name and a welcome message, optionally supplemented
 * by an image if provided.
 *
 * This adapter supports interaction through clicks, invoking a callback interface upon selection
 * of a user, enabling the caller to manage user selections for the group chat.
 *
 * @author Tony Wilson
 * @version 2.0
 */
public class NewGroupAdapter extends RecyclerView.Adapter<NewGroupAdapter.ContactViewHolder> implements GenericAdapterNotifier{
    private List<User> users;
    private LayoutInflater inflater;
    private ContactClickListener contactClickListener;

    /**
     * Constructs a NewGroupAdapter with the provided context, user list, and a callback listener
     * for handling user selection.
     *
     * @param context The current context, used for layout inflation.
     * @param users A list of User objects to be displayed.
     * @param contactClickListener A callback interface for handling clicks on user items.
     */
    public NewGroupAdapter(Context context, List<User> users, ContactClickListener contactClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.contactClickListener = contactClickListener;
    }

    /**
     * Creates a new ViewHolder for user items when required. This method is invoked by the
     * RecyclerView when it needs a new ViewHolder to display an item.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View. This adapter does not differentiate view types,
     *                 so this parameter is not used.
     * @return A new instance of ContactViewHolder that holds the View for the user item.
     */
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    /**
     * Binds data from the user object at a specified position in the list to a ViewHolder.
     * This method populates the views in the ViewHolder with the user's name and welcome message.
     * Optionally, it could also handle loading the user's image using an image loading library.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getName());
        holder.messageTextView.setText(user.getWelcomeMessage());
    }

    /**
     * Returns the total number of items in the list held by the adapter. This method is called by the
     * RecyclerView to determine how many items are in the list.
     *
     * @return The total number of users in the list.
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * ViewHolder class for contact items within the RecyclerView. Holds references to the
     * TextViews for the contact's name and welcome message, and an ImageView for the contact's
     * image. This class is static to prevent access to the enclosing adapter's members, promoting
     * better garbage collection and performance.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, messageTextView;
        ImageView imageView;

        /**
         * Initializes a new instance of the ViewHolder for the contact items. Binds the
         * TextViews and ImageView to their respective views in the layout.
         *
         * @param itemView The root view of the item layout.
         */
        ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contactNameTextView);
            messageTextView = itemView.findViewById(R.id.contactWelcomeMessageTextView);
            imageView = itemView.findViewById(R.id.contactImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contactClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            contactClickListener.onContactClick(position);
                        }
                    }
                }
            });
        }
    }

    /**
     * Interface definition for a callback to be invoked when a user is selected for group chat
     * inclusion. The method passes position of the selected user as a parameter.
     */
    public interface ContactClickListener {
        void onContactClick(int contactId);
    }

    /**
     * Notifies any registered observers that the data set has changed. This method should be called
     * by the RecyclerView to reflect any changes in the list of users being displayed.
     *
     * This implementation calls notifyDataSetChanged(), notifying observers that the entire data set
     * has changed. Use more specific notification methods to minimize unnecessary work by the RecyclerView.
     */
    @Override
    public void notifyAdapterDataSetChanged() {
        notifyDataSetChanged();
    }
}