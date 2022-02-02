package com.example.nogayoga.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nogayoga.Fragments.CalendarFragment;
import com.example.nogayoga.Models.Event;
import com.example.nogayoga.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{

    private final List<Event> eventList;
    private Context context;
    private FragmentActivity fragmentContext;
    private String selectedDate;

    public EventsAdapter(List<Event> eventList, Context context, FragmentActivity fragmentContext, String selectedDate) {
        this.eventList = eventList;
        this.context = context;
        this.fragmentContext = fragmentContext;
        this.selectedDate = selectedDate;
    }


    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.MyViewHolder holder, int position) {
        Event event = this.eventList.get(position);
        holder.eventType.setText(event.getTypeName());
        holder.eventTime.setText(event.getTime());
        if(event.getJoined()){
            holder.joinEventBtn.setVisibility(View.GONE);
            holder.unJoinEventBtn.setVisibility(View.VISIBLE);
        }else{
            holder.joinEventBtn.setVisibility(View.VISIBLE);
            holder.unJoinEventBtn.setVisibility(View.GONE);
        }
        onClickedJoinBtn(holder, event, position);
        onClickedUnJoinBtn(holder, event, position);
    }

    private void onClickedJoinBtn(EventsAdapter.MyViewHolder holder, Event event, int position){
        holder.joinEventBtn.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            holder.joinEventBtn.setVisibility(View.GONE);
            holder.unJoinEventBtn.setVisibility(View.VISIBLE);
            System.out.println("joined class : " + event.getTypeName() + " hour: " + event.getTime());
            if(!event.getUserUids().contains(FirebaseHelper.getUid())){
                event.getUserUids().add(FirebaseHelper.getUid());
                map.put("userUids",event.getUserUids());
                updateEventParticipants(event, map);
                updateUserEvents(event, false);
            }
        });
    }

    private void updateUserEvents(Event event, Boolean rmv) {
        DocumentReference documentReference = FirebaseHelper.db.collection("Users").
                document(FirebaseHelper.getUid()).collection("Events").document(event.getUid());
        if(!rmv)
            documentReference.set(event.toJson(this.selectedDate));
        else
            documentReference.delete();
    }

    private void updateEventParticipants(Event event, Map<String, Object> map) {
        //when tapping on join/un join -> updates the firebase
        DocumentReference documentReference = FirebaseHelper.db.collection("Classes").
                document(this.selectedDate).collection("Events").document(event.getUid());
        documentReference.update(map);
    }

    private void onClickedUnJoinBtn(EventsAdapter.MyViewHolder holder, Event event, int position){
        holder.unJoinEventBtn.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            holder.joinEventBtn.setVisibility(View.VISIBLE);
            holder.unJoinEventBtn.setVisibility(View.GONE);
            System.out.println("unjoined class : " + event.getTypeName() + " hour: " + event.getTime());
            if(event.getUserUids().contains(FirebaseHelper.getUid())){
                event.getUserUids().remove(FirebaseHelper.getUid());
                map.put("userUids",event.getUserUids());
                updateEventParticipants(event, map);
                updateUserEvents(event, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventType;
        TextView eventTime;
        Button joinEventBtn;
        Button unJoinEventBtn;

        public MyViewHolder(@NonNull @NotNull View view){
            super(view);
            eventType = view.findViewById(R.id.event_type);
            eventTime = view.findViewById((R.id.event_time));
            joinEventBtn = view.findViewById((R.id.join_btn));
            unJoinEventBtn = view.findViewById((R.id.unjoin_btn));
        }
    }
}
