package com.example.nogayoga.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nogayoga.DB.MongoDBController;
import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.example.nogayoga.Utils.MyAdapterVideos;
import com.example.nogayoga.Utils.ProgressBarDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SkilledFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Video> videos;
    private ProgressBarDialog progressBarDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skilled, container, false);
        init(view);
        setClickListeners();
        loadRecyclerViewData();

        return view;
    }

    public void init(View view){
        videos = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_skilled);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBarDialog = ProgressBarDialog.newInstance("Loading");

    }

    public void setClickListeners(){

    }

    private void loadRecyclerViewData() {
        progressBarDialog.show(getActivity().getSupportFragmentManager(), "Loading");
        getAllVideosByType("high");
    }

    private void getAllVideosByType(String type) {
        okhttp3.OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\n  \"level\": \""+ type +"\"\n }", mediaType);
        Request request = new Request.Builder()
                .url(MongoDBController.MONGO_SERVER_URL + "get-all-videos-by-level")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Failed to get all videos");
                progressBarDialog.dismiss();
                Log.d("ERROR:", "Connection failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseBody responseData = response.body();
                    try {
                        JSONArray array = new JSONArray(responseData.string());
                        System.out.println("Get all skilled video cards :\n" + array);
                        setRecyclerView(array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void  setRecyclerView(JSONArray array){
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                System.out.println("as obj: " + object.toString());

                Video video = new Video(
                        object.getString("title"),
                        object.getString("duration"),
                        object.getString("photoUrl"),
                        object.getString("link")
                );
                videos.add(video);
            }
            Log.d("Meditations", videos.toString());
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(() -> {
                adapter = new MyAdapterVideos(videos, getContext(), getActivity());
                recyclerView.setAdapter(adapter);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    recyclerView.setVerticalScrollbarPosition(adapter.getItemCount());
                }
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
                progressBarDialog.dismiss();
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}