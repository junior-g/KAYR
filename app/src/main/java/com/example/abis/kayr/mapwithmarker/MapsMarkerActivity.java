package com.example.abis.kayr.mapwithmarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.CustomJsonRequest.CustomRequest;
import com.example.abis.kayr.RiverMap;
import com.example.abis.kayr.SearchRiverListing.Item;
import com.example.abis.kayr.ShowData;
import com.example.abis.kayr.ShowIng;
import com.example.abis.kayr.VeryFirst;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.abis.kayr.R;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMarkerClickListener,
        OnMapReadyCallback {

    private Marker riverMarker;
    private String RiverName;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setOnMarkerClickListener(this);
        ReadDB();
    }

    private void ReadDB()
    {



        for(int i=0; i<VeryFirst.waterBody.size(); ++i)
        {
            Double lat=VeryFirst.latL.get(i);
            Double lng=VeryFirst.langL.get(i);
            LatLng latLng=new LatLng(lat, lng);
            riverMarker= googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(VeryFirst.waterBody.get(i)));
            RiverName=VeryFirst.waterBody.get(i);
        }

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        RiverName=marker.getTitle();
            Intent i=new Intent(MapsMarkerActivity.this, ShowIng.class);
            i.putExtra("River_Name", RiverName);
            MapsMarkerActivity.this.startActivity(i);
        return false;
    }

}
