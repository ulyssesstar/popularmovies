package com.ulisesmm.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.nio.DoubleBuffer;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class MovieDetail extends AppCompatActivity {
    private TextView mOriginalTitle;
    private TextView mOverview;
    private TextView mRating;
    private TextView mReleaseDate;
    private ImageView mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mOriginalTitle = (TextView) findViewById(R.id.tv_original_title_display);
        mOverview =(TextView)findViewById(R.id.tv_overview_display);
        mRating = (TextView)findViewById(R.id.tv_user_rating_display);
        mReleaseDate= (TextView)findViewById(R.id.tv_release_date_display);
        mPoster = (ImageView) findViewById(R.id.iv_movie_poster);

        Intent intent = getIntent();
        if(intent!= null){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                mOverview.setText("Resume:\n"+ extras.getString("overview"));

                mOriginalTitle.setText("Original Title: "+extras.getString("original"));
                Double rate = extras.getDouble("rating");
                mRating.setText("\nRating: "+ rate.toString());

                mReleaseDate.setText("\nRelease Date :" + extras.getString("date"));

                Context context =mPoster.getContext();
                String path ="http://image.tmdb.org/t/p/w780/"+extras.get("poster_path");
                Picasso.with(context).load(path).into(mPoster);
            }
        }
    }

}
