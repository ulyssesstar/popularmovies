package com.ulisesmm.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Adrian Ulises Mercado Martínez on 26/05/17.
 *
 * This code is based on student and sunshine examples
 * of the course Associate Android Developer Fast Track Nanodegree Program of Udacity
 *
 *
 ***************************************************************************************
 *  IMPORTANT:                                                                         *
 *  To use this program it is necessary to create an account of Themoviedb             *
 *  https://www.themoviedb.org                                                         *
 *  and request the api_key which must be replaced in the final string API_KEY         *
 * *************************************************************************************
 *
 */

public class NetworkUtils {

    /*********************************************
     *  Here the api_key value must be replaced  *
     *********************************************
     */
    private  static final String API_KEY = "";

    private static final String MOVIE_URL= "https://api.themoviedb.org/3/movie/";

    final static String QUERY_LANGUAGE = "language";

    final static String QUERY_PAGE = "page";

    final static String QUERY_PARAM = "api_key";

    /**
     * This method create a url for consult themoviedb
     * @param movieQuery the type of query
     * @return the url if
     */
    public static URL buildUrl(String movieQuery){
        String uritemp = MOVIE_URL+ movieQuery;
        Uri buildUri = Uri.parse(uritemp).buildUpon()
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .appendQueryParameter(QUERY_LANGUAGE, "es-ES")
                .appendQueryParameter(QUERY_PAGE, "1")
                .build()
                ;

        URL url = null;

        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;

    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
