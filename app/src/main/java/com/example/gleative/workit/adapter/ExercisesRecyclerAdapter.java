package com.example.gleative.workit.adapter;

import com.example.gleative.workit.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gleative.workit.model.Exercise;

import java.util.List;

/**
 * Created by gleative on 09.10.2017.
 */

public class ExercisesRecyclerAdapter extends RecyclerView.Adapter<ExercisesRecyclerAdapter.MyViewHolder>{

    List<Exercise> exerciseList;
    private LayoutInflater inflater;


    public ExercisesRecyclerAdapter(Context context, List<Exercise> data){
        inflater = LayoutInflater.from(context);
        this.exerciseList = data;
    }



    // Måtte ha disse pga extend RecyclerView

    // Får tak i layoutfilen
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_exercises, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exercise current = exerciseList.get(position);
        holder.setData(current);
    }

    @Override
    public int getItemCount(){
        return exerciseList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;

        public MyViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.exercise_title);
        }

        public void setData(Exercise current){
            this.title.setText(current.getExerciseName()); // Setter titelen på øvelslen
        }

        @Override
        public void onClick(View v) {
        }
    }

}
