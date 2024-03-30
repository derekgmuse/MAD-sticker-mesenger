package edu.northeastern.cs5520_lab6.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;

/**
 * Manages the display of contacts in the application, offering navigation to activities for creating
 * new groups or adding new contacts. It utilizes a RecyclerView to list users, allowing for
 * interaction with individual contact items. The activity also provides options for initiating
 * group chats or adding a new contact, integrating closely with Firebase for data management.
 *
 * Version update: Enhanced data loading mechanisms and streamlined navigation for an improved
 * user experience.
 *
 * @author Tony Wilson
 * @version 1.1
 */
public class ContactsActivity extends AppCompatActivity {
    private List<User> contacts = new ArrayList<>();
    private ContactsAdapter adapter;

    /**
     * Initializes the activity, sets up the toolbar, populates the users list, and configures
     * button listeners for creating new groups and adding new users.
     *
     * @param savedInstanceState Contains data supplied in onSaveInstanceState(Bundle) if the activity
     *                           is being re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setupToolbar();
        setupRecyclerView();
        setupInteractionButtons();
    }

    /**
     * Ensures the contacts list is refreshed every time the activity resumes from a paused state.
     * This method clears any existing contacts data and fetches the latest list from Firebase,
     * reflecting any changes made while the activity was not in the foreground. It guarantees that
     * the displayed contacts are always up-to-date, enhancing the user experience by providing
     * current information.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Immediately refresh the contacts list when the activity resumes to reflect any changes.
        loadContactData();
        // Additionally, ensure the RecyclerView is updated to display the latest data.
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Fetches the updated list of contacts from Firebase and refreshes the RecyclerView
     * adapter to display the latest data. This method ensures the contacts list is always
     * current, reflecting any changes made to the user's contacts.
     */
    private void loadContactData() {
        // Clear the existing contacts list
        contacts.clear();

        // Fetch the updated contacts list from Firebase
        FirebaseApi.loadContactData(contacts, new GenericAdapterNotifier() {
            @Override
            public void notifyAdapterDataSetChanged() {
                // This callback should be triggered once the contacts list is updated
                // Now, notify the adapter to refresh the UI
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    /**
     * Sets up the toolbar with a back button and a title.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select User");
    }

    /**
     * Initializes the RecyclerView for displaying users.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contactsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactsAdapter(this, contacts, new ContactsAdapter.ContactClickListener() {
            @Override
            public void onContactClick(String contactId) {
                List<String> contacts = new ArrayList<String> ();
                contacts.add(contactId);
                FirebaseApi.findOrCreateChatWithUsers(ContactsActivity.this,contacts, "");
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Configures listeners for the "New Group" and "Add User" buttons to navigate to the
     * corresponding activities.
     */
    private void setupInteractionButtons() {
        findViewById(R.id.newGroupButton).setOnClickListener(v -> {
            Intent intent = new Intent(ContactsActivity.this, NewGroupActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.addContactButton).setOnClickListener(v -> {
            Intent intent = new Intent(ContactsActivity.this, NewContactActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Inflates the menu for the toolbar to add actions to the ActionBar.
     *
     * @param menu The options menu in which the items are placed.
     * @return true for the menu to be displayed; if false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}