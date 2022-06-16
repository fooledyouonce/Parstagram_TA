package com.example.parstagram_ta.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram_ta.R;
import com.example.parstagram_ta.activities.CommentActivity;
import com.example.parstagram_ta.activities.PostDetailsActivity;
import com.example.parstagram_ta.fragments.ProfileFragment;
import com.example.parstagram_ta.models.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private static final String TAG = "ProfileAdapter";
    private final Context context;
    private final List<Post> posts;

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_post, parent, false);
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

        private final ImageView ivUserPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPost = itemView.findViewById(R.id.ivUserPost);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Post post) {
            ParseFile image = post.getImage();
            if (image != null) { Glide.with(context).load(image.getUrl()).into(ivUserPost); }
        }
    }
}
