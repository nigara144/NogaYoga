package com.example.nogayoga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser  extends AppCompatActivity implements View.OnClickListener{

    private TextView logo, registerUser;
    private EditText editFullName, editEmail, editPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        logo = (TextView) findViewById(R.id.logo);
        logo.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.sign_up_btn);
        registerUser.setOnClickListener(this);

        editFullName = (EditText) findViewById(R.id.full_name);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logo:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.sign_up_btn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String fullName = editFullName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(fullName.isEmpty()){
            editFullName.setError("Full name is required");
            editFullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email");
            editPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editPassword.setError("Password length should be at least 6 characters");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(RegisterUser.this, "Failed to register.Try again", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register.Try again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
