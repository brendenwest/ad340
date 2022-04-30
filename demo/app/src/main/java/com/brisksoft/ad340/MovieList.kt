package com.brisksoft.ad340

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.brisksoft.ad340.Movie.Companion.movies
import com.squareup.picasso.Picasso
import androidx.core.os.bundleOf
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieList.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_list2, container, false)

        val viewAdapter = MoviesAdapter(movies)

        view.findViewById<RecyclerView>(R.id.imageList).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        return view
    }

    class MoviesAdapter(private val movies: Array<Array<String>>) :
        RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

        class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ViewHolder {
            // create a new view
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.movieTitle).text = movies[position][0]
            holder.item.findViewById<TextView>(R.id.movieYear).text = movies[position][1]

            val mImage = holder.item.findViewById<ImageView>(R.id.movieImage)

            Picasso.get().load(movies[position][3]).into(mImage)

            holder.item.setOnClickListener {
                val bundle = bundleOf( "movie" to movies[position])

                Navigation.findNavController(holder.item).navigate(
                    R.id.action_movieList2_to_movieDetail,
                    bundle)
            }
        }

        override fun getItemCount() = movies.size
    }

}