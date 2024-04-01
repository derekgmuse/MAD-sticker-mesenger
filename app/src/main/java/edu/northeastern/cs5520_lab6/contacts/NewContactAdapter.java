package edu.northeastern.cs5520_lab6.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_lab6.R;

/**
 * Adapter for displaying search results within a RecyclerView in the NewContactActivity.
 * This adapter handles the layout for each user found in search results, allowing the user
 * to view and add contacts directly from these results. It differentiates itself from other
 * contact adapters by focusing on user search functionality, particularly in the context of
 * adding new contacts.
 *
 * The adapter binds user data to the view, including the username and welcome message, and
 * sets up click listeners for each item, enabling users to add contacts directly from the
 * search results. This class plays a crucial role in enhancing the user experience by
 * streamlining the process of searching for and adding new contacts.
 *
 * Version updates include:
 * - Adaptation to display search results with usernames and welcome messages.
 * - Implementation of click listeners on search results for direct contact addition.
 *
 * @author Tony Wilson
 * @version 1.1
 */
public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.ViewHolder> implements GenericAdapterNotifier{

    private List<User> users;
    private LayoutInflater inflater;
    private Context context;
    private ContactClickListener contactClickListener;

    /**
     * Constructs a NewContactAdapter with the specified context, a list of users, and a listener.
     * The context is used to inflate views from XML layout files.  The listener is used to provide
     * a way of interacting with the itemView object in the activity.
     *
     * @param context  The current context, used to inflate layout files.
     * @param users The list of User objects to be displayed in the RecyclerView.
     * @param contactClickListener A listener for click events on contact items.
     */
    public NewContactAdapter(Context context, List<User> users, ContactClickListener contactClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.contactClickListener = contactClickListener;
    }

    /**
     * Creates a new ViewHolder for a contact item when needed. This method inflates the layout
     * representing a single contact item in the search results.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View. Unused in this adapter as all items are of the same type.
     * @return A new instance of ViewHolder that holds the View for the contact item.
     */
    @Override
    public NewContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data of a specific user from the search results to a ViewHolder. This method
     * sets the user's username and welcome message in the respective TextViews of the item layout.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *               item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NewContactAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getUsername());
        holder.messageTextView.setText(user.getWelcomeMessage());
        // Set other views as needed
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
     * ViewHolder class for contact items within the RecyclerView. Holds references to the
     * TextViews for the contact's username and welcome message, and potentially an ImageView for
     * the contact's image in future implementations. The ViewHolder pattern provides a convenient
     * way to minimize layout inflation calls and improve performance in scrolling the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, messageTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
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
     * Interface definition for a callback to be invoked when a contact from the search results
     * is selected. This allows for interaction with the contact, such as adding them as a new
     * contact or initiating a chat.
     */
    public interface ContactClickListener {
        void onContactClick(String contactId);
    }

    /**
     * Notifies the RecyclerView to rebind all item views with the current data. This is used
     * after the dataset has changed, such as after a search operation, to ensure the views
     * reflect the current state.
     */
    @Override
    public void notifyAdapterDataSetChanged() {
        notifyDataSetChanged();
    }
}