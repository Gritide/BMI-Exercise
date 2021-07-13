package com.example.bmmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bmmo.Profile;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etHeight;
    private EditText etWeight;
    private Button btnRegister;
    private Button btnSwap;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnRegister = findViewById(R.id.btnRegister);
        btnSwap = findViewById(R.id.btnSwap);
        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick swap button");
                goLoginActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick register button");
                String username = etUsername.getText().toString().toLowerCase();
                String password = etPassword.getText().toString();
                double height = Double.parseDouble(etHeight.getText().toString());
                double weight = Double.parseDouble(etWeight.getText().toString());
                try {
                    Register(username, password, height, weight);
                    RegisterUser(username, password);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void Register(String username, String password, double height, double weight) throws ParseException {
//        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        Profile profile = new Profile();
        profile.setUser(newUser);
        profile.setHeightWeight(height, weight);
        profile.setLevel(1);
        newUser.signUp();
        profile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Log.e(TAG,"Error registering",e);
                    Toast.makeText(RegisterActivity.this,"Error registering",Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Registered "+profile.getUser());
            }
        });
    }

    private void RegisterUser(String username, String password) {
        Log.i(TAG, "Trying to register " + username);
        ParseUser.logInInBackground(username.toLowerCase(), password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue registering",e);
                    Toast.makeText(RegisterActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    return;
                }
                goLoginActivity();
                Toast.makeText(RegisterActivity.this,"Success!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
