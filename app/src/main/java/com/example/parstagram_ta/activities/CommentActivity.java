package com.example.parstagram_ta.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.parstagram_ta.R;
import com.example.parstagram_ta.models.Comment;
import com.example.parstagram_ta.models.Post;
import com.parse.ParseException;
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
                            Toast.makeText(CommentActivity.this, "Error while commenting!", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "Saved comment");

                        Intent i = new Intent();
                        i.putExtra("new_comment", comment);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancel) {
            goTimelineActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goTimelineActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); //makes main activity the "default" page, closes upload activity for access
    }
}