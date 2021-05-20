package com.porto.app.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.porto.app.R;
import com.porto.app.model.models.social.Comment;
import com.porto.app.repository.UserRepository;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_item, parent, false);

        return new CommentAdapter.ViewHolder(view);
    }

    public CommentAdapter() {
    }

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profileImage.setImageResource(R.drawable.ic_profile);
        UserRepository.getInstance().getUsername(comments.get(position).getFromUser()).observeForever(username -> {
            holder.username.setText(username);
        });
        holder.text.setText(comments.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView username;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.comment_item_profileImage);
            username = itemView.findViewById(R.id.comment_item__username);
            text = itemView.findViewById(R.id.comment_item__text);
        }
    }
}
