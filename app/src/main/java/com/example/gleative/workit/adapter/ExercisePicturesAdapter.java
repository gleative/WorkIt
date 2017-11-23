package com.example.gleative.workit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.R;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by glenn on 16.10.2017.
 */

public class ExercisePicturesAdapter extends PagerAdapter{

    private Activity activity;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images;

    public ExercisePicturesAdapter(Activity activity, ArrayList<String> images){
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpager_item, container, false); // XML file we want to inflate

        // Use GifImageView instead of imageview because a gif is displayed at the end. Since the gif view can display images aswell, this made it alot easier to have both.
        GifImageView imageView = view.findViewById(R.id.image_view_pager);

        // Makes so the images stretch to the whole viewpager
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);

        // Adds the pictures to the view pager, if string starts with "https" we know its from firbase storage, and display it then with glide, otherwise it is from drawable
        try{
            if(images.get(position).startsWith("https")){
                Glide.with(activity.getApplicationContext()).load(images.get(position)).into(imageView);
            }
            else{
                imageView.setImageResource(Integer.parseInt(images.get(position)));
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Failed to display images.", Toast.LENGTH_SHORT).show();

        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
