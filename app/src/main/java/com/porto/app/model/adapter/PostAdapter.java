package com.porto.app.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.porto.app.R;
import com.porto.app.model.Model;
import com.porto.app.model.models.social.Like;
import com.porto.app.model.models.holder.LikeHolder;
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

        holder.comment.setOnClickListener(view -> {
            String postId = posts.get(position).getPostUID();
            Bundle bundle = new Bundle();
            bundle.putString("post", postId);
            Navigation.findNavController(view).navigate(R.id.viewCommentsAction, bundle);
        });

        holder.share.setOnClickListener(view -> createSharePostIntent(posts.get(position), holder));

        setAutoUpdateScore(holder, position);
    }

    private void createSharePostIntent(PostHolder post, ViewHolder holder) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, String.format("Saw this post from %s on Porto: \n\n \"%s\"", post.getWrittenBy().getUsername().getValue(), post.getPost().getText()));

        Intent shareIntent = Intent.createChooser(intent, null);
        holder.context.startActivity(shareIntent);
    }

    private void setAutoUpdateScore(ViewHolder holder, int position) {
        LikeRepository.getInstance().getLiveLikesOfPost(posts.get(position)).observeForever(likes -> {
            int sumOfLikes = 0;
            boolean postLikedByUser = false, postDislikedByUser = false;
            PostHolder post = posts.get(position);

            for(LikeHolder likeHolder : likes) {
                Like like = likeHolder.getLike();
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
                holder.like.setColorFilter(Color.argb(255, 0, 173, 60));
                holder.dislike.clearColorFilter();
            } else if (postDislikedByUser) {
                holder.dislike.setColorFilter(Color.argb(255, 250, 58, 50));
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

        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.post_profileImage);
            username = itemView.findViewById(R.id.post_username);
            postText = itemView.findViewById(R.id.post_text);
            postTimestamp = itemView.findViewById(R.id.post_timestamp);

            like = itemView.findViewById(R.id.likeButton);
            dislike = itemView.findViewById(R.id.dislikeButton);
            score = itemView.findViewById(R.id.post_score);

            comment = itemView.findViewById(R.id.commentButton);
            share = itemView.findViewById(R.id.shareButton);

            context = itemView.getContext();
        }
    }
}
