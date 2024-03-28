package edu.northeastern.cs5520_lab6.contacts;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.northeastern.cs5520_lab6.R;

/**
 * An activity that presents a form for creating a new contact. Users can input the first name,
 * last name, username, and email of the new contact. The activity includes a toolbar with
 * "New Contact" as its title and an option to return to the previous screen. Upon filling the
 * form, users can save the new contact information by tapping the save button, which triggers
 * validation and persistence of the contact data.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class NewContactActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, usernameEditText, emailEditText;
    private Button saveContactButton;

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

        initializeToolbar();
        initializeFormFields();
        configureSaveButton();
    }

    /**
     * Initializes the toolbar, setting its title and enabling the Up button for navigation.
     */
    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Contact");
        getSupportActionBar().setSubtitle("Add contact");
    }

    /**
     * Initializes form fields for entering new contact information.
     */
    private void initializeFormFields() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
    }

    /**
     * Configures the save button with a click listener to trigger saving the new contact.
     */
    private void configureSaveButton() {
        saveContactButton = findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(view -> saveContact());
    }

    /**
     * Collects input from the form fields, validates them, and persists the new contact information.
     * After saving, it may navigate back to the ContactsActivity or display a success message.
     */
    private void saveContact() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        // Validate input...
        // Save the contact information to your database or server...
        // Intent to go back to the ContactsActivity or display success message
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