package com.example.parstagram_ta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private Context context;
    private List<Post> posts;

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

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageButton ibLike;
        private ImageButton ibComment;
        private int likeCount = 0;
        private List<ParseUser> likedBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getKeyDescription());
            tvUsername.setText(post.getKeyUser().getUsername());
            ParseFile image = post.getKeyImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //go to profile fragment
                    Fragment fragment = null;
                    fragment = new ProfileFragment();
                }
                //fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            });

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, PostDetails.class);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);
                }
            });

            List<ParseUser>likedBy = new ArrayList<>();

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!post.getLike()) {
                        if(!likedBy.contains(ParseUser.getCurrentUser())) {
                            post.setLike(true);
                            post.setLikeCount(post.getLikeCount() + 1);
                            likedBy.add(ParseUser.getCurrentUser());
                            Log.i(TAG, "likes: " + post.getLikeCount());
                            Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (post.getLike()) {
                        if(likedBy.contains(ParseUser.getCurrentUser())) {
                            post.setLike(false);
                            post.setLikeCount(post.getLikeCount() - 1);
                            likedBy.remove(ParseUser.getCurrentUser());
                            Log.i(TAG, "likes: " + post.getLikeCount());
                            Toast.makeText(context, "Unliked!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    post.saveInBackground();
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