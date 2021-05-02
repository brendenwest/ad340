package brisksoft.com.ad340;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RecyclerActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.brisksoft.ad340.MESSAGE";

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    // 2D data array
    String[][] movies = Movie.Companion.getMovies();

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        // use a linear layout manager
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new CustomAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void showMovie(int index) {
        Intent intent = new Intent(this, MovieActivity.class);
        Bundle b=new Bundle();
        b.putStringArray(EXTRA_MESSAGE, movies[index]);
        intent.putExtras(b);
        startActivity(intent);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTitle;
            public TextView mDetail;
            public ImageView mImage;
            public ViewHolder(final View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.movieTitle);
                mDetail = itemView.findViewById(R.id.movieYear);
                mImage = itemView.findViewById(R.id.movieImage);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        int pos = getAdapterPosition();
                        Toast.makeText(itemView.getContext(), movies[pos][0], Toast.LENGTH_SHORT).show();
                        showMovie(pos);
                    }
                });
            }
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            // Inflate the view for this view holder
            View item = getLayoutInflater().inflate(R.layout.movie_list_row_layout, parent,
                    false);

            // Call the view holder's constructor, and pass the view to it;
            // return that new view holder
            ViewHolder vh = new ViewHolder(item);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTitle.setText(movies[position][0]);
            holder.mDetail.setText(movies[position][1]);
            Picasso.get().load(movies[position][3]).into(holder.mImage);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return movies.length;
        }
    }
}
