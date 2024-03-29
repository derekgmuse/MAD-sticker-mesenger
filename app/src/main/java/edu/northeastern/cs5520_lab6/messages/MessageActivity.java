package edu.northeastern.cs5520_lab6.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.LogInActivity;
import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.SignUpActivity;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;

/**
 * Manages the user interface for displaying and sending messages in a chat conversation. This activity
 * includes a {@link RecyclerView} for displaying messages, a {@link EditText} for composing new messages,
 * and a {@link Button} for sending them. Messages sent by the current user and their contact are
 * distinguished visually and arranged chronologically.
 *
 * The activity simulates message sending and displays dummy messages upon creation for demonstration
 * purposes. In a real-world scenario, messages would be retrieved from and sent to a backend server or
 * database.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class MessageActivity extends AppCompatActivity {
    private EditText messageEditText; // Input field for composing messages
    private Button sendMessageButton; // Button for sending messages
    private RecyclerView messagesRecyclerView; // Displays the message history
    private MessageAdapter messageAdapter; // Adapter for the RecyclerView
    private List<Message> messages = new ArrayList<>(); // Stores the current session's messages
    private String chatId;
    private String currentUserId;

    /**
     * Sets up the activity's layout, initializes UI components, and populates the message history
     * with dummy data on creation.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Initialize FirebaseAuth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Check if the user is signed in
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            // Handle the case where the user is not signed in
            Intent intent_login = new Intent(MessageActivity.this, LogInActivity.class);
            startActivity(intent_login);
        }

        initializeToolbar();
        setupMessageRecyclerView();
        setupMessageInput();

        // Load the data after the recyclerview and adapter is initialized
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.chatId = extras.getString("chatId", null);

            // Load appropriate messages based on chatId
            if (chatId != null) {
                FirebaseApi.loadMessagesForChat(chatId, messages, new GenericAdapterNotifier() {
                    @Override
                    public void notifyAdapterDataSetChanged() {
                        messageAdapter.notifyDataSetChanged();
                        if (!messages.isEmpty()) {
                            messagesRecyclerView.smoothScrollToPosition(messages.size() - 1); // Scroll to the last message
                        }
                    }
                });
            }
        }
    }

    /**
     * Initializes the toolbar with navigation and title settings.
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Messages");
    }

    /**
     * Sets up the RecyclerView for displaying messages, including initializing the adapter
     * with dummy messages.
     */
    private void setupMessageRecyclerView() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // This will make the list start at the bottom
        layoutManager.setReverseLayout(false); // This keeps the order of items from top to bottom
        messagesRecyclerView.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(messages, currentUserId);
        messagesRecyclerView.setAdapter(messageAdapter);
    }

    /**
     * Configures the message input field and the send message button, including setting the click
     * listener for sending messages.
     */
    private void setupMessageInput() {
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(v -> sendMessage());
    }

    /**
     * Handles sending a new message, including updating the UI and the messages list.
     */
    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        long timestamp = System.currentTimeMillis();
        if (!messageText.isEmpty()) {
            FirebaseApi.sendMessage(chatId, messageText, timestamp, new FirebaseApi.MessageSendCallback() {
                @Override
                public void onSuccess(Message message) {
                    // Update UI and clear input field...
                    runOnUiThread(() -> {
                        messages.add(message);
                        messageAdapter.notifyItemInserted(messages.size() - 1);
                        messagesRecyclerView.smoothScrollToPosition(messages.size() - 1);
                        messageEditText.setText("");
                    });
                }

                @Override
                public void onFailure() {
                    // Handle sending failure, e.g., show a toast message
                    runOnUiThread(() -> {
                        Toast.makeText(MessageActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }

    /**
     * Handles navigation item selections in the toolbar, specifically the home/up button.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}