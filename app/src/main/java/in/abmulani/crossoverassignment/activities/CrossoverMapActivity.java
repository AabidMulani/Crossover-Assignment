package in.abmulani.crossoverassignment.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.abmulani.crossoverassignment.BaseActivity;
import in.abmulani.crossoverassignment.R;
import in.abmulani.crossoverassignment.databases.UserData;
import in.abmulani.crossoverassignment.fragments.AddNewUserDetails;
import in.abmulani.crossoverassignment.fragments.ShowDetailsFragment;
import in.abmulani.crossoverassignment.interfaces.MapInteractionListener;
import in.abmulani.crossoverassignment.listeners.CustomMapTouchListener;
import in.abmulani.crossoverassignment.utils.AppConstants;
import in.abmulani.crossoverassignment.utils.Utils;
import timber.log.Timber;

public class CrossoverMapActivity extends BaseActivity implements MapInteractionListener, OnSingleTapListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.contentLayout)
    RelativeLayout contentLayout;

    List<UserData> patientsPointsList;
    List<UserData> donorPointsList;
    private GraphicsLayer graphicsLayer;
    private boolean showDonorsData;
    private boolean showPatientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        showDonorsData = true;
        showPatientData = true;
        setUpToolBar();
        mapView.setEsriLogoVisible(true);
        mapView.enableWrapAround(true);

        graphicsLayer = new GraphicsLayer();
        mapView.addLayer(graphicsLayer);
        configureLocationSettings();
        // set custom gesture listener
        mapView.setOnTouchListener(new CustomMapTouchListener(this, mapView, this));
        mapView.setOnSingleTapListener(this);

        mapView.setAllowOneFingerZoom(true);
        mapView.setAllowMagnifierToPanMap(true);

        refreshPointsList();
    }

    private void configureLocationSettings() {
        mapView.getLocationDisplayManager().setAccuracyCircleOn(true);
        mapView.getLocationDisplayManager().setShowLocation(true);
//        mapView.getLocationDisplayManager().setLocationListener(new CustomLocationListener(this, mapView));
        mapView.getLocationDisplayManager().start();
    }

    private void refreshPointsList() {
        donorPointsList = new ArrayList<>();
        patientsPointsList = new ArrayList<>();
        updateAllDataList();
        displayPointsOnMap();
    }

    private void updateAllDataList() {
        List<UserData> allDataList = UserData.getAllUserData();
        for (UserData userData : allDataList) {
            Timber.i(userData.toString());
            if (userData.isDonor())
                donorPointsList.add(userData);
            else
                patientsPointsList.add(userData);
        }
    }

    private void displayPointsOnMap() {
        graphicsLayer.removeAll();
        ArrayList<Graphic> graphic = new ArrayList<>();


        if (showDonorsData) {
            for (UserData data : donorPointsList) {
                Timber.i(data.toString());
                graphic.add(new Graphic(new Point(data.getLatitude(), data.getLongitude()), new SimpleMarkerSymbol(AppConstants.COLOR_DONOR, 15, SimpleMarkerSymbol.STYLE.SQUARE)));
            }
            Timber.d("Donor Count: " + donorPointsList.size());
        }

        if (showPatientData) {
            for (UserData data : patientsPointsList) {
                Timber.i(data.toString());
                graphic.add(new Graphic(new Point(data.getLatitude(), data.getLongitude()), new SimpleMarkerSymbol(AppConstants.COLOR_PATIENT, 15, SimpleMarkerSymbol.STYLE.CIRCLE)));
            }
            Timber.d("Patient Count: " + patientsPointsList.size());
        }

        graphicsLayer.addGraphics(graphic.toArray(new Graphic[graphic.size()]));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.pause();
        mapView.getLocationDisplayManager().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.unpause();
        mapView.getLocationDisplayManager().resume();
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                Utils.showLongToast(activity, "Long Press on the Location");
                break;
            case R.id.filterItem:
                initiateFilterItemFlow();
                break;
        }
        return true;
    }

    private void initiateFilterItemFlow() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setIcon(null);
        builderSingle.setTitle(null);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_list_item_single_choice);
        arrayAdapter.add("All Data");
        arrayAdapter.add("Donor Data");
        arrayAdapter.add("Patients Data");


        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showDonorsData = true;
                        showPatientData = true;
                        break;
                    case 1:
                        showDonorsData = true;
                        showPatientData = false;
                        break;
                    case 2:
                        showDonorsData = false;
                        showPatientData = true;
                        break;
                }
                displayPointsOnMap();
            }
        });
        builderSingle.show();
    }

    @Override
    public void onMapLongPressedListener(double latitude, double longitude) {
        Timber.e("onMapLongPressedListener:: " + latitude + "," + longitude);

        AddNewUserDetails newUserDetails = new AddNewUserDetails();
        newUserDetails.setLatitude(latitude);
        newUserDetails.setLongitude(longitude);
        newUserDetails.setMapInteractionListener(this);
        newUserDetails.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onNewDataAdded() {
        //just refresh the current populated data
        refreshPointsList();
    }

    @Override
    public void onSingleTap(float x, float y) {
        int[] indexes = graphicsLayer.getGraphicIDs(x, y, 20);

        if (indexes.length > 0) {
            Timber.d("Some Point has been clicked");
            Graphic selectedGraphic = graphicsLayer.getGraphic(indexes[0]);
            double selectedPointX = ((Point) selectedGraphic.getGeometry()).getX();
            double selectedPointY = ((Point) selectedGraphic.getGeometry()).getY();

            // query from the DB and show all the relevant data
            UserData userData = UserData.getThisPointData(selectedPointX, selectedPointY);
            if (userData != null) {
                ShowDetailsFragment detailsFragment = new ShowDetailsFragment();
                detailsFragment.setCurrentUserData(userData);
                detailsFragment.show(getSupportFragmentManager(), "");
            } else {
                Timber.d("DB Item Not Found");
            }

        } else {
            Timber.d("NO POINT CLICKED");
        }

    }

}
