package com.example.bmmo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("workout")
public class Workout extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_NAME = "name";
    public static final String KEY_TIME = "time";
    public static final String KEY_EXPERIENCE = "experience";

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public String getName(){
        return getString(KEY_NAME);
    }
    public double getTime(){
        return getDouble(KEY_TIME);
    }
    public double getExperience(){
        return getDouble(KEY_EXPERIENCE);
    }

    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
}
