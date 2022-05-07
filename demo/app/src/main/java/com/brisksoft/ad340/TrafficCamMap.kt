package com.brisksoft.ad340

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
    private var map: GoogleMap? = null
    private val defaultLocation = Location(LocationManager.GPS_PROVIDER)
    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_cams)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        defaultLocation.latitude = 47.6060531
        defaultLocation.longitude = -122.3321

        // Load MapFragment and request map-ready notification
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun checkLocationPermission() {
        Log.d("LOCATION", "checkPermission")
        // Check if the Location permission has been granted
        if ( ContextCompat.checkSelfPermission(this@TrafficCamMap, ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            Log.d("LOCATION", "already granted")
            locationPermissionGranted = true
            // Permission is already available. Get user's location
            getDeviceLocation()
        } else {
            // Permission is missing and must be requested.
            Log.d("LOCATION", "should request")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@TrafficCamMap,
                    ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@TrafficCamMap,
                    arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(this@TrafficCamMap,
                    arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Log.d("LOCATION", "granted")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                locationPermissionGranted = true
                getDeviceLocation()

            } else {
                // Permission request was denied.
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    // [START maps_current_place_get_device_location]
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Log.e("LOCATION", " getDeviceLocation")
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        Log.d("LOCATION", "success. Current location is " + location.toString())
                        updateMap(location)
                    }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
    // [END maps_current_place_get_device_location]

    private fun updateMap(location: Location?) {
        Log.d("LOCATION", "updateMap")
        if (location != null) {
            Log.d("LOCATION", "move map to $location")
            map?.setMinZoomPreference(13f) // zoom to city level
            map?.apply {
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
            // load markers data
            TrafficCam.loadCameraData(applicationContext) { data ->
                showMarkers(data)
            }
        }
    }

    /**
     * Load camera data into map markers
     */
    private fun showMarkers(cameraList: List<TrafficCam>) {
        Log.d("LOCATION", "show markers")
        for (camera in cameraList) {
            map?.apply {
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

    override fun onMapReady(googleMap: GoogleMap) {
        // store map object for use once location is available
        map = googleMap
        Log.d("LOCATION", "map ready")

        // check for user's location
        checkLocationPermission()

    }

    companion object {
        private val TAG = TrafficCamMap::class.java.simpleName
        private const val DEFAULT_ZOOM = 13
    }

}