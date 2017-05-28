package com.ulisesmm.popularmovies.util;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class Movie implements Parcelable{
    public Integer id;
    public Boolean adult;
    public String overview;
    public String releaseDate;
    public String originalTitle;
    public String title;
    public String posterPath;
    public Double popularity;
    public Double voteCount;
    public Double voteAverage;

    /**
     *
     * @param jsonObject this the object to transform in a Movie
     * @throws JSONException
     */
    public Movie(JSONObject jsonObject)throws JSONException {
        this.id = jsonObject.getInt("id");
        this.adult = jsonObject.getBoolean("adult");
        this.overview = jsonObject.getString("overview");
        this.releaseDate = jsonObject.getString("release_date");
        this.originalTitle = jsonObject.getString("original_title");
        this.title = jsonObject.getString("title");
        this.posterPath = jsonObject.getString("poster_path");
        this.popularity = jsonObject.getDouble("popularity");
        this.voteCount = jsonObject.getDouble("vote_count");
        this.voteAverage = jsonObject.getDouble("vote_average");
    }

    /**
     *
     * @param in
     */
    public Movie(Parcel in){
        this.id = in.readInt();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.originalTitle = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readDouble();
        this.voteCount = in.readDouble();
        this.voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeDouble(popularity);
        dest.writeDouble(voteCount);
        dest.writeDouble(voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
