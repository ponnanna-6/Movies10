package com.example.movies10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MovieDetails extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        //get the intent info from MainActivity.class
        Intent intent = getIntent();

        String imageUrl = intent.getExtras().getString("imgUrl");

        //find image view and set to the intent selected
        ImageView imageView = (ImageView) findViewById(R.id.movie_image_detail);

        Picasso.get().load(imageUrl)
                .centerCrop()
                .fit()
                .into(imageView);
    }
}
