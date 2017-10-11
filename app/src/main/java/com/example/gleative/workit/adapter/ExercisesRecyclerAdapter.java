package com.example.gleative.workit.adapter;

import com.example.gleative.workit.ExerciseInfoActivity;
import com.example.gleative.workit.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.gleative.workit.model.Exercise;

import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class ExercisesRecyclerAdapter extends RecyclerView.Adapter<ExercisesRecyclerAdapter.MyViewHolder>{

    private List<Exercise> exerciseList;
    private LayoutInflater inflater;

    private OnExerciseSelectedListener exerciseSelectedListener;
    private RecycleAdapterListener recycleAdapterListener;


    public ExercisesRecyclerAdapter(Context context, List<Exercise> data, RecycleAdapterListener _recycleAdapterListener){
        this.inflater = LayoutInflater.from(context);
        this.exerciseList = data;

        this.recycleAdapterListener = _recycleAdapterListener;

        exerciseSelectedListener = new OnExerciseSelectedListener(){
            @Override
            public void exerciseSelected(int position){
                Exercise selectedExercise = exerciseList.get(position);
                recycleAdapterListener.exerciseSelected(selectedExercise);
            }
        };
    }



    // Måtte ha disse pga extend RecyclerView

    // Får tak i layoutfilen
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_exercises, parent, false); // Inflater list_exercise layout, sånn at hver data skal få dette utseende
        MyViewHolder holder = new MyViewHolder(view);

        view.setOnClickListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exercise current = exerciseList.get(position);
        holder.bind(current, exerciseSelectedListener);
        holder.setData(current);


    }

    @Override
    public int getItemCount(){
        return exerciseList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        OnExerciseSelectedListener onExerciseSelectedListener;

        public MyViewHolder(View itemView){
            super(itemView);

//            // Gjør at listen er clickbar
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view, int position){
//                    intent = new Intent(view.getContext(), ExerciseInfoActivity.class);
//                    startActivity(intent);
//                }
//            });
            title = itemView.findViewById(R.id.exercise_title);
            description = itemView.findViewById(R.id.exercise_description);
        }

        public void setData(Exercise current){
            this.title.setText(current.getExerciseName()); // Setter titelen på øvelslen
        }

        public void bind(Exercise exercise, OnExerciseSelectedListener listener){
            this.title.setText(exercise.getExerciseName());
            this.onExerciseSelectedListener = listener;

        }

        @Override
        public void onClick(View v){
            onExerciseSelectedListener.exerciseSelected(getAdapterPosition());
        }
    }

}
