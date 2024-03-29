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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email;
    private EditText password;
    private Button button_signup;
    private TextView onClick_login;
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText welcomeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getting instance of Firebase Authentication object
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editText_signup_email);
        password = findViewById(R.id.editText_signup_password);
        fullNameEditText = findViewById(R.id.editText_fullName);
        usernameEditText = findViewById(R.id.editText_username);
        welcomeEditText = findViewById(R.id.editText_welcome_message);
        button_signup = findViewById(R.id.button_signup);
        onClick_login = findViewById(R.id.onClick_login);

        //gathering email and password and creating a user in Firebase onClick of signup button
        button_signup.setOnClickListener(v->{

            //email and password Strings trimmed to eliminate any whitespace at the end of user input
            String user_email = email.getText().toString().trim();
            String user_password = password.getText().toString().trim();
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String welcome = welcomeEditText.getText().toString().trim();

            //if user email or password are empty we will throw an error
            //if not then we create a new user using the auth object
            if(!(user_email.isEmpty()) && !(user_password.isEmpty())){
                //source: https://www.youtube.com/watch?v=TStttJRAPhE&ab_channel=AndroidKnowledge
                //creating a user on Firebase on success of the createUserWithEmailAndPassword function
                auth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(t->{
                    if(t.isSuccessful()){
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the Firebase User ID
                        User newUser = new User(userId, fullName, username, user_email, welcome, "");
                        FirebaseApi.addUserToDatabase(this, newUser);
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Sign up unsuccessful." + t.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(user_email.isEmpty()){
                email.setError("Please enter an email here");
            }
            //password is empty
            else {
                password.setError("Please enter a password here");
            }
        });

        //setting onClick listener for users that already have an account and want to login
        //this file is set as the activity for when the application is launched
        //need a way to navigate to LogInActivity
        onClick_login.setOnClickListener(v->{
            Intent intent_login = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent_login);
        });

    }
}