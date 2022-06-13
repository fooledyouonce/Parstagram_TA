package com.example.parstagram_ta.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.parstagram_ta.adapters.CommentsAdapter;
import com.example.parstagram_ta.models.Comment;
import com.example.parstagram_ta.models.Post;
import com.example.parstagram_ta.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostDetailsActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailsActivity";
    private TextView tvUser;
    private TextView tvCreatedAt;
    private ImageView ivPic;
    private TextView tvCaption;
    private TextView tvPostLikes;
    private ImageButton ibPostLikes;
    private ImageButton ibPostComments;
    private RecyclerView rvComments;
    private CommentsAdapter adapter;
    private List<Comment> allComments;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUser = findViewById(R.id.tvUser);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        ivPic = findViewById(R.id.ivPic);
        tvPostLikes = findViewById(R.id.tvPostLikes);
        ibPostLikes = findViewById(R.id.ibPostLikes);
        ibPostComments = findViewById(R.id.ibPostComments);
        tvCaption = findViewById(R.id.tvCaption);

        rvComments = findViewById(R.id.rvComments);
        allComments = new ArrayList<>();
        adapter = new CommentsAdapter(this, allComments);
        rvComments.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);

        post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        tvUser.setText(post.getUser().getUsername());
        tvCreatedAt.setText(post.getCreatedAt().toString());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivPic);
        }
        tvPostLikes.setText(String.valueOf(post.getLikedBy().size()));
        tvCaption.setText(post.getDescription());

        if (post.getLikedBy().contains(ParseUser.getCurrentUser().getObjectId())) { ibPostLikes.setColorFilter(Color.RED);
        } else { ibPostLikes.setColorFilter(Color.DKGRAY); }

        queryComments();

        ibPostLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> likedBy = post.getLikedBy();
                if(!likedBy.contains(ParseUser.getCurrentUser().getObjectId())) {
                    likedBy.add(ParseUser.getCurrentUser().getObjectId());
                    post.setLikedBy(likedBy);
                    ibPostLikes.setColorFilter(Color.RED);
                }
                else {
                    likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                    post.setLikedBy(likedBy);
                    ibPostLikes.setColorFilter(Color.DKGRAY);
                }
                post.saveInBackground();
                tvPostLikes.setText(String.valueOf(post.getLikedBy().size()));
            }
        });

        ibPostComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PostDetailsActivity.this, "Comment button clicked!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PostDetailsActivity.this, CommentActivity.class);
                i.putExtra("post_to_comment_on", Parcels.wrap(post));
                //noinspection deprecation
                PostDetailsActivity.this.startActivityForResult(i, 109);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //subclass of Parcelable!
            Comment newComment = Objects.requireNonNull(data).getParcelableExtra("new_comment");
            allComments.add(0, newComment);
            adapter.notifyDataSetChanged();
        }
    }

    private void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.whereEqualTo(Comment.KEY_POST, post);
        query.include(Comment.KEY_AUTHOR);
        query.setLimit(20);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Unable to retrieve comments");
                    return;
                }
                for (Comment comment : comments) { Log.i(TAG, "Comment: " + comment.getBody() + ", username: " + comment.getAuthor().getUsername()); }
                allComments.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });
    }
}