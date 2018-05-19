package com.example.abis.kayr;


import android.app.PendingIntent;
import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.BRActivities.AddRiver;
import com.example.abis.kayr.SearchRiverListing.AdapterClass_River;
import com.example.abis.kayr.CustomJsonRequest.CustomRequest;
import com.example.abis.kayr.extraFearures.RiverOransiations;
import com.example.abis.kayr.locationhelper.MyLocationUsingLocationAPI;
import com.example.abis.kayr.loginRegister.*;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abis.kayr.SearchRiverListing.Item;

import com.example.abis.kayr.RiverPlansDetails.PlanAdapterClass;
import com.example.abis.kayr.RiverPlansDetails.PlanListItems;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.example.abis.kayr.mapwithmarker.MapsMarkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VeryFirst extends AppCompatActivity {



    public static List<String> StateName;

    //urls
    public  static  String url=null;
    public static String extendWaris=null;
    private double latitude, longitude;

    List<PlanListItems> planListItems;
    private RecyclerView.Adapter planAdapter;
    private RecyclerView planRecyclerView;

    private ProgressBar progressBar;

    //River name
    SearchView searchView;
    RecyclerView RiverRecyclerView;
    List<Item> riverItem;
   public static List<String> waterBody;
    public  static  List<Double> latL, langL;
    AdapterClass_River riverAdapter;

        private String Bodyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_very_first);
        initViews();

        getLatLang();
        progressBar.setVisibility(View.VISIBLE);
        SetAdapters();

    }



    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private void getLatLang()
    {

        if (ContextCompat.checkSelfPermission(VeryFirst.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(VeryFirst.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

        }
        else {
            // Get user location
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Keep track of user location.
            // Use callback/listener since requesting immediately may return null location.
            // IMPORTANT: TO GET GPS TO WORK, MAKE SURE THE LOCATION SERVICES ON YOUR PHONE ARE ON.
            // FOR ME, THIS WAS LOCATED IN SETTINGS > LOCATION.
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Listener());
            // Have another for GPS provider just in case.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Listener());


            // Try to request the location immediately
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (location != null) {
                handleLatLng(location.getLatitude(), location.getLongitude());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            Toast.makeText(getApplicationContext(),
                    "Trying to obtain GPS coordinates. Make sure you have location services on.",
                    Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
//    }


    /**
     * Handle lat lng.
     */
    private void handleLatLng(double latitude, double longitude){
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
    }

    /**
     * Listener for changing gps coords.
     */
    private class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider){}
        public void onStatusChanged(String provider, int status, Bundle extras){}
    }



    @Override
    protected void onResume() {

        progressBar.setVisibility(View.VISIBLE);
        super.onResume();
        riverItem.clear();
        riverAdapter.notifyDataSetChanged();
        planListItems.clear();
        planAdapter.notifyDataSetChanged();
        ReadDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //get river search
        final MenuItem SearchItem=menu.findItem(R.id.search);
        searchView=(SearchView) SearchItem.getActionView();
               //change color
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        //expand search
        searchView.setMaxWidth(Integer.MAX_VALUE);

        //qeary hint
        searchView.setQueryHint("River's Name");
        ///search
        ((EditText) searchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
              SearchItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final  List<Item> filtermodelist=filter(riverItem,newText);
                riverAdapter.setfilter(filtermodelist);
                return false;
            }
        });

        //close button
        MenuItemCompat.setOnActionExpandListener(SearchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Toast.makeText(VeryFirst.this, "onMenuItemActionExpand called", Toast.LENGTH_SHORT).show();
                planRecyclerView.setVisibility(View.GONE);
                RiverRecyclerView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Toast.makeText(VeryFirst.this, "onMenutItemActionCollapse called", Toast.LENGTH_SHORT).show();
                planRecyclerView.setVisibility(View.VISIBLE);
                RiverRecyclerView.setVisibility(View.GONE);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private List<Item> filter(List<Item> pl,String query)
    {
        query=query.toLowerCase();
        final List<Item> filteredModeList=new ArrayList<>();
        for (Item model:pl)
        {
            final String text=model.getRiverName().toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.bt_login:
                Intent i=new Intent(VeryFirst.this, AddRiver.class);
                VeryFirst.this.startActivity(i);
                break;
            case R.id.search:
                planRecyclerView.setVisibility(View.GONE);
                RiverRecyclerView.setVisibility(View.VISIBLE);
                break;
//            case R.id.graph_test:
//                Intent testi=new Intent(VeryFirst.this, ShowData.class);
//                testi.putExtra("River_Name", "abc");
//                VeryFirst.this.startActivity(testi);
//                break;
//            case R.id.bt_RiverMap:
//                Intent iweb=new Intent(VeryFirst.this, RiverMap.class);
//                VeryFirst.this.startActivity(iweb);
//                break;
            case R.id.bt_CurrentLocation:
                Intent loci=new Intent(VeryFirst.this, MyLocationUsingLocationAPI.class);
                VeryFirst.this.startActivity(loci);
                break;
            case R.id.bt_v2riverMap:
                Intent v2Mapi=new Intent(VeryFirst.this, MapsMarkerActivity.class);
                VeryFirst.this.startActivity(v2Mapi);
                break;

            case R.id.bt_riverOrganisations:
                extendWaris=getResources().getString(R.string.riverOrg);
                Intent extend=new Intent(VeryFirst.this, RiverOransiations.class);

                VeryFirst.this.startActivity(extend);
                break;
            case R.id.bt_majorSystem:
                extendWaris=getResources().getString(R.string.majorSystem);
                Intent majorSystem=new Intent(VeryFirst.this, RiverOransiations.class);

                VeryFirst.this.startActivity(majorSystem);
                break;

            case R.id.bt_qMonatring:
                url="http://india-wris.nrsc.gov.in/wrpinfo/index.php?title=River_Water_Quality_Monitoring";
                Intent qMonitring=new Intent(VeryFirst.this, RiverMap.class);
                VeryFirst.this.startActivity(qMonitring);

        }
        return super.onOptionsItemSelected(item);
    }


   void initViews()
    {
        planListItems=new ArrayList<>();
        planRecyclerView=(RecyclerView)findViewById(R.id.re_planView);
        planRecyclerView.setHasFixedSize(true);
        planRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        riverItem=new ArrayList<>();
        //River List
        RiverRecyclerView = (RecyclerView) findViewById(R.id.listshow);
        RiverRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RiverRecyclerView.setLayoutManager(linearLayoutManager);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        StateName=new ArrayList<>();

        waterBody=new ArrayList<>();
        latL=new ArrayList<>();
        langL=new ArrayList<>();
    }
    void ReadDB()
    {



//        for(int i=0; i<3; ++i)
//        {
//            PlanListItems planListItem=new PlanListItems("Title-"+(i+1), "Discription-"+(i+1), R.drawable.modi);
//            planListItems.add(planListItem);
//            planAdapter.notifyDataSetChanged();
//        }
        //state river count
        //river names
        // Instantiate the RequestQueue.
        RequestQueue Statequeue = Volley.newRequestQueue(VeryFirst.this);
        String url =Constants.BASE_URL+"search/state/";
        //Json
        // Request a string response from the provided URL.
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {
                            Log.e("state", response1);
                            JSONObject response=new JSONObject(response1);
                            JSONArray stateArray=(JSONArray)response.getJSONArray("state");
                            Log.e("state Array", stateArray.toString());
                            JSONArray countArray=(JSONArray)response.getJSONArray("count");
                            for(int i=0; i<stateArray.length(); ++i)
                            {
                                StateName.add(stateArray.getString(i).toString().toLowerCase().trim());
                                PlanListItems planListItem=new PlanListItems(stateArray.getString(i).toUpperCase()
                                ,countArray.getString(i).toUpperCase());
                                planListItems.add(planListItem);
                                planAdapter.notifyDataSetChanged();
                            }


                        }catch (JSONException e) {
                            // If an error is thrown when executing any of the above statements in the "try" block,
                            // catch the exception here, so the app doesn't crash. Print a log message
                            // with the message from the exception.
                            Log.e("VeryFirst", "Problem parsing the  JSON results", e);

                        }
                        progressBar.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.activity_very_first), "Server Error!", Snackbar.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        Statequeue.add(stringRequest1);


        //river names
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(VeryFirst.this);
        String url1 =Constants.BASE_URL+"riverlist/";
        //Json
        // Request a string response from the provided URL.
        CustomRequest stringRequest = new CustomRequest(Request.Method.GET, url1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray root = response;
                            for(int i=0; i<root.length(); ++i)
                            {
                                //River's name
                                            JSONObject temp=root.getJSONObject(i);


                                    riverItem.add(new Item(temp.getString("waterbody").toUpperCase()));

                                  Double lat=temp.getDouble("latitude");
                                     Double lng=temp.getDouble("longitude");

                                        double dist=distance(latitude, longitude, lat, lng, "K")*1000;

                                        Log.e("distance", ""+dist+" "+latitude+" "+longitude+" "+lat+" "+lng+" "+temp.getString("waterbody"));

                                         if(Math.abs(dist)<100)
                                         {  Bodyname=temp.getString("name").toUpperCase(); notificationcall();

                                         }

                                         waterBody.add(temp.getString("waterbody").toLowerCase());
                                         latL.add(lat);
                                         langL.add(lng);


                                    riverAdapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e) {
                            // If an error is thrown when executing any of the above statements in the "try" block,
                            // catch the exception here, so the app doesn't crash. Print a log message
                            // with the message from the exception.
                            Log.e("VeryFirst", "Problem parsing the  JSON results", e);
                            Snackbar.make(findViewById(R.id.activity_very_first), "Problem in parsing", Snackbar.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.activity_very_first), "Server Error!", Snackbar.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    void SetAdapters()
    {
        planAdapter=new PlanAdapterClass(planListItems, VeryFirst.this);
        planRecyclerView.setAdapter(planAdapter);

        //River Adapter
        riverAdapter = new AdapterClass_River(riverItem, VeryFirst.this);
        RiverRecyclerView.setAdapter(riverAdapter);
    }

    private static final int NOTIFICATION_ID_OPEN_ACTIVITY = 9;
    public void notificationcall()
    {
        android.support.v7.app.NotificationCompat.Builder notificationBuilder = (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(this)
                .setDefaults(android.support.v7.app.NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background))
                .setContentTitle("Opportunity to enhance")
                .setContentText(Bodyname);

        Intent notifyIntent=new Intent(VeryFirst.this,AddRiver.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(VeryFirst.this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationBuilder.build());

    }

}
