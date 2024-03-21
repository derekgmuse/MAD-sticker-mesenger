package edu.northeastern.cs5520_lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email;
    private EditText password;
    private Button button_login;
    private TextView onClick_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //getting instance of Firebase Authentication object
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editText_login_email);
        password = findViewById(R.id.editText_login_password);
        button_login = findViewById(R.id.button_login);
        onClick_signup = findViewById(R.id.onClick_signup);

        //checking email and password (really just email for this project)
        //ensuring user has already signed up
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

        // adding onClick to this text view to allow user to navigate to sign up page
        onClick_signup.setOnClickListener(v->{
            Intent intent_signup = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent_signup);
        });
    }
}