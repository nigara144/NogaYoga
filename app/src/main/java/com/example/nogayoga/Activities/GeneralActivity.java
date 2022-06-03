package com.example.nogayoga.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.nogayoga.DB.Mongo;
import com.example.nogayoga.Fragments.CalendarFragment;
import com.example.nogayoga.Fragments.FavoritesFragment;
import com.example.nogayoga.Fragments.HomeFragment;
import com.example.nogayoga.Fragments.ProfileFragment;
import com.example.nogayoga.Models.User;
import com.example.nogayoga.R;
import com.google.android.material.navigation.NavigationBarView;

public class GeneralActivity extends AppCompatActivity {

    private Button profile;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        getUserData();

//        initMongo();

        Log.d("User", user.toString());
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        navigationBarView.setSelectedItemId(R.id.nav_home);

        navigationBarSelection(navigationBarView);
    }

    private void initMongo() {
        Mongo mongo = new Mongo();
        mongo.connect();
        mongo.insert("hod", "hod@g.com", "053324244");
    }

    private void navigationBarSelection(NavigationBarView navigationBarView) {
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_calendar:
                        fragment = new CalendarFragment();
                        break;
                    case R.id.nav_favorites:
                        fragment = new FavoritesFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                return true;
            }
        });
    }


    public void getUserData(){
        user = new User();
        user = User.fromJson(getIntent().getStringExtra("USER"));
    }
}