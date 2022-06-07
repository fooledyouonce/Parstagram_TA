package com.example.parstagram_ta;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKE = "like";
    public static final String KEY_COUNT = "likeCount";

    public String getKeyDescription() {
       return getString(KEY_DESCRIPTION);
    }

    public void setKeyDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getKeyImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setKeyImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getKeyUser() {
        return getParseUser(KEY_USER);
    }

    public void setKeyUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public boolean getLike() {
        return getBoolean(KEY_LIKE);
    }

    public void setLike(Boolean like) {
        put(KEY_LIKE, like);
    }

    public int getLikeCount() {
        return getInt(KEY_COUNT);
    }

    public void setLikeCount(int like) {
        put(KEY_COUNT, like);
    }
}
