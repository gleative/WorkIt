package com.example.gleative.workit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.R;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;

import org.w3c.dom.Text;

/**
 * Created by gleative on 08.11.2017.
 */

public class CustomExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView customExerciseName, customExerciseBodyPart, customExerciseSets, customExerciseReps;
    ImageView customExerciseThumb1, customExerciseThumb2;
    Context context;
    OnExerciseSelectedListener onExerciseSelectedListener;

    public CustomExerciseViewHolder(View itemView){
        super(itemView);

        context = itemView.getContext();

        customExerciseName = itemView.findViewById(R.id.custom_exercise_title);
        customExerciseBodyPart = itemView.findViewById(R.id.custom_exercise_body_part);
        customExerciseSets = itemView.findViewById(R.id.custom_exercise_sets);
        customExerciseReps = itemView.findViewById(R.id.custom_exercise_reps);
        customExerciseThumb1 = itemView.findViewById(R.id.custom_exercise_thumb1);
        customExerciseThumb2 = itemView.findViewById(R.id.custom_exercise_thumb2);

    }

    public void bind(CustomExercise customExercise, OnExerciseSelectedListener listener){
        this.customExerciseName.setText(customExercise.getExercise().getExerciseName());
        this.customExerciseBodyPart.setText(customExercise.getExercise().getBodyPart());
        this.customExerciseSets.setText("Sets  : " + Integer.toString(customExercise.getSets())); // Had to make them to strings for it to display
        this.customExerciseReps.setText("Reps : " + Integer.toString(customExercise.getReps())); // Had to make them to strings for it to display
        Glide.with(context).load(customExercise.getExercise().getImageThumb1()).into(customExerciseThumb1);
        Glide.with(context).load(customExercise.getExercise().getImageThumb2()).into(customExerciseThumb2);
        this.onExerciseSelectedListener = listener;
    }

    @Override
    public void onClick(View v) {
        onExerciseSelectedListener.exerciseSelected(getAdapterPosition());
    }
}
