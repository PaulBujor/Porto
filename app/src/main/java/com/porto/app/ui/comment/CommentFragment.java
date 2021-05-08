package com.porto.app.ui.comment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.porto.app.R;
import com.porto.app.model.Model;
import com.porto.app.model.adapter.CommentAdapter;
import com.porto.app.model.adapter.PostAdapter;
import com.porto.app.model.models.Comment;
import com.porto.app.model.models.Like;
import com.porto.app.model.models.Post;
import com.porto.app.model.models.holder.LikeHolder;
import com.porto.app.model.models.holder.PostHolder;
import com.porto.app.repository.LikeRepository;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class CommentFragment extends Fragment {
    private RecyclerView comments;

    private TextView comment_post_username;
    private TextView comment_post_text;
    private TextView comment_post_timestamp;
    private TextView comment_post_score;

    private ImageButton shareButton;
    private ImageButton likeButton;
    private ImageButton dislikeButton;

    private ImageView profileImage;

    private EditText commentText;
    private Button sendCommentButton;

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    private CommentViewModel mViewModel;
    private String postID = "0";
    private PostHolder post;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_fragment, container, false);

        postID = getArguments().getString("post");

        comment_post_username = view.findViewById(R.id.comment_post_username);
        comment_post_text = view.findViewById(R.id.comment_post_text);
        comment_post_timestamp = view.findViewById(R.id.comment_post_timestamp);
        comment_post_score = view.findViewById(R.id.comment_post_score);

        shareButton = view.findViewById(R.id.comment_shareButton);
        likeButton = view.findViewById(R.id.comment_likeButton);
        dislikeButton = view.findViewById(R.id.comment_dislikeButton);

        profileImage = view.findViewById(R.id.comment_post_profileImage);

        profileImage.setImageResource(R.drawable.ic_profile);

        comments = view.findViewById(R.id.commentsRecycler);
        commentText = view.findViewById(R.id.writeComment);
        sendCommentButton = view.findViewById(R.id.sendComment);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        post = mViewModel.getPost(postID);

        post.getWrittenBy().getUsername().observeForever((username) -> {
            comment_post_username.setText(username);
        });
        comment_post_text.setText(post.getPost().getText());

        comment_post_timestamp.setText(LocalDateTime.ofInstant(Instant.ofEpochMilli(post.getPost().getTimestamp()),
                TimeZone.getDefault().toZoneId()).toString());

        sendCommentButton.setOnClickListener(view -> {
            if (!commentText.getText().toString().equals(""))
                mViewModel.addComments(new Comment(postID, Model.getInstance().getCurrentUser().getUID(), commentText.getText().toString()));
            commentText.setText("");
        });

        mViewModel.getComments(postID).observeForever(newComments -> {
            CommentAdapter adapter = new CommentAdapter(newComments);
            comments.setLayoutManager(new LinearLayoutManager(getContext()));
            comments.setAdapter(adapter);
        });

        shareButton.setOnClickListener(view -> createSharePostIntent(post));

        setAutoUpdateScore();
    }

    private void setAutoUpdateScore() {
        LikeRepository.getInstance().getLiveLikesOfPost(post).observeForever(likes -> {
            int sumOfLikes = 0;
            boolean postLikedByUser = false, postDislikedByUser = false;

            for (LikeHolder likeHolder : likes) {
                Like like = likeHolder.getLike();
                if (like.getUserId().equals(Model.getInstance().getCurrentUser().getUID())) {
                    if (like.getValue() == 1)
                        postLikedByUser = true;
                    else if (like.getValue() == -1)
                        postDislikedByUser = true;
                }
                sumOfLikes += like.getValue();
            }
            comment_post_score.setText(String.valueOf(sumOfLikes));

            if (postLikedByUser) {
                likeButton.setColorFilter(Color.argb(255, 0, 173, 60));
                dislikeButton.clearColorFilter();
            } else if (postDislikedByUser) {
                dislikeButton.setColorFilter(Color.argb(255, 250, 58, 50));
                likeButton.clearColorFilter();
            } else {
                dislikeButton.clearColorFilter();
                likeButton.clearColorFilter();
            }

            boolean finalPostLikedByUser = postLikedByUser;
            boolean finalPostDislikedByUser = postDislikedByUser;

            likeButton.setOnClickListener(view -> {
                if (finalPostLikedByUser)
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 0));
                else
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 1));
            });

            dislikeButton.setOnClickListener(view -> {
                if (finalPostDislikedByUser)
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), 0));
                else
                    LikeRepository.getInstance().addLike(new Like(post.getPostUID(), Model.getInstance().getCurrentUser().getUID(), -1));
            });
        });
    }

    private void createSharePostIntent(PostHolder post) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, String.format("Saw this post from %s on Porto: \n\n \"%s\"", post.getWrittenBy().getUsername().getValue(), post.getPost().getText()));

        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);
    }

}