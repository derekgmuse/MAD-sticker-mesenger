package edu.northeastern.cs5520_lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.cs5520_lab6.main.MainActivity;

/**
 * The LogInActivity class is responsible for managing the user login process in the application.
 * It interacts with Firebase Authentication to verify user credentials. This activity provides a user interface
 * where users can enter their email and password to log in. Additionally, it offers a way to navigate
 * to the SignUpActivity for users who need to create a new account.
 */
public class LogInActivity extends AppCompatActivity {

    /**
     * FirebaseAuth instance for handling login operations.
     */
    private FirebaseAuth auth;

    /**
     * EditText field for user to enter their email.
     */
    private EditText email;

    /**
     * EditText field for user to enter their password.
     */
    private EditText password;

    /**
     * Button for initiating the login process.
     */
    private Button button_login;

    /**
     * TextView that when clicked, navigates the user to the SignUpActivity for account creation.
     */
    private TextView onClick_signup;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically interact
     * with widgets in the UI, setting up listeners, and initializing class fields.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize FirebaseAuth instance and UI components.
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText_login_email);
        password = findViewById(R.id.editText_login_password);
        button_login = findViewById(R.id.button_login);
        onClick_signup = findViewById(R.id.onClick_signup);

        // Setup the button click listener to handle the login process.
        button_login.setOnClickListener(v->{
            //email and password Strings trimmed to eliminate any whitespace at the end of user input
            String user_email = email.getText().toString().trim();
            String user_password = password.getText().toString().trim();

            /*
            if the email is not empty and it matches a users email,
            the user will be logged in sending them to the main activity,
            and this onclick method will finish

            otherwise the login was not successful and an error message is presented

            Source: https://www.youtube.com/watch?v=TStttJRAPhE&ab_channel=AndroidKnowledge
                https://www.geeksforgeeks.org/user-authentication-using-firebase-in-android/

             */
            // Attempt to log in with the provided credentials, showing appropriate messages on success or failure.
            if(!user_email.isEmpty()){
                auth.signInWithEmailAndPassword(user_email, user_password).addOnSuccessListener(t->{
                    Toast.makeText(LogInActivity.this, "User Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(t->{
                    Toast.makeText(LogInActivity.this, "Log in Failed.  Try again", Toast.LENGTH_SHORT).show();
                });
            }
            else {
                email.setText("Email cannot be empty");
            }
        });

        // Setup the click listener for the signup text view to navigate to the SignUpActivity.
        onClick_signup.setOnClickListener(v->{
            Intent intent_signup = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent_signup);
        });
    }
}