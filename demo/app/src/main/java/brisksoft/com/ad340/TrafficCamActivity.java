package brisksoft.com.ad340;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrafficCamActivity extends AppCompatActivity {

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;

    ListView cameraList;
    CameraListAdapter listAdapter;

    String dataUrl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";
    ArrayList<TrafficCamera> cameraData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_cam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cameraList = findViewById(R.id.cameraList);
        listAdapter = new CameraListAdapter(this, cameraData);
        cameraList.setAdapter(listAdapter);

        // check network status
        checkNetworkConnection();
        if (wifiConnected || mobileConnected) {
            // initiate data request
            loadCameraData(dataUrl);
        } else {
            // show toast
        }

    }

    public class CameraListAdapter extends ArrayAdapter<TrafficCamera> {
        private final Context context;
        private ArrayList<TrafficCamera> values;

        public CameraListAdapter(Context context, ArrayList<TrafficCamera> values) {
            super(context, 0, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.camera_list_item_layout, parent, false);
            TextView label = rowView.findViewById(R.id.label);
            ImageView image = rowView.findViewById(R.id.image);
            label.setText(values.get(position).label);
            String imageUrl = values.get(position).image;
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(image);
            }
            return rowView;
        }
    }

    /**
     * Loads camera data
     */
    public void loadCameraData(String dataUrl) {

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonReq = new JsonArrayRequest
                (Request.Method.GET, dataUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("CAMERAS 1", response.toString());
                        try {
                            for (int i = 1; i < response.length(); i++) {

                                JSONObject camera = response.getJSONObject(i);

                                double[] coords = {camera.getDouble("ypos"), camera.getDouble("xpos")};
                                TrafficCamera c = new TrafficCamera(
                                        camera.getString("cameralabel"),
                                        camera.getString("ownershipcd"),
                                        camera.getJSONObject("imageurl").getString("url"),
                                        coords
                                );
                                cameraData.add(c);

                            }
                            // trigger refresh of list view once data is loaded & parsed
                            listAdapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                            // error handler
                                Log.d("CAMERAS error", e.getMessage());
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

    private void checkNetworkConnection() {
        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected) {
                Log.i("NETWORK", "WIFI connection");
            } else if (mobileConnected){
                Log.i("NETWORK", "mobile connection");
            }
        } else {
            Log.i("NETWORK", "no network connection");
        }
        // END_INCLUDE(connect)
    }

}
