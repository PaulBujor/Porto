package com.porto.app.model.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.porto.app.R;
import com.porto.app.dao.UserDao;
import com.porto.app.manager.Model;
import com.porto.app.model.Like;
import com.porto.app.model.Post;
import com.porto.app.model.holder.PostHolder;
import com.porto.app.repository.LikeRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<PostHolder> posts;

    public PostAdapter(List<PostHolder> posts) {
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
        //todo

        posts.get(position).getAltUser().getUsername().observeForever((username) -> {
            holder.username.setText(username);
        });
        holder.username.setText(posts.get(position).getAltUser().getUsername().getValue());
        holder.postText.setText(posts.get(position).getPost().getText());
        holder.postTimestamp.setText(LocalDateTime.ofInstant(Instant.ofEpochMilli(posts.get(position).getPost().getTimestamp()),
                TimeZone.getDefault().toZoneId()).toString());

        LikeRepository likeRepo = LikeRepository.getInstance();

        Log.i("Default color", holder.like.getBackground().toString());

        Post post = posts.get(position).getPost();
        holder.like.setOnClickListener(view -> {
            if (likeRepo.getPostIsLiked(post) == 1)
                likeRepo.addLike(new Like(post, Model.getInstance().getCurrentUser(), 0));
            else
                likeRepo.addLike(new Like(post, Model.getInstance().getCurrentUser(), 1));
            updateScore(likeRepo, holder, position);
        });
        holder.dislike.setOnClickListener(view -> {
            if (likeRepo.getPostIsLiked(post) == -1)
                likeRepo.addLike(new Like(post, Model.getInstance().getCurrentUser(), 0));
            else
                likeRepo.addLike(new Like(post, Model.getInstance().getCurrentUser(), -1));
            updateScore(likeRepo, holder, position);

        });

        updateScore(likeRepo, holder, position);
    }

    private void updateScore(LikeRepository likeRepo, ViewHolder holder, int position) {
        if (likeRepo.getPostIsLiked(posts.get(position).getPost()) == 1) {
            holder.like.setColorFilter(R.color.theme_orange);
            holder.dislike.clearColorFilter();
        } else if (likeRepo.getPostIsLiked(posts.get(position).getPost()) == -1) {
            holder.dislike.setColorFilter(R.color.theme_orange);
            holder.like.clearColorFilter();
        } else {
            holder.dislike.clearColorFilter();
            holder.like.clearColorFilter();
        }

        holder.score.setText(String.valueOf(likeRepo.getScoreOfPost(posts.get(position).getPost())));
    }

    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView username;
        TextView postText;
        TextView postTimestamp;

        ImageButton like;
        ImageButton dislike;
        TextView score;

        ImageButton comment;
        ImageButton share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.post_profileImage);
            username = itemView.findViewById(R.id.post_username);
            postText = itemView.findViewById(R.id.post_text);
            postTimestamp = itemView.findViewById(R.id.post_timestamp);

            like = itemView.findViewById(R.id.likeButton);
            dislike = itemView.findViewById(R.id.dislikeButton);
            score = itemView.findViewById(R.id.post_score);
        }
    }
}
