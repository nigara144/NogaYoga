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
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.nogayoga.Activities.GeneralActivity;
import com.example.nogayoga.Interfaces.EventReadyCallBack;
import com.example.nogayoga.Models.Event;
import com.example.nogayoga.R;
import com.example.nogayoga.Utils.EventsAdapter;
import com.example.nogayoga.Utils.FirebaseHelper;
import com.example.nogayoga.Utils.MyAdapterVideos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TextView dateView;
    private List<Event> eventList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        setOnClickCalendar();
        return view;
    }

    public void init(View view){
        eventList = new ArrayList<>();
        calendarView= (CalendarView) view.findViewById(R.id.calendar);
        //text view - shows the date selected in the calendar
        dateView = view.findViewById(R.id.date_view);
        recyclerView = view.findViewById(R.id.recycler_view_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setOnClickCalendar() {
        calendarView
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                String dateStr = dayOfMonth + "-" + (month + 1) + "-" + year;
                                loadRecyclerViewData(dateStr);
                                dateView.setText(dateStr);
                            }});
    }

    private void loadRecyclerViewData(String dateStr) {
        eventList.clear();
        getAllEventsByDate(dateStr);
    }

    private void getAllEventsByDate(String dateStr) {
        Log.d("DATE", dateStr);
        //get all events in db where this date is equal to date in timestamp
        CollectionReference collectionRef = FirebaseHelper.db.collection("Classes").
                document(dateStr).collection("Events");
        Task<QuerySnapshot> snapshotTask = collectionRef.get();
        snapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("TAG", String.valueOf(task.getResult().getDocuments().size()));
                    for(DocumentSnapshot doc: task.getResult().getDocuments()){
                        ArrayList<String> userUids = new ArrayList<String>();
                        userUids = (ArrayList<String>)doc.get("userUids");
                        Log.d("USER UIDS ", userUids.toString());
                        Boolean isJoined = checkIfJoined(userUids);
                        eventList.add(new Event(doc.getString("typeName"),
                                doc.getString("time"), doc.getId(), userUids, isJoined));
                    }
                    setRecyclerView(dateStr);
                    Log.d("TAG", eventList.toString());
                }
            }
        });
    }

    private Boolean checkIfJoined(List<String> userUids) {
        return userUids.contains(FirebaseHelper.getUid());
    }

    public void  setRecyclerView(String dateStr){
        adapter = new EventsAdapter(eventList, getContext(), getActivity(), dateStr);
        recyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(adapter.getItemCount());
        }
        Log.d("EVENT: ", String.valueOf(adapter.getItemCount()));
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }
}