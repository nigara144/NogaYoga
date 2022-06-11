package com.example.nogayoga.DB;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.nogayoga.Fragments.HomeFragment;
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

public class MongoDBController {

    public static String MONGO_SERVER_URL = "https://noga-yoga-multi-db.herokuapp.com/";
    private final Context context;
    private final FragmentActivity fragmentActivity;

    public MongoDBController(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }

    public MongoDBController(Context context) {
        this.context = context;
        this.fragmentActivity = null;
    }

    public Boolean getVideosByLevel(String type){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\n  \"level\": \""+ type +"\"\n }", mediaType);
        Request request = new Request.Builder()
                .url(MONGO_SERVER_URL + "get-all-videos-by-level")
                .method("POST", body)
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
                    Log.d("SUCCESS ::", "insert");
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }else{
                    Log.d("FAILED ::", "insert");
                }
            }
        });
        return true;
    }
}
