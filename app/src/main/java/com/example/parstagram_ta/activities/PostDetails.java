package com.example.parstagram_ta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram_ta.models.Post;
import com.example.parstagram_ta.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostDetails extends AppCompatActivity {
    private TextView tvUser;
    private TextView tvCreatedAt;
    private ImageView ivPic;
    private TextView tvCaption;
    private TextView tvPostLikes;
    private ImageButton ibPostLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUser = findViewById(R.id.tvUser);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        ivPic = findViewById(R.id.ivPic);
        tvPostLikes = findViewById(R.id.tvPostLikes);
        ibPostLikes = findViewById(R.id.ibPostLikes);
        tvCaption = findViewById(R.id.tvCaption);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUser.setText(post.getKeyUser().getUsername());
        tvCreatedAt.setText(post.getCreatedAt().toString());
        ParseFile image = post.getKeyImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivPic);
        }
        tvPostLikes.setText(String.valueOf(post.getLikedBy().size()));
        tvCaption.setText(post.getKeyDescription());

        if (post.getLikedBy().contains(ParseUser.getCurrentUser().getObjectId())) { ibPostLikes.setColorFilter(Color.RED);
        } else { ibPostLikes.setColorFilter(Color.DKGRAY); }

        ibPostLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> likedBy = post.getLikedBy();
                if(!likedBy.contains(ParseUser.getCurrentUser().getObjectId())) {
                    likedBy.add(ParseUser.getCurrentUser().getObjectId());
                    post.setLikedBy(likedBy);
                    ibPostLikes.setColorFilter(Color.RED);
                    //Toast.makeText(PostDetails.this, "Liked!", Toast.LENGTH_SHORT).show();
                }
                else {
                    likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                    post.setLikedBy(likedBy);
                    ibPostLikes.setColorFilter(Color.DKGRAY);
                    //Toast.makeText(PostDetails.this, "Unliked!", Toast.LENGTH_SHORT).show();
                }
                post.saveInBackground();
                tvPostLikes.setText(String.valueOf(post.getLikedBy().size()));
            }
        });
    }
}