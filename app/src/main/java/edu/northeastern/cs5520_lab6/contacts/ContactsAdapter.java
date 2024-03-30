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
 * Adapter class for managing a list of contacts within a RecyclerView. Each contact item is
 * represented by a user's name, a welcome message, and an optional image. This class is
 * responsible for the creation of ViewHolder objects for each contact and binding those
 * ViewHolders with the data from the provided User objects.
 *
 * @version 2.0 Updated to include dynamic image loading and enhanced click handling.
 * @author Tony Wilson
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> implements GenericAdapterNotifier{
    private List<User> users;
    private LayoutInflater inflater;
    private ContactClickListener contactClickListener;

    /**
     * Constructs a ContactsAdapter with the specified context, a list of users, and a listener.
     * The context is used to inflate views from XML layout files.  The listener is used to provide
     * a way of interacting with the itemView object in the activity.
     *
     * @param context  The current context, used to inflate layout files.
     * @param users The list of User objects to be displayed in the RecyclerView.
     * @param contactClickListener A listener for click events on contact items.
     */
    public ContactsAdapter(Context context, List<User> users, ContactClickListener contactClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.contactClickListener = contactClickListener;
    }

    /**
     * Called when RecyclerView needs a new {@link ContactViewHolder} of the given type to represent
     * an item. This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ContactViewHolder, int)}.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View. This allows for creating different types of
     *                 ViewHolders based on the item view type.
     * @return A new ContactViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ContactViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder The ContactViewHolder which should be updated to represent the contents of the
     *               item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getName());
        holder.messageTextView.setText(user.getWelcomeMessage());
        // Placeholder for image loading code, e.g., using Glide or Picasso libraries
        // Glide.with(holder.itemView.getContext()).load(user.getImageUrl()).into(holder.imageView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the users list representing the total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * ViewHolder for individual contact items in the RecyclerView. Holds the text views
     * for displaying the contact's name and welcome message, and an image view for the contact's
     * image. Implements click handling to notify a click listener of selected contacts.
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
                            contactClickListener.onContactClick(users.get(position).getUserId());
                        }
                    }
                }
            });
        }
    }

    /**
     * Interface definition for a callback to be invoked when a contact is clicked.
     */
    public interface ContactClickListener {
        void onContactClick(String contactId);
    }

    /**
     * Implementing the GenericAdapterNotifier method allowing FirebaseApi to update the adapter.
     */
    @Override
    public void notifyAdapterDataSetChanged() {
        notifyDataSetChanged();
    }
}