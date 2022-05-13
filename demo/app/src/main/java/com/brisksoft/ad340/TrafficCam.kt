package com.brisksoft.ad340

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.ArrayList

data class TrafficCam(val description: String, private val image: String, var type: String, var coords: DoubleArray ) {

    private val baseUrl: Map<String, String> = mapOf(
        "sdot" to "http://www.seattle.gov/trafficcams/images/",
        "wsdot" to "http://images.wsdot.wa.gov/nw/"
    )

    fun imageUrl() : String {
        return baseUrl[this.type] + this.image
    }

    companion object {
        var dataUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"

        /**
         * Loads camera data
         */
        fun loadCameraData(context: Context, updateResults: (List<TrafficCam>) -> Unit) {
            val queue = Volley.newRequestQueue(context)
            val cameraList: MutableList<TrafficCam> = ArrayList()

            val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null, { response ->
                Log.d("CAMERAS", response.toString())
                try {
                    val features = response.getJSONArray("Features") // top-level node
                    for (i in 1 until features.length()) {
                        val point = features.getJSONObject(i)
                        val pointCoords = point.getJSONArray("PointCoordinate")

                        // points may have more than one camera
                        val camera = point.getJSONArray("Cameras").getJSONObject(0)
                        val c = TrafficCam(
                            camera.getString("Description"),
                            camera.getString("ImageUrl"),
                            camera.getString("Type"),
                            doubleArrayOf(pointCoords.getDouble(0), pointCoords.getDouble(1))
                        )
                        cameraList.add(c)
                    }
                    // return results to caller
                    updateResults(cameraList)
                } catch (e: JSONException) {
                }
            }) { error -> Log.d("JSON", "Error: " + error.message) }
            // Add the request to the RequestQueue.
            queue.add(jsonReq)
        }
    }
}
