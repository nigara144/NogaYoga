package com.example.nogayoga.DB;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MongoDBController {

    public static String MONGO_SERVER_URL = "https://aqueous-beyond-11020.herokuapp.com/";
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


}
