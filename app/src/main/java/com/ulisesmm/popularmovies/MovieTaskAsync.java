package com.ulisesmm.popularmovies;

import com.ulisesmm.popularmovies.util.Movie;

import java.util.ArrayList;

/**
 * Created by ulises on 28/05/17.
 */

public interface MovieTaskAsync {
    public void movieFinishedTask(ArrayList<Movie> movies);
}
