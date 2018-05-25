package brisksoft.com.ad340

import android.content.Context
import android.view.View;
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import brisksoft.com.ad340.R.id.parent
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.camera_items.view.*

/** Demonstrates customizing the info window and/or its contents.  */
class CameraInfoWindow (var mContext: Context) : InfoWindowAdapter  {

    private val mWindow: View;
    private var markerId = "";

    init {
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.camera_item_map, null)
    }

    override fun getInfoWindow(marker: Marker): View? {
        Log.d("MARKER", "getInfoWindow");
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        val label = mWindow.findViewById(R.id.description) as TextView
        label.text = marker.title

        val mImage =  mWindow.findViewById(R.id.image) as ImageView
        Picasso.get().load(marker.snippet).into(mImage, InfoWindowRefresher(marker))

        return mWindow

    }

    inner class InfoWindowRefresher(private val markerToRefresh: Marker) : Callback {

        override fun onSuccess() {
            if (!markerToRefresh.id.equals(markerId)) {
                markerId = markerToRefresh.id
                markerToRefresh.showInfoWindow()
            }
        }

        override fun onError(e: Exception) {}
    }

}