package com.example.movies10;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {
    private String mUrl;
    public MoviesLoader(@NonNull Context context, String urls) {
        super(context);
        mUrl = urls;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return Utils.fetchMovieData(mUrl);
    }
}
