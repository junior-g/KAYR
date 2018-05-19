package com.example.abis.kayr;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.CustomJsonRequest.CustomRequest;
import com.example.abis.kayr.SearchRiverListing.AdapterClass_River;
import com.example.abis.kayr.SearchRiverListing.Item;
import com.example.abis.kayr.loginRegister.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class stateRivers extends AppCompatActivity {

    RecyclerView RiverRecyclerView;
    List<Item> riverItems;
    AdapterClass_River riverAdapter;

    private ProgressBar progressBar;

    private String sendState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_rivers);

        sendState=getIntent().getStringExtra("state");

        initViews();

        progressBar.setVisibility(View.VISIBLE);

        setRiverAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        riverItems.clear();
        riverAdapter.notifyDataSetChanged();
        ReadDB();
    }

    private void initViews()
    {
        RiverRecyclerView=(RecyclerView)findViewById(R.id.re_stateRivers);
        RiverRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RiverRecyclerView.setLayoutManager(linearLayoutManager);
        riverItems=new ArrayList<>();

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }
    private void setRiverAdapter()
    {
        riverAdapter = new AdapterClass_River(riverItems, stateRivers.this);
        RiverRecyclerView.setAdapter(riverAdapter);
    }
    private void ReadDB()
    {

        JSONObject sendRequest=new JSONObject();

        try {
            sendRequest.put("state", sendState.toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("stateRiver-----", sendRequest.toString());
        //river names
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(stateRivers.this);
       String  url = Constants.BASE_URL+"search/statename/";
        //Json
        // Request a string response from the provided URL.
        CustomRequest stringRequest = new CustomRequest(Request.Method.POST, url,sendRequest,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray root = response;
                            Log.e("state river name", root.toString());
                            for(int i=0; i<root.length(); ++i)
                            {
                                //River's name
                                riverItems.add(new Item(root.getString(i).toUpperCase()));
                                riverAdapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e) {
                            // If an error is thrown when executing any of the above statements in the "try" block,
                            // catch the exception here, so the app doesn't crash. Print a log message
                            // with the message from the exception.
                            Log.e("VeryFirst", "Problem parsing the  JSON results", e);
                            Snackbar.make(findViewById(R.id.activity_state_rivers), "Problem in parsing!", Snackbar.LENGTH_LONG).show();


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.activity_state_rivers), "Server Error!", Snackbar.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

        progressBar.setVisibility(View.GONE);
    }
}
