package com.example.movies10;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utils {

    //Create a URL from a given string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("tag", "Error creating URL ", exception);
        }
        return url;
    }

    //Get the json data from a given url
    private static String makeHttpRequest(URL url) throws IOException {
        //initialize json response to empty
        String jsonResponse = "";

        //if url is null return null as json response
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        //Connecting to the given url
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //If http request says 200 ok read data from stream
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("tag", "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("TAG", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Converts the given input stream into a JSON response
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Extracts each movie details from the given JSON data and adds it to a ArrayList of movies
    private static ArrayList<Movies> extractFeatureFromJson(String moviesJSON) {
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        try {
            JSONArray moviesArray = new JSONArray(moviesJSON);
            int  moviesArrayLength = moviesArray.length();
            ArrayList<Movies> moviesList = new ArrayList<Movies>();

            if (moviesArrayLength > 0) {
                for(int i=0; i<moviesArrayLength; i++){
                    JSONObject movie = moviesArray.getJSONObject(i);
                    int movieNum = Integer.parseInt(movie.getString("id"));
                    String movieImageUrl = movie.getString("image").replace("http", "https");
                    String movieName = movie.getString("title");
                    moviesList.add(new Movies(movieNum, movieImageUrl, movieName));
                }
                return moviesList;
            }
        } catch (JSONException e) {
            Log.e("tag", "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

    /*FINAL METHOD WHICH USES ALL THE ABOVE METHODS TO RETURN MOVIES LIST FROM A GIVEN API URL*/
    public static ArrayList<Movies> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException exception) {
            Log.e("tag", "Error", exception);
        }
        return extractFeatureFromJson(jsonResponse);
    }
}

