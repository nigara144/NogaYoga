package com.example.nogayoga.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.example.nogayoga.Utils.MyAdapterVideos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BeginnerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Video> videos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_beginner, container, false);

        init(view);
        setClickListeners();
        loadRecyclerViewData();

        return view;
    }

    public void init(View view){
        videos = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_beginner);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setClickListeners(){

    }

    private void loadRecyclerViewData() {
        getAllVideosByType("Beginner");
    }

    private void getAllVideosByType(String type) {
        CollectionReference collectionRef = FirebaseHelper.db.collection("Videos").document(type).collection("Clips");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        videos.add(document.toObject(Video.class));
                    }
                    setRecyclerView();
                    Log.d("TAG", videos.toString());
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void  setRecyclerView(){
        adapter = new MyAdapterVideos(videos, getContext(), getActivity());
        recyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(adapter.getItemCount());
        }
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }
}