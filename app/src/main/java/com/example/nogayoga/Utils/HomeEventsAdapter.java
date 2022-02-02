package com.example.nogayoga.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nogayoga.Activities.GeneralActivity;
import com.example.nogayoga.Models.Event;
import com.example.nogayoga.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeEventsAdapter extends RecyclerView.Adapter<HomeEventsAdapter.MyViewHolder>{
    private List<Event> joinedEventList;
    private Context context;
    private FragmentActivity fragmentContext;
    private String eventJoinedDate;

    public HomeEventsAdapter(List<Event> joinedEventList, Context context, FragmentActivity activity) {
        this.joinedEventList = joinedEventList;
        this.context = context;
        this.fragmentContext = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_event_row_layout,parent,false);
        return new HomeEventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeEventsAdapter.MyViewHolder holder, int position) {
        Event event = this.joinedEventList.get(position);
        holder.eventType.setText(event.getTypeName());
        holder.eventTime.setText(event.getTime());
        holder.eventDate.setText(event.getEventDate());
    }

    @Override
    public int getItemCount() {
        return joinedEventList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventType;
        TextView eventTime;
        TextView eventDate;

        public MyViewHolder(@NonNull @NotNull View view){
            super(view);
            eventType = view.findViewById(R.id.home_event_type);
            eventTime = view.findViewById((R.id.home_event_time));
            eventDate = view.findViewById((R.id.home_event_date));
        }
    }
}
