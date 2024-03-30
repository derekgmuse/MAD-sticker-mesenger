package edu.northeastern.cs5520_lab6.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
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

        // Initialize components of activity
        chatList = new ArrayList<>();
        initializeRecyclerView(view);
        FirebaseApi.loadChatData(chatList, adapter);

        return view;
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
            public void onChatClick(String chatId) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("chatId", chatId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}