package com.example.nogayoga.Utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nogayoga.Models.Meditation;
import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class MyAdapterMeditation extends RecyclerView.Adapter<MyAdapterMeditation.MyViewHolder> {
    private final List<Meditation> myMeditations;
    private Context context;
    private FragmentActivity fragmentContext;


    public MyAdapterMeditation(List<Meditation> myMeditations, Context context, FragmentActivity fragmentContext){
        this.myMeditations =myMeditations;
        this.context = context;
        this.fragmentContext = fragmentContext;
    }

    @NonNull
    @NotNull
    @Override
    public MyAdapterMeditation.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapterMeditation.MyViewHolder holder, int position) {
        Meditation meditation = this.myMeditations.get(position);
        holder.nameText.setText(meditation.getName());
        holder.durationText.setText(String.valueOf(meditation.getDuration()));
        Picasso.get().load(Uri.parse(meditation.getPhotoUrl())).into(holder.videoImg);
        onClickedLinkBtn(holder, meditation, position);
    }

    private void onClickedLinkBtn(MyViewHolder holder, Meditation meditation, int position){
        holder.linkBtn.setOnClickListener(v -> {
            System.out.println("id : " + meditation.getName());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(meditation.getLink()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return myMeditations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        TextView durationText;
        ImageView videoImg;
        Button linkBtn;

        public MyViewHolder(@NonNull @NotNull View view){
            super(view);
            nameText = view.findViewById(R.id.video_name);
            durationText = view.findViewById((R.id.duration));
            videoImg = view.findViewById((R.id.video_img));
            linkBtn = view.findViewById((R.id.link_btn));
        }
    }
}
