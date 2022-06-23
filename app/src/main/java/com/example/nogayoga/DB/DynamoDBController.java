package com.example.nogayoga.DB;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.nogayoga.Fragments.MeditationFragment;
import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DynamoDBController {

    public static String DYNAMO_SERVER_URL = "https://noga-yoga-multi-db.herokuapp.com/meditation/";
    private final Context context;
    private final FragmentActivity fragmentActivity;

    public DynamoDBController(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }

    public DynamoDBController(Context context) {
        this.context = context;
        this.fragmentActivity = null;
    }

    public Boolean getAll(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(DYNAMO_SERVER_URL + "get-all")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("SUCCESS ::", "GET ALL");
                    Log.d("GET ALL: Meditation", response.body().toString());
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MeditationFragment()).commit();
                }else{
                    Log.d("FAILED ::", "GET ALL");
                }
            }
        });
        return true;
    }


}
