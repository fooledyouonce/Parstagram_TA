package com.example.parstagram_ta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import com.example.parstagram_ta.R;
import com.example.parstagram_ta.fragments.ComposeFragment;
import com.example.parstagram_ta.fragments.PostsFragment;
import com.example.parstagram_ta.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import java.util.Objects;

//TODO: Create new user

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment(ParseUser.getCurrentUser());
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, Objects.requireNonNull(fragment)).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            goLoginActivity();
            return true;
        } else if (item.getItemId() == R.id.upload) { goUploadActivity(); }
        return super.onOptionsItemSelected(item);
    }

    private void goUploadActivity() {
        Intent i = new Intent(this, UploadActivity.class);
        startActivity(i);
        finish();
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
