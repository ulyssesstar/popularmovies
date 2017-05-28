package com.ulisesmm.popularmovies.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ulisesmm.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class MovieAdapter  extends  RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private final MovieAdapterOnClickHandler mClickHandler;
    List<Movie> mMoviesData;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movieDetail);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_image_movie) ImageView mMovieImageView;
        @BindView(R.id.tv_title) TextView mMovieTextView;

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMoviesData.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context =  viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieHolder, int position) {

        Movie movie = mMoviesData.get(position);
        Context context = movieHolder.mMovieImageView.getContext();
        movieHolder.mMovieTextView.setText(movie.title);

        String path ="http://image.tmdb.org/t/p/w342/"+movie.posterPath;
        Picasso.with(context)
                .load(path)
                .placeholder(R.drawable.moviefilm)
                .error(R.drawable.error_load)
                .into(movieHolder.mMovieImageView);

    }

    @Override
    public int getItemCount() {
        if(null == mMoviesData) return 0;
        return mMoviesData.size();
    }

    public void setMovies(List moviesData){
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

    public ArrayList getMovies(){
        return   (ArrayList) mMoviesData;
    }

}
