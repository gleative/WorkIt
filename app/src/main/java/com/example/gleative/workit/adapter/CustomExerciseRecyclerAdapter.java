package com.example.gleative.workit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gleative.workit.R;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by gleative on 08.11.2017.
 */

public class CustomExerciseRecyclerAdapter extends RecyclerView.Adapter<CustomExerciseViewHolder>{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbReferenceExercises;

    private Exercise exercise;
    private List<CustomExercise> customExerciseData;
    private LayoutInflater inflater;
    private OnExerciseSelectedListener exerciseSelectedListener;
    private RecycleAdapterListener recycleAdapterListener;

    public CustomExerciseRecyclerAdapter(Context context, List<CustomExercise> data, RecycleAdapterListener _recycleAdapterListener){
        this.customExerciseData = data;
        this.inflater = LayoutInflater.from(context);

        this.recycleAdapterListener = _recycleAdapterListener;

        exerciseSelectedListener = new OnExerciseSelectedListener() {
            @Override
            public void exerciseSelected(int position) {
                CustomExercise customExercise = customExerciseData.get(position);
                recycleAdapterListener.customExerciseSelected(customExercise);
            }
        };
    }

    @Override
    public CustomExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_custom_exercises, parent, false);
        CustomExerciseViewHolder holder = new CustomExerciseViewHolder(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReferenceExercises = firebaseDatabase.getReference().child("exercises");

        view.setOnClickListener(holder);

        return holder;
    }

//    @Override
//    public void onBindViewHolder(final CustomExerciseViewHolder holder, int position) {
//        final CustomExercise currObj = customExerciseData.get(position);
//
//        final String value = String.valueOf(currObj.getExerciseID());
//
//        dbReference = FirebaseDatabase.getInstance().getReference().child("exercises");
//
//
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot exerciseDataSnapshot : dataSnapshot.getChildren()){
//                    // Gets the value of the exercise that customExercise is
//                    if(value.equals(exerciseDataSnapshot.getKey())){
//                        Exercise newExercise = dataSnapshot.child(value).getValue(Exercise.class);
//                        holder.bind(currObj, newExercise, exerciseSelectedListener);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//        // Binds the values to the different views for each item on the list
////        holder.bind(currObj, exercise, exerciseSelectedListener);
//    }

    @Override
    public void onBindViewHolder(CustomExerciseViewHolder holder, int position){
        CustomExercise currObj = customExerciseData.get(position);
//        Exercise exercise = findExercise(currObj.getExerciseID());
//        Exercise exercise = currObj.getExercise();
        findExercise(currObj.getExerciseID(), position);
        holder.bind(currObj, exerciseSelectedListener);
    }

    private void findExercise(final int exerciseID, final int position){
        dbReferenceExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
                    CustomExercise customExercise = customExerciseData.get(position);
                    Exercise currExercise = exerciseSnapshot.getValue(Exercise.class);

                    if(currExercise.getExerciseID() == exerciseID){
                        customExercise.setExercise(currExercise);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return customExerciseData.size();
    }

    // Updates the adapter with new data
    public void updateAdapter(List<CustomExercise> newList){
        this.customExerciseData = newList;
        notifyDataSetChanged();
    }

//    private Exercise getExercise(int position){
//        final CustomExercise currObj = customExerciseData.get(position);
//
//        String value = String.valueOf(currObj.getExerciseID());
//
//        dbReference = FirebaseDatabase.getInstance().getReference().child("exercises").child(value);
//
//
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Gets the value of the exercise that customExercise is
//                Exercise newExercise = dataSnapshot.getValue(Exercise.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//
//        });
//    }
}
