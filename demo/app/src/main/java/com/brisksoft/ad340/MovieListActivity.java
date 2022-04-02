package com.brisksoft.ad340;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import com.brisksoft.ad340.R;

public class MovieListActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.brisksoft.ad340.MESSAGE";
    ListView movieList;

    // 2D data array
    String[][] movies = Movie.Companion.getMovies();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        movieList = findViewById(R.id.movieList1);
        MovieListAdapter adapter = new MovieListAdapter(this, movies);
        movieList.setAdapter(adapter);

    }

    private void showMovie(int index) {
        Intent intent = new Intent(this, MovieActivity.class);
        Bundle b=new Bundle();
        b.putStringArray(EXTRA_MESSAGE, movies[index]);
        intent.putExtras(b);
        startActivity(intent);
    }

    public class MovieListAdapter extends ArrayAdapter<String[]> {
        private final Context context;
        private final String[][] values;

        public MovieListAdapter(Context context, String[][] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.movie_list_row_layout, parent, false);
            TextView movieTitle = rowView.findViewById(R.id.movieTitle);
            TextView movieYear = rowView.findViewById(R.id.movieYear);
            ImageView imageView = rowView.findViewById(R.id.movieImage);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(rowView.getContext(), movies[position][0], Toast.LENGTH_SHORT).show();
                    showMovie(position);
                }
            });
            movieTitle.setText(values[position][0]);
            movieYear.setText(values[position][1]);
            Picasso.get().setIndicatorsEnabled(true);
            Picasso.get().load(values[position][3]).into(imageView);

            return rowView;
        }
    }

    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "pausing movie list");
    }
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "stopping movie list");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "destroying movie list");
    }
}
