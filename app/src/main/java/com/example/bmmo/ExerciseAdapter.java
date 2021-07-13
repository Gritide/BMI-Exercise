package com.example.bmmo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private Context context;
    private List<Workout> workouts;

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        workouts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Workout> list) {
        workouts.addAll(list);
        notifyDataSetChanged();
    }


    public ExerciseAdapter(Context context, List<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workouts.get(position);
//        Log.i("hi",workout.getUser().getUsername());
        holder.bind(workout);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
//            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Workout workout) {
//            tvDescription.setText(exercise.getDescription());
//            tvUsername.setText(exercise.getUser().getUsername());
            String use = workout.getUser().getUsername();
//            String use = "dogu";
            tvUsername.setText(workout.getCreatedAt().toString());
            tvDescription.setText(use.substring(0, 1).toUpperCase()+ use.substring(1).toLowerCase() +
                    " exercised by doing " + workout.getName() +
                    " for " + (int)workout.getTime() + " seconds, gaining " + (int)workout.getExperience() +" exp.");


//            ParseFile image = exercise.getImage();
//            if (image != null){
//                Glide.with(context)
//                        .load(exercise.getImage().getUrl())
//                        .into(ivImage);
//            }
        }
    }
}
