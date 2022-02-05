package com.example.nogayoga.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nogayoga.Activities.GeneralActivity;
import com.example.nogayoga.Interfaces.LogoutSuccessCallBack;
import com.example.nogayoga.Activities.MainActivity;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private Button logout;
    private View view = null;
    private EditText fullName;
    private TextView email;
    private TextView count;
    private ImageView edit, save;
    private String tempFullName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view =  inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        clickOnLogoutListener();
        clickListenerEditAndSave();

        return view;
    }

    private void clickListenerEditAndSave() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserName();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEditedUserName();
            }
        });
    }

    public void init(View view){
        fullName = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email_profile);
        logout = view.findViewById(R.id.logout_button);
        count = view.findViewById(R.id.joined_count);
        edit = view.findViewById(R.id.edit_icon);
        save = view.findViewById(R.id.save_icon);

        fullName.setText(GeneralActivity.user.getFullName());
        email.setText(GeneralActivity.user.getEmail());
        count.setText(String.valueOf(GeneralActivity.user.getCount()));
    }

    public void editUserName(){
        edit.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        tempFullName = fullName.getText().toString();
        fullName.setEnabled(true);
    }

    public void saveEditedUserName(){
        tempFullName = fullName.getText().toString();
        saveEditedDataToDB();
    }

    private void saveEditedDataToDB() {
        String userID = FirebaseHelper.mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseHelper.db.collection("Users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("fullName", tempFullName);
        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                GeneralActivity.user.setFullName(tempFullName);
                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                fullName.setEnabled(false);
                Log.d("UPDATE PROFILE", "User name edited successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "OnFailure: " + e.toString());
            }
        });
    }


    public void clickOnLogoutListener(){
        logout.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("We will miss you!")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> FirebaseHelper.logOut(new LogoutSuccessCallBack() {
                        @Override
                        public void didSuccess(Boolean didSuccess) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }))
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        });
    }
}