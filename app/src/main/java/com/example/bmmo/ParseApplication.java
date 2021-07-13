package com.example.bmmo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {


    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Workout.class);
        ParseObject.registerSubclass(Profile.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zhEbL731kdgduHF6c4E8Y2b1RimX8wdck47BTOJI")
                .clientKey("7ZFscsxl7ftkXxW19pJpgUFs5xjJbTIuPQKJfZDa")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
