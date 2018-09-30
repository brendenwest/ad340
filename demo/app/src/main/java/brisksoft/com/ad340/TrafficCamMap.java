package brisksoft.com.ad340;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrafficCamMap extends AppCompatActivity implements
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private Location mLastLocation;
    private GoogleMap mMap;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9;
    protected boolean mAddressRequested;
    protected String mAddressOutput;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mLocationText;

    List<TrafficCam> cameraList = new ArrayList<>();

    private AddressResultReceiver mAddressReceiver;
    private Marker mLastSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_cams);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddressRequested = true;
        mAddressOutput = "";

        mLatitudeText = (TextView) findViewById(R.id.textLatitude);
        mLongitudeText = (TextView) findViewById(R.id.textLongitude);
        mLocationText = (TextView) findViewById(R.id.textLocation);

        // Load MapFragment and request map-ready notification
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get user's location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationCallback();
        getLocation();

        // load camera data
        loadCameraData();

    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d("LOCATION","onLocationResult");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;
                    updateUI();
                }
            };
        };
    }

    @Override
    public void onConnected(Bundle connectionHint) {}

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12));
    }

    /**
     * Loads camera data
     */
    public void loadCameraData() {
        String dataUrl = " https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
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

            // initiate geocode request
            if (mAddressRequested) {
                startIntentService();
            }

            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.setMinZoomPreference(12); // zoom to city level
            mMap.addMarker(new MarkerOptions().position(myLocation)
                    .title("My current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    /**
     * Loads camera data
     */
    public void showMarkers() {
        Log.d("DATA", cameraList.toString());
        for (int i=0; i<cameraList.size(); i++) {
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
    public void onLocationChanged(Location location) {
        Log.d("LOCATION","onLocationChanged");
        mLastLocation = location;
        updateUI();
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mAddressReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("receivedResult", resultData.toString());
            if (resultData == null) {
                return;
            }

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            Log.d("LOCATION", mAddressOutput);
            mLocationText.setText(mAddressOutput);

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        showToast("Connection failed.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            showToast("Disconnected. Please re-connect.");
        } else if (i == CAUSE_NETWORK_LOST) {
            showToast("Network lost. Please re-connect.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddressReceiver = new AddressResultReceiver(new Handler());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }

}
