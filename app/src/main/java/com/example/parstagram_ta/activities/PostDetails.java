package com.example.parstagram_ta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram_ta.Post;
import com.example.parstagram_ta.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetails extends AppCompatActivity {

    private TextView tvUser;
    private TextView tvCreatedAt;
    private ImageView ivPic;
    private TextView tvCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUser = findViewById(R.id.tvUser);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        ivPic = findViewById(R.id.ivPic);
        tvCaption = findViewById(R.id.tvCaption);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUser.setText(post.getKeyUser().getUsername());
        Date time = post.getCreatedAt();
        tvCreatedAt.setText(post.getCreatedAt().toString());
        ParseFile image = post.getKeyImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivPic);
        }
        tvCaption.setText(post.getKeyDescription());
    }
}