package brisksoft.com.ad340;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.squareup.picasso.Picasso;

public class RecyclerWebActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    String url = " https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

    List<TrafficCam> cameraList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        // use a linear layout manager
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new RecyclerWebActivity.CustomAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                    // trigger refresh of recycler view
                    recyclerViewAdapter.notifyDataSetChanged();
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

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mName;
            public ImageView mImage;
            public ViewHolder(View v) {
                super(v);
                mName = v.findViewById(R.id.description);
                mImage = v.findViewById(R.id.image);
            }
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            // Inflate the view for this view holder
            View item = getLayoutInflater().inflate(R.layout.camera_items, parent,
                    false);

            // Call the view holder's constructor, and pass the view to it;
            // return that new view holder
            ViewHolder vh = new ViewHolder(item);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mName.setText(cameraList.get(position).getDescription());
            //String url = baseUrls.get(cameraList.get(position).getType()) + cameraList.get(position).getImageUrl();
            Picasso.get().load(cameraList.get(position).imageUrl()).into(holder.mImage);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return cameraList.size();
        }
    }
}
