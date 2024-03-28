package edu.northeastern.cs5520_lab6.messages;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String contactId = extras.getString("contactId", null);
            String groupId = extras.getString("groupId", null);
            // Load appropriate messages based on ID
            if (contactId != null) {
                // Load conversation with this contact
            } else if (groupId != null) {
                // Load group conversation
            }
        }

        initializeToolbar();
        setupDummyMessages();
        setupMessageRecyclerView();
        setupMessageInput();
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

    private void setupDummyMessages() {
        // Populate messages with dummy data for demonstration
        String currentUserId = "currentUserId";
        messages.add(new Message("Hello!", currentUserId, System.currentTimeMillis() - 5000));
        messages.add(new Message("Hi there! How are you?", "user2", System.currentTimeMillis() - 3000));
        messages.add(new Message("I'm doing great, thanks for asking!", currentUserId, System.currentTimeMillis() - 1000));
    }

    /**
     * Sets up the RecyclerView for displaying messages, including initializing the adapter
     * with dummy messages.
     */
    private void setupMessageRecyclerView() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate messages with dummy data for demonstration
        String currentUserId = "currentUserId";

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
        if (!messageText.isEmpty()) {
            Message newMessage = new Message(messageText, "user1", System.currentTimeMillis());
            messages.add(newMessage);

            // Update UI and clear input field...
            messageAdapter.notifyItemInserted(messages.size() - 1);
            messagesRecyclerView.scrollToPosition(messages.size() - 1);
            messageEditText.setText("");
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