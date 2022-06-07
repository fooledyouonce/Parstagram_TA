package com.example.parstagram_ta.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {
    public static final String KEY_PFP = "pfp";

    public ParseFile getKeyPfp() {
        return getParseFile(KEY_PFP);
    }

    public void setPfp(ParseFile parseFile) {
        put(KEY_PFP, parseFile);
    }
}
