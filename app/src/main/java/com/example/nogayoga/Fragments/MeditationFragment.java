package com.example.nogayoga.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nogayoga.DB.DynamoDBController;
import com.example.nogayoga.Models.Meditation;
import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.MyAdapterMeditation;
import com.example.nogayoga.Utils.MyAdapterVideos;
import com.google.firebase.database.annotations.NotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MeditationFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Meditation> meditation_videos;
    private DynamoDBController dynamoDBController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meditation, container, false);

        init(view);
        setClickListeners();
        loadRecyclerViewData();

        return view;
    }

    public void init(View view){
        dynamoDBController = new DynamoDBController(getContext(),getActivity());
        meditation_videos = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_meditation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setClickListeners(){

    }

    private void loadRecyclerViewData() {
        //here we get the videos from the other DB
        getAllAssignmentFromDB();
    }

    private void getAllAssignmentFromDB() {
        okhttp3.OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(DynamoDBController.DYNAMO_SERVER_URL + "get-all")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Failed to get all assignments");
//                progressBarDialog.dismiss();
                Log.d("ERROR:", "Connection failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful() && response.body() != null) {
                    ResponseBody responseData = response.body();
                    try {
                        JSONArray array = new JSONArray(responseData.string());
                        System.out.println("Get all meditation cards :\n" + array);
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

                Meditation meditation = new Meditation(
                        object.getString("title"),
                        object.getString("duration"),
                        object.getString("photoUrl"),
                        object.getString("link")
                );
                meditation_videos.add(meditation);
            }
            Log.d("Meditations", meditation_videos.toString());
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(() -> {
                adapter = new MyAdapterMeditation(meditation_videos, getContext(), getActivity());
                recyclerView.setAdapter(adapter);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    recyclerView.setVerticalScrollbarPosition(adapter.getItemCount());
                }
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
               // progressBarDialog.dismiss();
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
