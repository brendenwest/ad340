package brisksoft.com.ad340

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.content_movie.*

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar.*

class MovieActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val b = this.intent.extras
        val movieData = b?.getStringArray(RecyclerActivity.EXTRA_MESSAGE)

        val imageView = findViewById(R.id.movieImage) as ImageView
        if (movieData !== null) {
            movieTitle.text = movieData[0]
            movieYear.text = movieData[1]
            movieDesc.text = movieData[4]
            Picasso.get().load(movieData[3]).into(imageView);
        }
    }

}
