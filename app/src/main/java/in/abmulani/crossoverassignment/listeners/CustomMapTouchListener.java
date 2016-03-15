package in.abmulani.crossoverassignment.listeners;

import android.content.Context;
import android.view.MotionEvent;

import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;

import in.abmulani.crossoverassignment.interfaces.MapInteractionListener;

/**
 * Created by aabid-personal on 3/14/16.
 */
public class CustomMapTouchListener extends MapOnTouchListener {

    private MapInteractionListener interactionListener;
    private MapView mapView;

    public CustomMapTouchListener(Context context, MapView view, MapInteractionListener interactionListener) {
        super(context, view);
        this.mapView = view;
        this.interactionListener = interactionListener;
    }

    @Override
    public void onLongPress(MotionEvent point) {
        super.onLongPress(point);
        Point mapPoint = mapView.toMapPoint(point.getX(), point.getY());

        //invoke LongPress event in MapActivity
        interactionListener.onMapLongPressedListener(mapPoint.getX(), mapPoint.getY());
    }

}
