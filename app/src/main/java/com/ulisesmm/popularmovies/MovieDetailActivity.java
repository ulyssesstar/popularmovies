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
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ulisesmm.popularmovies.util.Movie;

import java.nio.DoubleBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_original_title_display) TextView mOriginalTitle;
    @BindView(R.id.tv_overview_display) TextView mOverview;
    @BindView(R.id.tv_user_rating_display) TextView mRating;
    @BindView(R.id.tv_release_date_display) TextView mReleaseDate;
    @BindView(R.id.iv_movie_poster) ImageView mPoster;
    @BindView(R.id.rb_vote_average) RatingBar mVoteAverage;
    @BindView(R.id.title_movie) TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent!= null){
            //Bundle extras = intent.getExtras();
            Movie movie = intent.getParcelableExtra("movieDetail");
            if(movie!=null){
                mOriginalTitle.setText(movie.originalTitle);
                mTitle.setText(movie.title);
                mOverview.setText(movie.overview);
                mRating.setText(movie.voteAverage.toString());
                String str = movie.voteAverage.toString();
                float rate = Float.parseFloat(str);
                mVoteAverage.setRating(rate);
                mReleaseDate.setText(movie.releaseDate);
                Context context =mPoster.getContext();
                String path ="http://image.tmdb.org/t/p/w780/"+movie.posterPath;
                Picasso.with(context).load(path)
                        .placeholder(R.drawable.moviefilm2)
                        .error(R.drawable.error_load2)
                        //.resize(300,400)
                        .into(mPoster);
            }
        }
    }

}
