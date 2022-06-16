package com.example.parstagram_ta.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.parstagram_ta.EndlessRecyclerViewScrollListener;
import com.example.parstagram_ta.adapters.ProfileAdapter;
import com.example.parstagram_ta.models.Post;
import com.example.parstagram_ta.adapters.PostsAdapter;
import com.example.parstagram_ta.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";
    ParseUser userToFilterBy;
    RecyclerView rvProfile;
    ProfileAdapter adapter;
    List<Post> allPosts;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;

    public ProfileFragment(ParseUser userToFilterBy) {
        this.userToFilterBy = userToFilterBy;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfile = view.findViewById(R.id.rvProfile);
        allPosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), allPosts);
        rvProfile.setAdapter(adapter);

        TextView tvCurrentUser;
        tvCurrentUser = view.findViewById(R.id.tvCurrentUser);
        //tvCurrentUser.setText(post.getUser().getUsername());

        int numberOfColumns = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryPosts(allPosts.size());
            }
        };
        rvProfile.setLayoutManager(gridLayoutManager);
        rvProfile.addOnScrollListener(scrollListener);

        queryPosts(0);

        //SwipeRefresh
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                adapter.clear();
                allPosts.clear();
                queryPosts(0);
            }
        });
    }

    protected void queryPosts(int skipAmount) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, userToFilterBy);
        query.setLimit(20);
        query.setSkip(skipAmount);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Unable to retrieve posts", e);
                    return;
                }
                for (Post post : posts) { Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername()); }

                allPosts.addAll(posts);
                swipeContainer.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });
    }
}