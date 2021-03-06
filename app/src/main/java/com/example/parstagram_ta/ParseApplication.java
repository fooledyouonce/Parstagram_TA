package com.example.parstagram_ta;

import android.app.Application;

import com.example.parstagram_ta.models.Comment;
import com.example.parstagram_ta.models.Post;
import com.example.parstagram_ta.models.User;
import com.parse.Parse;

import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //register parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Comment.class);

       // ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("BpRjgMeIbcOlhcWIWEnZeMRcBsza0JzOJBRMQcaZ")
                .clientKey("6VtIjWfBYP8z4bTgmCsb10mxRPhLxa2kcQm9DtSF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
