package com.example.bmmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private Context context;
    private List<Profile> profiles;

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        profiles.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Profile> list) {
        profiles.addAll(list);
        notifyDataSetChanged();
    }


    public StatsAdapter(Context context, List<Profile> profile) {
        this.context = context;
        this.profiles = profile;
    }

    @NonNull
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stats,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
        Profile profile = profiles.get(0);
        holder.bind(profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHealth;
        private TextView tvStamina;
        private TextView tvStrength;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvHealth = itemView.findViewById(R.id.tvHealth);
//            ivImage = itemView.findViewById(R.id.ivImage);
            tvStamina = itemView.findViewById(R.id.tvStamina);
            tvStrength = itemView.findViewById(R.id.tvStrength);

        }

        public void bind(Profile profile) {
//            tvDescription.setText(exercise.getDescription());
            tvHealth.setText("Health: "+profile.getHealth());
            tvStamina.setText("Stamina: "+profile.getStamina());
            tvStrength.setText("Strength: "+profile.getStrength());
//            ParseFile image = exercise.getImage();
//            if (image != null){
//                Glide.with(context)
//                        .load(exercise.getImage().getUrl())
//                        .into(ivImage);
//            }
        }
    }
}
