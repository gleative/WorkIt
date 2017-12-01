package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gleative.workit.R;

/**
 * Created by glenn on 17.10.2017.
 */

public class CreateWorkoutFragment extends Fragment{

    public CreateWorkoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);

        return view;
    }
}
