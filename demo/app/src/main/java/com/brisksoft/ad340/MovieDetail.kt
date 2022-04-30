package com.brisksoft.ad340

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.squareup.picasso.Picasso

private const val ARG_PARAM1 = "movie"

class MovieDetail : Fragment() {

    private var movieData: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieData = it.getStringArray(ARG_PARAM1) as Array<String>?
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)

        val imageView = view.findViewById(R.id.movieImageLarge) as ImageView
        val movieTitle = view.findViewById(R.id.movieTitle) as TextView
        val movieDir = view.findViewById(R.id.movieDirector) as TextView
        val movieYear = view.findViewById(R.id.movieYear) as TextView
        val movieDesc = view.findViewById(R.id.movieDesc) as TextView

        if (movieData !== null) {
            movieTitle.text = movieData!![0]
            movieYear.text = movieData!![1]
            movieDir.text = movieData!![2]
            movieDesc.text = movieData!![4]
            Picasso.get().load(movieData!![3]).into(imageView);
        }
        return view

    }

}
