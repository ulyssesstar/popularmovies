package com.ulisesmm.popularmovies.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 */

public class OpenMovieJsonUtils {

    public static ArrayList<Movie> getSimpleMovieStringsFromJson(Context context, String movieJsonStr)throws JSONException{
        final String STATUS_CODE = "status_code";
        final String RESULT ="results";
        ArrayList parseMovieData = new ArrayList();
        JSONObject movieJson = new JSONObject(movieJsonStr);

        if(movieJson.has(STATUS_CODE)){
            int errorCode = movieJson.getInt(STATUS_CODE);

            switch (errorCode){
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
                    return null;
                default:
                    break;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(RESULT);
        for(int i = 0; i <movieArray.length();i++){
            parseMovieData.add(new Movie(movieArray.getJSONObject(i)));
        }

        return parseMovieData;
    }
}
