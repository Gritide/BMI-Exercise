package com.example.bmmo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bmmo.MainActivity;
import com.example.bmmo.Workout;
import com.example.bmmo.Profile;
import com.example.bmmo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.lang.Math;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ComposeFragment extends Fragment {

    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etDescription;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private Button btnStop;
    private File photoFile;
    private String photoFileName = "photo.jpg";
    private long start = 0;
    private double seconds = 0;

    private long pause;
    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private Spinner spinner;
    private String workout = null;
    private static final String[] paths = {"Pushups", "Squats", "Lunges","Glute bridge"};

   private Chronometer timer;
   private boolean checker;


    public ComposeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

//        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        //ivPostImage = view.findViewById(R.id.ivPostImage);
        super.onCreate (savedInstanceState);
        timer = view.findViewById (R.id.timer);

        spinner = (Spinner) view.findViewById (R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (((MainActivity) getActivity ()),

                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter (adapter);

        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v ("item", (String) parent.getItemAtPosition (position));
                workout = (String) parent.getItemAtPosition (position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //START
        btnStop = view.findViewById (R.id.btnStop);
        btnSubmit = view.findViewById (R.id.btnSubmit);
        //        queryPosts();
        btnSubmit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (workout != null) {
                    Log.v ("item", workout);

                }
                btnStop.setEnabled(true);
                btnSubmit.setEnabled(false);
                start = System.nanoTime();
                spinner.setEnabled(false);
                spinner.setClickable(false);
                if (!checker) {
                    timer.setBase(SystemClock.elapsedRealtime ()-pause);
                    timer.start ();
                    checker = true;
                }


//                ParseUser currentUser = ParseUser.getCurrentUser();
//                savePost(description,currentUser,photoFile);
            }
        });
        //STOP
        btnStop.setEnabled(false);
        btnStop.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (workout != null) {
                    Log.v ("item", workout);
                }
                btnStop.setEnabled(false);
                btnSubmit.setEnabled(true);
                timer.setBase (SystemClock.elapsedRealtime ());
                pause=0;
                spinner.setEnabled(true);
                spinner.setClickable(true);
                if (checker) {
                    timer.stop ();
                    long end = System.nanoTime();
                    long elapsedTime = end - start;
                    seconds = (double)elapsedTime / 1_000_000_000.0;
                    System.out.println( seconds );
                    pause=SystemClock.elapsedRealtime () - timer.getBase ();
                    checker = false;
                }

//                ParseUser currentUser = ParseUser.getCurrentUser();
//                savePost(description,currentUser,photoFile);
                double xp = seconds/10;
                if (seconds >7200)
                {
                    Log.e ("You need a break ", String.valueOf (seconds));

                }
                saveExercise(ParseUser.getCurrentUser(), seconds, workout,xp);
            }
        });


    }



    private void saveExercise(ParseUser currentUser, double time, String name, double exp) {
//        ParseObject work = new ParseObject("workout");
        Workout work = new Workout();
        work.put("time",time);
        work.put("user",currentUser);
        work.put("name",name);
        work.put("experience",exp);
        work.saveInBackground();
        currentUser.saveInBackground();
        work.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Log.e(TAG,"Error saving",e);
                    Toast.makeText(getContext(),"Error saving",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.whereEqualTo(Profile.KEY_USER,currentUser);
        query.include(Profile.KEY_USER);
        query.findInBackground(new FindCallback<Profile>() {
            @Override
            public void done(List<Profile> profile, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue getting posts", e);
                    return;
                }
//                Log.i(TAG,String.valueOf(exp));
                profile.get(0).setExperience(exp);
                Log.i(TAG,profile.get(0).getUser().getUsername());
                int level = profile.get(0).getLevel();
                profile.get(0).setLevel((int)Math.floor((Math.sqrt(625+100*profile.get(0).getExperience())-25)/50));
                if (level!=profile.get(0).getLevel() ){
                    Toast.makeText(getContext(),"You leveled up!",Toast.LENGTH_LONG).show();
                }
                Log.i(TAG,String.valueOf(profile.get(0).getExperience()));
                profile.get(0).saveInBackground();
            }
        });
    }




}



//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ComposeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ComposeFragment newInstance(String param1, String param2) {
//        ComposeFragment fragment = new ComposeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//    }