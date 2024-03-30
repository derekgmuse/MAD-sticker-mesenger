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
 * An adapter for a RecyclerView that displays a list of selected contacts. This adapter is
 * particularly used in contexts where users can select multiple contacts, such as when forming
 * a new group chat. Each selected contact is represented by their name and an optional image,
 * typically their avatar, in a horizontal list.
 *
 * The adapter relies on inflating a custom layout (`item_selected_contact.xml`) for each item,
 * which should include at least an ImageView for the contact's avatar and a TextView for their name.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class SelectedContactsAdapter extends RecyclerView.Adapter<SelectedContactsAdapter.ViewHolder> {

    private List<User> selectedUsers;
    private LayoutInflater inflater;

    /**
     * Initializes a new SelectedContactsAdapter with the provided context and list of selected
     * contacts.
     *
     * @param context The current context, used for inflating layout files.
     * @param selectedUsers A list of User objects representing the selected contacts.
     */
    public SelectedContactsAdapter(Context context, List<User> selectedUsers) {
        this.inflater = LayoutInflater.from(context);
        this.selectedUsers = selectedUsers;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_selected_contact, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method updates the contents
     * of the ViewHolder to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = selectedUsers.get(position);
        holder.nameTextView.setText(user.getName());
        // Load the image into imageView. Example with Glide: Glide.with(holder.imageView.getContext()).load(user.getImageUrl()).into(holder.imageView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of selected contacts.
     */
    @Override
    public int getItemCount() {
        return selectedUsers.size();
    }

    /**
     * ViewHolder class for the selected contacts. It contains an ImageView for displaying the
     * contact's avatar and a TextView for the contact's name. This setup facilitates easy
     * visualization and identification of selected contacts in the RecyclerView.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;

        /**
         * Constructs a new ViewHolder instance, binding the ImageView and TextView from the
         * inflated layout to display the contact's information.
         *
         * @param itemView The root view of the item layout which contains the ImageView and TextView.
         */
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contactImageView);
            nameTextView = itemView.findViewById(R.id.contactNameTextView);
        }
    }
}