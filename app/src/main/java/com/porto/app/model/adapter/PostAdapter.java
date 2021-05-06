package com.porto.app.model.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.porto.app.R;
import com.porto.app.model.Model;
import com.porto.app.model.models.Like;
import com.porto.app.model.models.holder.PostHolder;
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

        posts.get(position).getWrittenBy().getUsername().observeForever((username) -> {
            holder.username.setText(username);
        });
        holder.postText.setText(posts.get(position).getPost().getText());
        holder.postTimestamp.setText(LocalDateTime.ofInstant(Instant.ofEpochMilli(posts.get(position).getPost().getTimestamp()),
                TimeZone.getDefault().toZoneId()).toString());

        LikeRepository likeRepo = LikeRepository.getInstance();

        Log.i("Default color", holder.like.getBackground().toString());

        setAutoUpdateScore(holder, position);
    }

    private void setAutoUpdateScore(ViewHolder holder, int position) {
        LikeRepository.getInstance().getLiveLikesOfPost(posts.get(position)).observeForever(likes -> {
            int sumOfLikes = 0;
            boolean postLikedByUser = false, postDislikedByUser = false;
            PostHolder post = posts.get(position);

            for(Like like : likes) {
                if (like.getUserId().equals(Model.getInstance().getCurrentUser().getUID())) {
                    if(like.getValue() == 1)
                        postLikedByUser = true;
                    else if (like.getValue() == -1)
                        postDislikedByUser = true;
                }
                sumOfLikes += like.getValue();
            }
            holder.score.setText(String.valueOf(sumOfLikes));

            if (postLikedByUser) {
                holder.like.setColorFilter(R.color.theme_orange);
                holder.dislike.clearColorFilter();
            } else if (postDislikedByUser) {
                holder.dislike.setColorFilter(R.color.theme_orange);
                holder.like.clearColorFilter();
            } else {
                holder.dislike.clearColorFilter();
                holder.like.clearColorFilter();
            }

            boolean finalPostLikedByUser = postLikedByUser;
            boolean finalPostDislikedByUser = postDislikedByUser;

            holder.like.setOnClickListener(view -> {
                if (finalPostLikedByUser)
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 0));
                else
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 1));
            });

            holder.dislike.setOnClickListener(view -> {
                if (finalPostDislikedByUser)
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 0));
                else
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), -1));
            });
        });
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
