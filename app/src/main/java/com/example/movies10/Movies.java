package com.example.movies10;

import java.util.ArrayList;

public class Movies extends ArrayList<Movies> {
    private String mMovieImageUrl;
    private String mMovieName;
    private int mMovieNum;

    public Movies(int movieNum, String movieImageUrl, String movieName){
        mMovieNum = movieNum;
        mMovieImageUrl = movieImageUrl;
        mMovieName = movieName;
    }

    public int getMovieNum(){
        return mMovieNum;
    }

    public String getMovieImageUrl(){
        return mMovieImageUrl;
    }

    public String getMovieName(){
        return mMovieName;
    }

}
