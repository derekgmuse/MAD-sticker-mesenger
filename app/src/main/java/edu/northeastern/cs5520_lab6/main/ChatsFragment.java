package edu.northeastern.cs5520_lab6.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.messages.Chat;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

/**
 * Displays a list of chat conversations within a RecyclerView. This fragment sets up the
 * RecyclerView with a LinearLayoutManager and an instance of ChatsAdapter to manage and
 * display a list of Chat objects. It serves as the primary interface for users to view
 * their recent chat conversations.
 *
 * This fragment is part of the app's messaging feature, allowing users to see a summary
 * of their chats and select one to view or continue the conversation.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatsAdapter adapter;
    private List<Chat> chatList;

    /**
     * Required empty public constructor for fragment initialization.
     */
    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the fragment's layout and initializes the RecyclerView with its adapter
     * and layout manager. The method sets up the adapter with an initially empty list of chats,
     * which should be populated through a data source.
     *
     * @param inflater LayoutInflater object to inflate views in the fragment.
     * @param container If non-null, is the parent view that the fragment's UI will be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return Returns the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        // Placeholder for chat data loading logic
        chatList = new ArrayList<>();

        setupDummyChats();
        initializeRecyclerView(view);
        loadChatData();

        return view;
    }

    public void setupDummyChats() {
        chatList.add(new Chat("1", "Alice", "Hey, how are you?", "10:45 AM", "image_url_1"));
        chatList.add(new Chat("2", "Bob", "Let's meet tomorrow!", "Yesterday", "image_url_2"));
        chatList.add(new Chat("3", "Charlie", "Did you complete the assignment?", "Sunday", "image_url_3"));
        // Add more dummy chats as needed

    }

    /**
     * Initializes the RecyclerView with a LinearLayoutManager and sets the ChatsAdapter. Utilizes a
     * listener to call the intent when an item is called.
     *
     * @param view The inflated view of this fragment.
     */
    private void initializeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatsAdapter(chatList, new ChatsAdapter.ChatItemClickListener() {
            @Override
            public void onChatClick(String contactId) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("contactId", contactId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Loads chat data into the adapter. This method is a placeholder and should be implemented
     * to fetch real chat data from a database or other data source.
     */
    private void loadChatData() {

        // Example: chatList.add(new Chat("User Name", "Last Message", "Timestamp", "AvatarUrl"));
        adapter.notifyDataSetChanged();
    }

    // TODO: Add methods to update chatList and refresh the adapter as needed
}