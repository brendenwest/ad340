package brisksoft.com.ad340

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import java.util.*

const val PERMISSION_REQUEST_LOCATION = 9


/* Map w/ markers
- renders a Google map
- requests location permission
- gets last known location
- centers map on location & sets zoom level
- creates marker for current location
- loads camera data from internet
- populates map markers with camera data
https://www.tutorialspoint.com/how-to-request-location-permission-at-runtime-on-kotlin
*/
class TrafficCamMap : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mMap: GoogleMap? = null
    private var cameraList: MutableList<TrafficCam> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_cams)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Load MapFragment and request map-ready notification
        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            // store map object for use once location is available
            mMap = googleMap
            Log.d("LOCATION", "has map")

            // check for user's location
            checkLocationPermission()

            // load markers data
            loadCameraData(TrafficCam.dataUrl)
        }

    }

    private fun checkLocationPermission() {
        Log.d("LOCATION", "checkPermission")
        // Check if the Location permission has been granted
        if ( ContextCompat.checkSelfPermission(this@TrafficCamMap, ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            Log.d("LOCATION", "already granted")
            // Permission is already available. Get user's location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Log.d("LOCATION", location.toString())

                    // temp hard-code for Seattle
                    if (location == null) {
                        val tmpLocation = Location(LocationManager.GPS_PROVIDER)
                        tmpLocation.latitude = 47.6060531
                        tmpLocation.longitude = -122.3321
                        updateMap(tmpLocation)
                    }

                    updateMap(location)
                }

        } else {
            // Permission is missing and must be requested.
            Log.d("LOCATION", "should request")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@TrafficCamMap,
                    ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this@TrafficCamMap,
                    arrayOf(ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
            } else {
                ActivityCompat.requestPermissions(this@TrafficCamMap,
                    arrayOf(ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Log.d("LOCATION", "granted")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        // Got last known location. In some rare situations this can be null.
                        Log.d("LOCATION2", location.toString())
                        updateMap(location)
                    }

            } else {
                // Permission request was denied.
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * Loads camera data
     */
    private fun loadCameraData(dataUrl: String?) {
        val queue = Volley.newRequestQueue(this)
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
                // trigger display of map markers
                showMarkers()
            } catch (e: JSONException) {
            }
        }) { error -> Log.d("JSON", "Error: " + error.message) }
        // Add the request to the RequestQueue.
        queue.add(jsonReq)
    }

    private fun updateMap(location: Location?) {
        Log.d("LOCATION", "updateMap")
        if (location != null) {
            Log.d("LOCATION", "move map")
            mMap?.setMinZoomPreference(12f) // zoom to city level
            mMap?.apply {
                val position = LatLng(location.latitude, location.longitude)
                addMarker(
                    MarkerOptions()
                        .position(position)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("My Location")
                )
                // [START_EXCLUDE silent]
                moveCamera(CameraUpdateFactory.newLatLng(position))
                // [END_EXCLUDE]
            }
        }
    }

    /**
     * Load camera data into map markers
     */
    private fun showMarkers() {
        Log.d("LOCATION", "show markers")
        for (camera in cameraList) {
            mMap?.apply {
                val position = LatLng(camera.coords[0], camera.coords[1])
                addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(camera.description)
                )
            }
        }
    }

    /**
     * Shows a toast with the given text.
     */
    private fun showToast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

}