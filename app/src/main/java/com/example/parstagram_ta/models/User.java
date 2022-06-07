package com.example.parstagram_ta.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject  {
    public static final String KEY_PFP = "pfp";

    public ParseFile getKeyPfp() {
        return getParseFile(KEY_PFP);
    }

    public void setKeyPfp(ParseFile parseFile) {
        put(KEY_PFP, parseFile);
    }
}
