package brisksoft.com.ad340;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* Map w/ markers
- renders a Google map
- requests location permission
- gets last known location
- centers map on location & sets zoom level
- creates marker for current location
- loads camera data from internet
- populates map markers with camera data
*/

public class TrafficCamMap extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationClient;

    private Location mLastLocation;
    private GoogleMap mMap;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9;


    List<TrafficCam> cameraList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_cams);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load MapFragment and request map-ready notification
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get user's location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        // load camera data
        String dataUrl = " https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
        loadCameraData(dataUrl);

    }

    public void getLocation() {
        Log.d("LOCATION","getLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Log.d("LOCATION","permissionGranted");
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d("LOCATION","gotLocation");

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mLastLocation = location;
                                updateUI();
                            }
                        }
                    });
        } else {
            Log.d("LOCATION","permissionNotGranted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Log.d("LOCATION","requestPermission");

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("LOCATION","onRequestPermissionsResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    updateUI();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // store map object for use once location is available
        Log.d("LOCATION","onMapReady");
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CameraInfoWindow(this));
    }

    /**
     * Loads camera data
     */
    public void loadCameraData(String dataUrl) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (Request.Method.GET, dataUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CAMERAS", response.toString());
                        try {
                            JSONArray features = response.getJSONArray("Features"); // top-level node
                            for(int i = 1; i < features.length(); i++) {
                                JSONObject point = features.getJSONObject(i);
                                JSONArray pointCoords = point.getJSONArray("PointCoordinate");
                                double[] coords = {pointCoords.getDouble(0), pointCoords.getDouble(1)};

                                // points may have more than one camera
                                JSONArray cameras = point.getJSONArray("Cameras");
                                for (int j = 0; j < cameras.length(); j++) {
                                    JSONObject camera = cameras.getJSONObject(j);
                                    TrafficCam c = new TrafficCam(
                                            camera.getString("Description"),
                                            camera.getString("ImageUrl"),
                                            camera.getString("Type"),
                                            coords
                                    );
                                    cameraList.add(c);
                                }
                            }
                            // trigger display of map markers
                            showMarkers();

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", "Error: " + error.getMessage());
                    }

                });
        // Add the request to the RequestQueue.
        queue.add(jsonReq);
    }

    public void updateUI() {
        if (mLastLocation == null) {
            // get location updates
        } else {

            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.setMinZoomPreference(12); // zoom to city level
            mMap.addMarker(new MarkerOptions().position(myLocation)
                    .title("My current location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    /**
     * Load camera data into map markers
     */
    public void showMarkers() {
        Log.d("DATA", cameraList.toString());
        for (int i=0; i < cameraList.size(); i++) {
            TrafficCam c = cameraList.get(i);
            LatLng position = new LatLng(c.getCoords()[0], c.getCoords()[1]);
            Marker m = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(c.getDescription())
                .snippet(c.imageUrl()));
            m.setTag(i);
        }
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }

}
