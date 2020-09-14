package com.example.movies10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movies>> {

    private MovieAdapter mMovieAdapter;
    private static final int MOVIE_LOADER_ID = 1;
    private TextView mEmptyView;
    private String URL = "https://velmm.com/apis/volley_array.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieAdapter = new MovieAdapter(this, new ArrayList<Movies>());

        GridView movieGridView = (GridView) findViewById(R.id.grid);

        mEmptyView = (TextView) findViewById(R.id.empty_view);

        movieGridView.setEmptyView(mEmptyView);
        movieGridView.setAdapter(mMovieAdapter);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(MOVIE_LOADER_ID, null, this);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the movie which is clicked
                Movies currentMovie = mMovieAdapter.getItem(position);

                //Toast message to show movie name as a toast
                Toast toast = Toast.makeText(getApplicationContext(), currentMovie.getMovieName(), Toast.LENGTH_SHORT);
                toast.show();

                //Intent to show image in full screen
                Intent detailIntent = new Intent(getApplicationContext(), MovieDetails.class);
                detailIntent.putExtra("imgName", currentMovie.getMovieName());
                detailIntent.putExtra("imgNum", currentMovie.getMovieNum());
                detailIntent.putExtra("imgUrl", currentMovie.getMovieImageUrl());
                startActivity(detailIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int id, @Nullable Bundle bundle) {
        return new MoviesLoader(this, URL);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(@NonNull Loader<List<Movies>> loader, List<Movies> movies) {
        mMovieAdapter.clear();

        if (movies != null && !movies.isEmpty()) {
                mMovieAdapter.addAll(movies);
            }
        mEmptyView.setText("NO MOVIES FOUND");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> loader) {
        mMovieAdapter.clear();
    }


}

