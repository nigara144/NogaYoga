package com.example.nogayoga.Utils;

import android.util.Log;

import com.example.nogayoga.Interfaces.LogoutSuccessCallBack;
import com.example.nogayoga.Interfaces.UserReadyCallBack;
import com.example.nogayoga.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class FirebaseHelper {
     public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
     public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getUserDetails(final UserReadyCallBack callBackUsersReady) {
        String uid = getUid();
        if (!uid.equals("")) {
            DocumentReference docRef = db.collection("Users").document(uid);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                Log.d("getUser", String.valueOf(documentSnapshot.getData()));
                callBackUsersReady.userReady(documentSnapshot.toObject(User.class));
            });
        }
    }

    public static void logOut(final LogoutSuccessCallBack logoutSuccessCallback) {
        FirebaseAuth.getInstance().signOut();
        logoutSuccessCallback.didSuccess(!isUserLoggedIn());
    }

    public static String getUid() {
        if (isUserLoggedIn()) {
            Log.d("getUser", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return "";
    }

    public static boolean isUserLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
}
