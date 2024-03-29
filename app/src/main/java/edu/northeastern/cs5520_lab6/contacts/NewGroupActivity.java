package edu.northeastern.cs5520_lab6.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

/**
 * An activity dedicated to creating a new group by selecting members from a list of users.
 * It features two sections: one for displaying selected users and another for selecting
 * from all available users. A floating action button confirms the selection, finalizing
 * the group creation process.
 *
 * The activity utilizes a {@link Toolbar} for navigation and contextual information,
 * including the ability to return to the previous screen. The RecyclerView for selected
 * users displays them horizontally, emphasizing the distinction between selections and
 * potential choices.
 *
 * Initial contact data is provided as dummy data for demonstration and should be replaced
 * with dynamic data retrieval from a persistent storage or remote database solution.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class NewGroupActivity extends AppCompatActivity {

    private List<User> contacts = new ArrayList<>();
    private List<User> selectedUsers = new ArrayList<>();
    private SelectedContactsAdapter selectedContactsAdapter;
    private NewGroupAdapter newGroupAdapter;

    /**
     * Initializes the activity, sets up the user interface, and prepares data and view
     * components for interaction.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Otherwise,
     *                           it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        initializeToolbar();
        setupRecyclerViews();
        FirebaseApi.loadContactData(contacts, newGroupAdapter);
        configureConfirmationButton();
    }

    /**
     * Sets up the toolbar with a back button and titles indicating the activity's purpose.
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Group");
        getSupportActionBar().setSubtitle("Add members");
    }

    /**
     * Configures RecyclerViews for displaying selected users and all users, allowing
     * users to manage group membership before finalizing.
     */
    private void setupRecyclerViews() {
        // RecyclerView setup for selected and all users
        // Setup RecyclerView for selected users
        RecyclerView selectedContactsRecyclerView = findViewById(R.id.selectedContactsRecyclerView);
        selectedContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selectedContactsAdapter = new SelectedContactsAdapter(this, selectedUsers);

        // Assume SelectedContactsAdapter is our adapter for selected users
        selectedContactsRecyclerView.setAdapter(selectedContactsAdapter);

        // Setup RecyclerView for all users
        RecyclerView contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Assume NewGroupAdapter is our adapter for all users
        newGroupAdapter = new NewGroupAdapter(this, contacts, new NewGroupAdapter.ContactClickListener() {
            @Override
            public void onContactClick(int contactId) {
                selectedUsers.add(contacts.get(contactId));
                selectedContactsAdapter.notifyDataSetChanged();
            }
        });

        contactsRecyclerView.setAdapter(newGroupAdapter);
    }

    /**
     * Configures the FloatingActionButton to finalize the selection of users for the
     * new group, typically involving saving the group to a database and navigating to the
     * group chat view.
     */
    private void configureConfirmationButton() {
        // FAB setup for confirming selections
        FloatingActionButton fab = findViewById(R.id.confirmSelectionFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseApi.navigateToMessageActivityWithChatId(NewGroupActivity.this, createGroupAndGetId(selectedUsers));
                /*
                // Assuming you have logic here to create the group and get its ID
                String groupId = createGroupAndGetId(selectedUsers);

                Intent intent = new Intent(NewGroupActivity.this, MessageActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);

                 */
            }
        });
    }

    /**
     * Returns the selectedUsers as a string.  May need to modify this to get the desired affect.
     *
     * @param selectedUsers
     * @return
     */
    private List<String> createGroupAndGetId(List<User> selectedUsers) {
        List<String> selectedUserIds = new ArrayList<String>();
        for (User user : selectedUsers) {
            selectedUserIds.add(user.getUserId());
        }
        return selectedUserIds;
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