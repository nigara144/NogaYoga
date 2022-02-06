package com.example.nogayoga.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nogayoga.Interfaces.LogoutSuccessCallBack;
import com.example.nogayoga.Interfaces.UserReadyCallBack;
import com.example.nogayoga.Interfaces.VideoReadyCallBack;
import com.example.nogayoga.Models.Event;
import com.example.nogayoga.Models.User;
import com.example.nogayoga.Models.Video;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
     public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
     public static FirebaseFirestore db = FirebaseFirestore.getInstance();

//    public User getUser(){
//        ArrayList<User> users = new ArrayList<>();
//        DocumentReference documentReference = db.collection("Users").document(mAuth.getCurrentUser().getUid());
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
//                            if(documentSnapshot.exists()){
//                                List<Event> events;
//                                events = (ArrayList<Event>)task.getResult().get("events");
//                                users.add(new User(documentSnapshot.getString("fullName"),
//                                        documentSnapshot.getString("email"), events));
//                            }else{
//                                return;
//                            }
//                        }
//                    });
//                }else{
//                    task.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("TAG", "Failed login!");
//                        }
//                    });
//                }
//            }
//        });
//        return users.isEmpty() ? null : users.get(0);
//    }



    public static void getUserDetails(final UserReadyCallBack callBackUsersReady) {
        String uid = getUid();
        if (!uid.equals("")) {
            DocumentReference docRef = db.collection("Users").document(uid);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                Log.d("getUser", String.valueOf(documentSnapshot.getData()));
                Log.d("getUser", "User uid is" + uid);
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
