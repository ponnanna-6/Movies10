package com.example.movies10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movies> {
    public MovieAdapter(Context context, ArrayList<Movies> moviesArrayList){
        super(context, 0, moviesArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null){
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Movies currentMovie = getItem(position);

        TextView movieNameTextView = (TextView) gridItemView.findViewById(R.id.movie_name);
        ImageView movieImageView = (ImageView) gridItemView.findViewById(R.id.movie_image);

        String movieName = currentMovie.getMovieName();


        Picasso.get().load(currentMovie.getMovieImageUrl())
                .centerCrop()
                .fit()
                .into(movieImageView);
        movieNameTextView.setText(movieName);


        return gridItemView;
    }
}
