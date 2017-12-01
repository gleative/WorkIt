package com.example.gleative.workit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.R;
import com.example.gleative.workit.model.Exercise;

/**
 * Created by gleative on 14.10.2017.
 */

public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView exerciseName, exerciseBodyPart;
    ImageView exerciseThumb1, exerciseThumb2;
    OnExerciseSelectedListener onExerciseSelectedListener;
    Context context;

    public ExerciseViewHolder(View itemView){
        super(itemView);

        context = itemView.getContext();

        // Find the views
        exerciseName = itemView.findViewById(R.id.exercise_title);
        exerciseBodyPart = itemView.findViewById(R.id.exercise_body_part);
        exerciseThumb1 = itemView.findViewById(R.id.exercise_thumb_1);
        exerciseThumb2 = itemView.findViewById(R.id.exercise_thumb_2);

    }

    // Sets the values to the views in the recycler view
    public void bind(Exercise exercise, OnExerciseSelectedListener listener){
        this.exerciseName.setText(exercise.getExerciseName());
        this.exerciseBodyPart.setText(exercise.getBodyPart());

        Glide.with(context).load(exercise.getImageThumb1()).into(exerciseThumb1);
        Glide.with(context).load(exercise.getImageThumb2()).into(exerciseThumb2);

        this.onExerciseSelectedListener = listener;
    }

    @Override
    public void onClick(View v) {
        onExerciseSelectedListener.exerciseSelected(getAdapterPosition());
    }
}
