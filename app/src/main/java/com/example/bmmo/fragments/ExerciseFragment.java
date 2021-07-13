package com.example.bmmo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmmo.Workout;
import com.example.bmmo.ExerciseAdapter;
import com.example.bmmo.Quiz;
import com.example.bmmo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ExerciseFragment extends Fragment {

    private final int REQUEST_CODE = 20;
    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    public ExerciseAdapter adapter;
    public List<Workout> allWorkouts;
    private SwipeRefreshLayout swipeContainer;

    public ExerciseFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.quiz_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.quiz_menu) {
//            Intent intent = new Intent(getActivity(), Quiz.class);
//            startActivityForResult(intent,REQUEST_CODE);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragments_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allWorkouts = new ArrayList<>();
        adapter = new ExerciseAdapter(getContext(), allWorkouts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
//         Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//         Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                adapter.clear();
                queryPosts();
            }
        });
        queryPosts();
    }



    protected void queryPosts() {
        ParseQuery<Workout> query = ParseQuery.getQuery(Workout.class);
        query.include(Workout.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Workout.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Workout>() {
            @Override
            public void done(List<Workout> workouts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue getting posts",e);
                    return;
                }
//                for (Exercise exercise : exercises){
//                    Log.i(TAG,"Post: " + exercise.getUser() + ", username: " + exercise.getUser().getUsername());
//                }
//                Log.i(TAG,workouts.get(2).getUser().getUsername());
                allWorkouts.addAll(workouts);
//                adapter.addAll(allExercises);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
    }

}