package brisksoft.com.ad340

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.util.*

class TrafficCamActivity : AppCompatActivity() {
    var cameraList: ListView? = null
    var listAdapter: CameraListAdapter? = null
    var dataUrl = TrafficCam.dataUrl
    val cameraData : MutableList<TrafficCam> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_cam)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        cameraList = findViewById(R.id.cameraList)
        listAdapter = CameraListAdapter(cameraData)
        cameraList?.setAdapter(listAdapter)

        // check network status
        if (Utils.hasNetworkConnection(this)) {
            loadCameraData(dataUrl)
        } else {
            // show toast
        }
    }

    inner class CameraListAdapter(
        private val values: List<TrafficCam>
    ) : ArrayAdapter<TrafficCam?>(
        applicationContext,
        0,
        values
    ) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = context
                .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.camera_items, parent, false)
            val description = rowView.findViewById<TextView>(R.id.description)
            val image = rowView.findViewById<ImageView>(R.id.image)
            description.text = values[position].description
            val imageUrl = values[position].imageUrl()
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(image)
            }
            return rowView
        }
    }

    /**
     * Loads camera data
     */
    fun loadCameraData(dataUrl: String?) {
        val queue = Volley.newRequestQueue(this)
        val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null, { response ->
            Log.d("CAMERAS 1", response.toString())
            try {
                val features = response.getJSONArray("Features") // top-level node
                for (i in 1 until features.length()) {
                    val point = features.getJSONObject(i)
                    val pointCoords = point.getJSONArray("PointCoordinate")
                    val coords = doubleArrayOf(pointCoords.getDouble(0), pointCoords.getDouble(1))

                    // points may have more than one camera
                    val cameras = point.getJSONArray("Cameras")
                    for (j in 0 until cameras.length()) {
                        val camera = cameras.getJSONObject(j)
                        val c = TrafficCam(
                            camera.getString("Description"),
                            camera.getString("ImageUrl"),
                            camera.getString("Type"),
                            coords
                        )
                        cameraData.add(c)
                    }
                }

                // trigger refresh of list view once data is loaded & parsed
                listAdapter!!.notifyDataSetChanged()
            } catch (e: JSONException) {
                // error handler
                Log.d("CAMERAS error", e.message!!)
            }
        }) { error -> Log.d("JSON", "Error: " + error.message) }
        // Add the request to the RequestQueue.
        queue.add(jsonReq)
    }

}