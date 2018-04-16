package brisksoft.com.ad340

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        textView.text = message
    }

    public override fun onStart() {
        super.onStart()  // Always call the superclass method first
        Log.d(TAG, "onStart()")
    }

    public override fun onResume() {
        super.onResume()  // Always call the superclass method first
        Log.d(TAG, "onResume()")
    }

    public override fun onPause() {
        super.onPause()  // Always call the superclass method first
        Log.d(TAG, "onPause()")
    }

    public override fun onStop() {
        super.onStop()  // Always call the superclass method first
        Log.d(TAG, "onStop()")
    }

    public override fun onDestroy() {
        super.onDestroy()  // Always call the superclass method first
        Log.d(TAG, "onDestroy()")
    }

    companion object {
        private val TAG = DetailActivity::class.java!!.getSimpleName()
    }
}
