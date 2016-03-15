package in.abmulani.crossoverassignment.interfaces;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

public class CustomLocationListener implements LocationListener {

    private MapView mapView;
    private Context context;

    public CustomLocationListener(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Point latLngPoint = new Point(location.getLatitude(), location.getLongitude());
            Point mapPoint = (Point) GeometryEngine.project(latLngPoint, SpatialReference.create(4326), mapView.getSpatialReference());
            mapView.centerAt(mapView.toScreenPoint(mapPoint), true);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
