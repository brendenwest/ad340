package com.brisksoft.ad340;

import android.os.Bundle;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso;
import com.brisksoft.ad340.Movie.Companion.movies

/**
 * MovieList Fragment using ListView array adapter
 */
class MovieList1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        Log.d("FRAGMENT", "movies 1")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_list1, container, false)

        view.findViewById<ListView>(R.id.imageList).run {
            // specify a viewAdapter
            adapter = MoviesAdapter(requireContext() as MoviesActivity)
        }
        return view
    }


    private class MoviesAdapter(private val context: MoviesActivity) :
        ArrayAdapter<Array<String>>(context,0, movies) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            var itemView = view
            if (itemView == null) {
                itemView = LayoutInflater.from(context)
                .inflate(R.layout.movie_list_item, parent, false)
            }

            itemView!!.findViewById<TextView>(R.id.movieTitle).text = movies[position][0]
            itemView!!.findViewById<TextView>(R.id.movieYear).text = movies[position][1]
            val imageView = itemView!!.findViewById<ImageView>(R.id.movieImage)
            Picasso.get().setIndicatorsEnabled(true);
            Picasso.get().load(movies[position][3]).into(imageView);

            itemView.setOnClickListener {
                val bundle = bundleOf( "movie" to movies[position])

                Navigation.findNavController(itemView).navigate(
                    R.id.action_movieList1_to_movieDetail,
                    bundle)
            }

            return itemView
        }
    }

}
