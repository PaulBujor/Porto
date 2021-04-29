package com.porto.app.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.porto.app.R;
import com.porto.app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public PostAdapter() {
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profileImage.setImageResource(R.drawable.ic_profile);
        holder.name.setText(posts.get(position).getWrittenBy().getName());
        holder.username.setText(String.format("@%s", posts.get(position).getWrittenBy().getUsername()));
        holder.postText.setText(posts.get(position).getText());
        holder.score.setText(String.valueOf(posts.get(position).getScore()));
    }

    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name;
        TextView username;
        TextView postText;
        TextView score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.post_profileImage);
            name = itemView.findViewById(R.id.post_name);
            username = itemView.findViewById(R.id.post_username);
            postText = itemView.findViewById(R.id.post_text);
            score = itemView.findViewById(R.id.post_score);
        }
    }
}
