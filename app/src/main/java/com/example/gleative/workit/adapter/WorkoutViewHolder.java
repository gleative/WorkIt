package com.example.gleative.workit.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gleative.workit.R;
import com.example.gleative.workit.fragments.WorkoutCustomExercisesListFragment;
import com.example.gleative.workit.fragments.WorkoutListFragment;
import com.example.gleative.workit.model.Workout;

/**
 * Created by glenn on 17.10.2017.
 */

public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView workoutName, workoutAmountExercises;
    private ImageView imgDelete;
    private OnWorkoutSelectedListener onWorkoutSelectedListener;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        workoutName = itemView.findViewById(R.id.workout_title);
        workoutAmountExercises = itemView.findViewById(R.id.workout_exercise_amount);
        imgDelete = itemView.findViewById(R.id.img_delete_workout);
    }

    // Adds listeners to the icons delete and edit
    public void setListenersToImages(){
        imgDelete.setOnClickListener(WorkoutViewHolder.this);
    }

    public void bind(Workout workout, OnWorkoutSelectedListener listener, int layoutType){
        this.workoutName.setText(workout.getWorkoutName());
        this.workoutAmountExercises.setText("Exercises: "); // workout.getCustomExercises(), supposed to have this inside the setText. But it always shows 0.
        this.onWorkoutSelectedListener = listener;

        // Hides the imageview if user is going to start a workout
        if(layoutType == 1){
            imgDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        onWorkoutSelectedListener.workoutSelected(getAdapterPosition());
        onWorkoutSelectedListener.workoutDeleteImageSelected(view, getAdapterPosition());
    }
}
