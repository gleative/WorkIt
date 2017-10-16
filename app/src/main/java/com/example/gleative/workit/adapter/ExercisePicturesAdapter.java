package com.example.gleative.workit.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gleative.workit.R;

/**
 * Created by glenn on 16.10.2017.
 */

public class ExercisePicturesAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.testpicture, R.drawable.e1_1, R.drawable.e1_2};

    public ExercisePicturesAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_exercise_info, null); // XML file we want to inflate
        ImageView imageView = (ImageView) view.findViewById(R.id.exercise_picture); // Gets the image view from fragment_exercise_info layout file
        imageView.setImageResource(images[position]); // Sets the image that is in the current position

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
