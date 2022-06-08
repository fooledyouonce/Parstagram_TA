package com.example.parstagram_ta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parstagram_ta.R;
import com.example.parstagram_ta.models.Comment;
import com.example.parstagram_ta.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";
    private EditText etComment;
    private Button btnComment;

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        post = Parcels.unwrap(getIntent().getParcelableExtra("post_to_comment_on"));

        etComment = findViewById(R.id.etComment);
        btnComment = findViewById(R.id.btnComment);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment();
                comment.setAuthor(ParseUser.getCurrentUser());
                comment.setBody(etComment.getText().toString());
                comment.setPost(post);
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving");
                            Toast.makeText(CommentActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "Saved post");
//                        Intent i = new Intent(CommentActivity.this, PostDetailsActivity.class);
//                        CommentActivity.this.startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
}