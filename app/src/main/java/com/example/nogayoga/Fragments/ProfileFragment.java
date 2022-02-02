package com.example.nogayoga.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nogayoga.Activities.GeneralActivity;
import com.example.nogayoga.Interfaces.LogoutSuccessCallBack;
import com.example.nogayoga.Activities.MainActivity;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private View view = null;
    private TextView fullName;
    private TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view =  inflater.inflate(R.layout.fragment_profile, container, false);

        fullName = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email_profile);
        logout = view.findViewById(R.id.logout_button);

        fullName.setText(GeneralActivity.user.getFullName());
        email.setText(GeneralActivity.user.getEmail());

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
        return view;
    }
}