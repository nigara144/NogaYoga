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

import com.example.nogayoga.Models.Video;
import com.example.nogayoga.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class MyAdapterVideos extends RecyclerView.Adapter<MyAdapterVideos.MyViewHolder> {
    private final List<Video> myVideos;
    private Context context;
    private FragmentActivity fragmentContext;


    public MyAdapterVideos(List<Video> myVideos, Context context, FragmentActivity fragmentContext){
        this.myVideos =myVideos;
        this.context = context;
        this.fragmentContext = fragmentContext;
    }

    @NonNull
    @NotNull
    @Override
    public MyAdapterVideos.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapterVideos.MyViewHolder holder, int position) {
        Video video = this.myVideos.get(position);
        holder.nameText.setText(video.getName());
        holder.durationText.setText(String.valueOf(video.getDuration()));
        Picasso.get().load(Uri.parse(video.getPhotoUrl())).into(holder.videoImg);
        onClickedLinkBtn(holder, video, position);
    }

    private void onClickedLinkBtn(MyViewHolder holder, Video video, int position){
        holder.linkBtn.setOnClickListener(v -> {
            System.out.println("id : " + video.getName());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getLink()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return myVideos.size();
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
