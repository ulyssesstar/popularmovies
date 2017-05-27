package com.ulisesmm.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ulisesmm.popularmovies.util.Movie;
import com.ulisesmm.popularmovies.util.MovieAdapter;
import com.ulisesmm.popularmovies.util.NetworkUtils;
import com.ulisesmm.popularmovies.util.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class MainActivity extends AppCompatActivity  implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private GridLayoutManager gridLayoutManager;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(this,5);
        else
            gridLayoutManager = new GridLayoutManager(this,3);


        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            loadMovieData();
        }else{
            mMovieAdapter.setMovies(savedInstanceState.getParcelableArrayList("movies"));
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", mMovieAdapter.getMovies());
    }



    private void showMovieDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadMovieData(){
        new FetchMovieTask().execute("now_playing");
    }

    private void loadMostPopular(){
        new FetchMovieTask().execute("popular");
    }

    private void loadTopRated(){
        new FetchMovieTask().execute("top_rated");
    }

    @Override
    public void onClick(Movie movieDetail) {
        Context context = this;

        Class destinationClass = MovieDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("original",movieDetail.originalTitle);
        intentToStartDetailActivity.putExtra("poster_path", movieDetail.posterPath);
        intentToStartDetailActivity.putExtra("overview", movieDetail.overview);
        intentToStartDetailActivity.putExtra("rating", movieDetail.voteCount);
        intentToStartDetailActivity.putExtra("date", movieDetail.releaseDate);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if(menuItemThatWasSelected == R.id.mi_order_by_most_popular){
            loadMostPopular();
            return true;
        }
        if(menuItemThatWasSelected == R.id.mi_order_by_top_rated){
            loadTopRated();
            return true;
        }
        if(menuItemThatWasSelected == R.id.mi_latest){
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>>{

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
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovies;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movies != null) {
                showMovieDataView();
                mMovieAdapter.setMovies(movies);
            }else{
                showErrorMessage();
            }
        }
    }


}
