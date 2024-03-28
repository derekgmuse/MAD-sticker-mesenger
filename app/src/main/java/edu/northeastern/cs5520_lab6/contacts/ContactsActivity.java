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
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

/**
 * Displays a list of contacts and provides functionality to navigate to activities for creating a
 * new group or adding a new contact. The activity uses a {@link RecyclerView} to list contacts,
 * each represented by a {@link Contact} object. Options for "New Group" and "Add Contact" lead
 * to respective activities for those actions. This activity is an essential part of the app's
 * navigation and user interaction, allowing for the management and selection of contacts.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class ContactsActivity extends AppCompatActivity {
    private List<Contact> contacts = new ArrayList<>();
    private ContactsAdapter adapter;

    /**
     * Initializes the activity, sets up the toolbar, populates the contacts list, and configures
     * button listeners for creating new groups and adding new contacts.
     *
     * @param savedInstanceState Contains data supplied in onSaveInstanceState(Bundle) if the activity
     *                           is being re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setupToolbar();
        populateContactsList();
        setupRecyclerView();
        setupInteractionButtons();
    }

    /**
     * Sets up the toolbar with a back button and a title.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Contact");
    }

    /**
     * Populates the contacts list with dummy data. This method should be replaced with actual
     * data retrieval from a database or a network request.
     */
    private void populateContactsList() {
        contacts.add(new Contact("1", "John Doe", "Hello there!", "image_url"));
        contacts.add(new Contact("2", "Jane Smith", "Welcome!", "image_url"));
    }

    /**
     * Initializes the RecyclerView for displaying contacts.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contactsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactsAdapter(this, contacts, new ContactsAdapter.ContactClickListener() {
            @Override
            public void onContactClick(String contactId) {
                Intent intent = new Intent(ContactsActivity.this, MessageActivity.class);
                intent.putExtra("contactId", contactId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Configures listeners for the "New Group" and "Add Contact" buttons to navigate to the
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