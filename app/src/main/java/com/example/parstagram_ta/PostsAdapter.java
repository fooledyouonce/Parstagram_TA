package com.example.parstagram_ta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram_ta.activities.PostDetails;
import com.example.parstagram_ta.fragments.ProfileFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private static final String TAG = "PostsAdapter";
    private final Context context;
    private final List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvLikes;
        private final ImageView ivImage;
        private final TextView tvDescription;
        private final ImageButton ibLike;
        private final ImageButton ibComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getKeyDescription());
            tvUsername.setText(post.getKeyUser().getUsername());
            tvLikes.setText(String.valueOf(post.getLikedBy().size()));

            if(post.getLikedBy().contains(ParseUser.getCurrentUser().getObjectId())) {
                ibLike.setColorFilter(Color.RED);
            } else { ibLike.setColorFilter(Color.DKGRAY); }

            ParseFile image = post.getKeyImage();
            if (image != null) { Glide.with(context).load(image.getUrl()).into(ivImage); }

            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //go to profile fragment
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment profileFragment = new ProfileFragment(post.getParseUser(Post.KEY_USER));
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, profileFragment).addToBackStack(null).commit();
                }
            });

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, PostDetails.class);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);
                }
            });

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> likedBy = post.getLikedBy();
                    if(!likedBy.contains(ParseUser.getCurrentUser().getObjectId())) {
                        likedBy.add(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likedBy);
                        ibLike.setColorFilter(Color.RED);
                        Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likedBy);
                        ibLike.setColorFilter(Color.DKGRAY);
                        Toast.makeText(context, "Unliked!", Toast.LENGTH_SHORT).show();
                    }
                    post.saveInBackground();
                    tvLikes.setText(String.valueOf(post.getLikedBy().size()));
                }
            });

            ibComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Comment button clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}