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

public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.ViewHolder> implements GenericAdapterNotifier{

    private List<User> users;
    private LayoutInflater inflater;
    private Context context;
    private ContactClickListener contactClickListener;

    /**
     * Constructs a ContactsAdapter with the specified context and a list of users.
     * The context is used to inflate views from XML layout files.
     *
     * @param context  The current context, used to inflate layout files.
     * @param users The list of User objects to be displayed in the RecyclerView.
     */
    public NewContactAdapter(Context context, List<User> users, ContactClickListener contactClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.contactClickListener = contactClickListener;
    }

    @Override
    public NewContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewContactAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getUsername());
        holder.messageTextView.setText(user.getWelcomeMessage());
        // Set other views as needed
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

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

    public interface ContactClickListener {
        void onContactClick(String contactId);
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        notifyDataSetChanged();
    }
}

