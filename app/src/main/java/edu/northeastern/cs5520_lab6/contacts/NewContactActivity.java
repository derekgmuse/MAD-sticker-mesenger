package edu.northeastern.cs5520_lab6.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
import edu.northeastern.cs5520_lab6.api.FirebaseApi;

/**
 * Facilitates the creation of a new contact by providing a user interface for inputting contact
 * details such as first name, last name, username, and email. The activity features a toolbar for
 * navigation and a form for contact information entry. It also supports searching for users by
 * username and displaying the results in a RecyclerView, allowing for easy addition of contacts.
 *
 * Upon entering search criteria and initiating a search, users are displayed in a list. Selecting
 * a user from this list will add them to the current user's contacts. This functionality enhances
 * user interaction by streamlining the process of finding and adding new contacts.
 *
 * Version updates include:
 * - Implementation of user search functionality based on username.
 * - Display of search results with an option to add users as contacts directly from the search
 *   results.
 * - Automated return to the ContactsActivity upon successful addition of a new contact.
 *
 * @author Tony Wilson
 * @version 1.1
 */
public class NewContactActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, usernameEditText, emailEditText;
    private Button saveContactButton;
    private RecyclerView searchResultsRecyclerView;
    private NewContactAdapter newContactAdapter;
    private List<User> searchResults = new ArrayList<>();
    private String currentUserId;

    /**
     * Sets up the activity's user interface on creation. Initializes form input fields and
     * configures the toolbar and save button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        // Initialize FirebaseAuth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Check if the user is signed in
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            // Handle the case where the user is not signed in
            Intent intent_login = new Intent(NewContactActivity.this, LogInActivity.class);
            startActivity(intent_login);
        }

        initializeToolbar();
        initializeFormFields();
        setupRecyclerView();
        configureSaveButton();
    }

    /**
     * Initializes the toolbar, setting its title and enabling the Up button for navigation.
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New User");
        getSupportActionBar().setSubtitle("Add contact");
    }

    /**
     * Initializes form fields for entering new contact information and configures the search
     * results RecyclerView. This method sets up the user interface components for data entry
     * and displays search results, facilitating user interaction.
     */
    private void initializeFormFields() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
    }

    /**
     * Initializes the RecyclerView for displaying users.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newContactAdapter = new NewContactAdapter(this, searchResults, new NewContactAdapter.ContactClickListener() {
            @Override
            public void onContactClick(String contactId) {
                FirebaseApi.addContactToUser(NewContactActivity.this, currentUserId, contactId, new FirebaseApi.ContactAddedCallback() {
                    @Override
                    public void onContactAdded() {
                        // Contact added successfully, navigate back to ContactsActivity
                        finish();
                    }
                });
            }
        });
        recyclerView.setAdapter(newContactAdapter);
    }

    /**
     * Configures the save button with a click listener to trigger saving the new contact.
     */
    private void configureSaveButton() {
        saveContactButton = findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(view -> saveContact());
    }

    /**
     * Searches for users by username and displays the results in a RecyclerView. Users can select
     * a contact from the search results to add them directly as a new contact. This method enhances
     * the user experience by providing an efficient way to find and add contacts.
     */
    private void saveContact() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        // Validate input...
        // Save the contact information to your database or server...
        // Intent to go back to the ContactsActivity or display success message
        FirebaseApi.searchUsersByUsername(username, new UserSearchCallback() {
            @Override
            public void onSearchResults(List<User> users) {
                searchResults.clear();
                if(users != null) {
                    searchResults.addAll(users);
                    newContactAdapter.notifyDataSetChanged(); // Refresh search results in UI
                }

            }
        });
    }

    /**
     * Interface for receiving search results from user queries. This callback interface is utilized
     * to update the user interface with search results, allowing the user to select from the
     * displayed users and add them as contacts.
     */
    public interface UserSearchCallback {
        void onSearchResults(List<User> users);
    }

    /**
     * This hook is called whenever an item in your options menu is selected. The default implementation
     * simply returns false to have the normal processing happen (calling the item's Runnable or
     * sending a message to its Handler as appropriate). You can use this method for any items
     * for which you would like to do processing without those other facilities.
     *
     * Derived classes should call through to the base class for it to perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the toolbar's up button click here
        if (item.getItemId() == android.R.id.home) {
            // Close this activity and return to the previous activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}