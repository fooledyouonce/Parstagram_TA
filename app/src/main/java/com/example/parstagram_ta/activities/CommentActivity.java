package com.example.parstagram_ta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parstagram_ta.R;

public class CommentActivity extends AppCompatActivity {

    private EditText etComment;
    private Button btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        etComment = findViewById(R.id.etComment);
        btnComment = findViewById(R.id.btnComment);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommentActivity.this, PostDetailsActivity.class);
                CommentActivity.this.startActivity(i);
            }
        });
    }
}