package com.brisksoft.ad340

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso

class TrafficCamActivity : AppCompatActivity() {
    var cameraList: ListView? = null
    var listAdapter: CameraListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_cam)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // check network status
        if (Utils.hasNetworkConnection(this)) {
            TrafficCam.loadCameraData(this) { data ->
                cameraList = findViewById(R.id.cameraList)
                listAdapter = CameraListAdapter(data)
                cameraList?.setAdapter(listAdapter)
            }
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
            val camera = values[position]
            description.text = camera.description
            val imageUrl = camera.imageUrl()
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(image)
            }
            return rowView
        }
    }

}