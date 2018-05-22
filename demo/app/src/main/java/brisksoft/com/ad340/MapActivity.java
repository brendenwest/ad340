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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private GoogleMap mMap;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9;
    protected boolean mAddressRequested;
    protected String mAddressOutput;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mLocationText;

    private AddressResultReceiver mAddressReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddressRequested = true;
        mAddressOutput = "";

        mLatitudeText = (TextView) findViewById(R.id.textLatitude);
        mLongitudeText = (TextView) findViewById(R.id.textLongitude);
        mLocationText = (TextView) findViewById(R.id.textLocation);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationCallback();
        getLocation();

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
                ==PackageManager.PERMISSION_GRANTED){
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
        mMap = googleMap;
        Log.d("LOCATION","onMapReady");
    }


    public void updateUI() {
        if (mLastLocation == null) {
            // get location updates
            Log.d("LOCATION", "startLocationUpdates");
            startLocationUpdates();
        } else {

            // initiate geocode request
            if (mAddressRequested) {
                startIntentService();
            }

            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.setMinZoomPreference(10); // zoom to city level
            mMap.addMarker(new MarkerOptions().position(myLocation)
                    .title("My current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);        }
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
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
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
