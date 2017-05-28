package com.ulisesmm.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.ulisesmm.popularmovies.util.Movie;
import com.ulisesmm.popularmovies.util.NetworkUtils;
import com.ulisesmm.popularmovies.util.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Adrian Ulises Mercado Martínez on 28/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private Context context;
    private ProgressBar mLoadingIndicator;
    private MovieTaskAsync movieMainThread;

    public FetchMovieTask(MovieTaskAsync movieMainThread, Context context, ProgressBar loadingIndicator){
        this.context = context;
        this.movieMainThread = movieMainThread;
        mLoadingIndicator = loadingIndicator;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        if(params.length==0){
            return null;
        }

        String urlStr = params[0];
        URL moviesUrl = NetworkUtils.buildUrl(urlStr);
        System.out.println(moviesUrl.toString());

        try{
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesUrl);


            ArrayList<Movie> simpleJsonMovies = OpenMovieJsonUtils
                    .getSimpleMovieStringsFromJson(context, jsonMovieResponse);

            return simpleJsonMovies;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieMainThread.movieFinishedTask(movies);
    }
}
