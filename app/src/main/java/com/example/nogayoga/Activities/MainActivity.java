package com.example.nogayoga.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nogayoga.Interfaces.LogoutSuccessCallBack;
import com.example.nogayoga.Interfaces.UserReadyCallBack;
import com.example.nogayoga.Models.User;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnFailureListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String EMAIL = "editEmail";
    private TextView register;
    private EditText editPassword, editEmail;
    private ProgressBar progressBar;
    private Button loginUser;
    private String userID;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        loginUser = (Button) findViewById(R.id.sign_in_btn);
        loginUser.setOnClickListener(this);

        editPassword = (EditText) findViewById(R.id.password);
        editEmail = (EditText) findViewById(R.id.email);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.sign_in_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editPassword.setError("Password length should be at least 6 characters");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        signIn(email, password);
    }

    private void signIn(String emailTxt , String passwordTxt)
    {
        FirebaseHelper.mAuth.signInWithEmailAndPassword(emailTxt , passwordTxt)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("login", "signInWithEmail:success");
                        String userUId = FirebaseHelper.getUid();
                        FirebaseHelper.getUserDetails(new UserReadyCallBack() {
                            @Override
                            public void userReady(User user1) {
                                user = user1;
                                Log.d("TAG", "OnSuccess: login" + user.toString());
                                progressBar.setVisibility(View.GONE);
                                sendIntent(user);
                            }
                            @Override
                            public void error() {
                                FirebaseHelper.logOut(new LogoutSuccessCallBack() {
                                    @Override
                                    public void didSuccess(Boolean didSuccess) {
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(FirebaseHelper.isUserLoggedIn()){
            FirebaseHelper.getUserDetails(new UserReadyCallBack() {
                @Override
                public void userReady(User user1) {
                    user = user1;
                    Log.d("TAG", "OnSuccess: login" + user.toString());
                    sendIntent(user);
                }
                @Override
                public void error() {
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sendIntent(User user){
        Intent mainActivityIntent = new Intent(MainActivity.this, GeneralActivity.class);
        mainActivityIntent.putExtra("USER", user.toJson());
        Log.d("TAG", "OnSuccess: sendIntent before start activity" + user.toJson());
        startActivity(mainActivityIntent);
        finish();
    }
}