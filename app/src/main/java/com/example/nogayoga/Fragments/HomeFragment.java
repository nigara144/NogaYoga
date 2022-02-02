package com.example.nogayoga.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nogayoga.Activities.GeneralActivity;
import com.example.nogayoga.Models.Event;
import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.example.nogayoga.Utils.HomeEventsAdapter;
import com.example.nogayoga.Utils.MyAdapterVideos;
import com.example.nogayoga.Utils.TabsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Event> eventList;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        createTabs();
        return view;
    }

    private void createTabs() {
        TabsAdapter tabsAdapter = new TabsAdapter(getActivity());
        tabsAdapter.addFragment(new BeginnerFragment(), "BEGINNER");
        tabsAdapter.addFragment(new IntermediateFragment(), "INTERMEDIATE");
        tabsAdapter.addFragment(new SkilledFragment(), "SKILLED");
        viewPager.setAdapter(tabsAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabsAdapter.getPageTitle(position))
        ).attach();
    }

    public void init(View view){
        username = view.findViewById(R.id.home_username);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        recyclerView = view.findViewById(R.id.recycler_view_home_events);
        username.setText(GeneralActivity.user.getFullName());
    }

    private void loadRecyclerViewData() {
        getAllJoinedEvents();
    }

    private void getAllJoinedEvents() {

    }

    public void setRecyclerView(){
        adapter = new HomeEventsAdapter(eventList, getContext(), getActivity());
        recyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(adapter.getItemCount());
        }
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }



}