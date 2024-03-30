package edu.northeastern.cs5520_lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.cs5520_lab6.contacts.User;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;

/**
 * The SignUpActivity class manages the user sign-up process, allowing new users to register in the application.
 * It handles user input for registration details such as email, password, full name, username, and a welcome message.
 * This activity uses Firebase Authentication to create new user accounts and stores additional user information in the
 * Firebase database.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * Firebase Authentication instance for handling sign-up operations.
     */
    private FirebaseAuth auth;

    /**
     * EditText fields for entering user email, password, full name, username, and welcome message.
     */
    private EditText email, password, fullNameEditText, usernameEditText, welcomeEditText;

    /**
     * Button for initiating the sign-up process.
     */
    private Button button_signup;

    /**
     * TextView that when clicked, navigates the user back to the LogInActivity.
     */
    private TextView onClick_login;

    /**
     * Called when the activity is starting. It initializes UI components, sets up Firebase Authentication,
     * and configures listeners for the sign-up process and navigating to the login screen.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Authentication instance and UI components.
        auth = FirebaseAuth.getInstance();
        initializeUI();

        // Setup the button click listener for signing up.
        button_signup.setOnClickListener(v -> {
            String user_email = email.getText().toString().trim();
            String user_password = password.getText().toString().trim();
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String welcome = welcomeEditText.getText().toString().trim();

            // Perform user registration with Firebase Authentication.
            performUserRegistration(user_email, user_password, fullName, username, welcome);
        });

        //setting onClick listener for users that already have an account and want to login
        //this file is set as the activity for when the application is launched
        //need a way to navigate to LogInActivity
        onClick_login.setOnClickListener(v->{
            Intent intent_login = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent_login);
        });
    }

    /**
     * Initializes UI components used for capturing user input during the sign-up process.
     */
    private void initializeUI() {
        email = findViewById(R.id.editText_signup_email);
        password = findViewById(R.id.editText_signup_password);
        fullNameEditText = findViewById(R.id.editText_fullName);
        usernameEditText = findViewById(R.id.editText_username);
        welcomeEditText = findViewById(R.id.editText_welcome_message);
        button_signup = findViewById(R.id.button_signup);
        onClick_login = findViewById(R.id.onClick_login);
    }

    /**
     * Performs user registration using Firebase Authentication and adds additional user information to the Firebase database.
     * It validates the input fields, creates a new user account with the provided email and password, and stores additional
     * details such as full name, username, and welcome message.
     *
     * @param email     User's email address.
     * @param password  User's chosen password.
     * @param fullName  User's full name.
     * @param username  User's chosen username.
     * @param welcome   User's welcome message.
     */
    private void performUserRegistration(String email, String password, String fullName, String username, String welcome) {
        // Validation and Firebase Authentication logic for creating a new user account.
        if (!email.isEmpty() && !password.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User newUser = new User(userId, fullName, username, email, welcome, "");
                    FirebaseApi.addUserToDatabase(this, newUser);
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign up unsuccessful." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (email.isEmpty()) {
            this.email.setError("Please enter an email here");
        } else {
            this.password.setError("Please enter a password here");
        }
    }
}