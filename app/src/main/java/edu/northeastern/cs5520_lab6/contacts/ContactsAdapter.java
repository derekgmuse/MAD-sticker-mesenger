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
 * Adapter for displaying a list of contacts within a RecyclerView. Each contact is presented
 * with their name, a welcome message, and an image, which are populated from the provided
 * list of {@link Contact} objects. This adapter is responsible for creating and binding
 * view holders for each contact, enabling dynamic content display within a RecyclerView layout.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
    private List<Contact> contacts;
    private LayoutInflater inflater;
    private ContactClickListener contactClickListener;

    /**
     * Constructs a ContactsAdapter with the specified context and a list of contacts.
     * The context is used to inflate views from XML layout files.
     *
     * @param context  The current context, used to inflate layout files.
     * @param contacts The list of Contact objects to be displayed in the RecyclerView.
     */
    public ContactsAdapter(Context context, List<Contact> contacts, ContactClickListener contactClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.contacts = contacts;
        this.contactClickListener = contactClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.messageTextView.setText(contact.getWelcomeMessage());
        // Use Glide or Picasso to load the image
        // Glide.with(holder.itemView.getContext()).load(contact.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
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
                            contactClickListener.onContactClick(contacts.get(position).getId());
                        }
                    }
                }
            });
        }
    }

    public interface ContactClickListener {
        void onContactClick(String contactId);
    }
}