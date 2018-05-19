package com.example.abis.kayr;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowIng extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ing);


        TextView textView=(TextView)findViewById(R.id.txShow);

      String  RiverName=getIntent().getStringExtra("River_Name");


        JSONObject SendJson=new JSONObject();
        try
        {
            SendJson.put("waterbody",RiverName.toLowerCase());
        }catch (Exception e)
        {

        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"search/river/";
        Log.e("At Show Data", "--------------here");
        //Json
        // Request a string response from the provided URL.
        JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.DEPRECATED_GET_OR_POST, url,SendJson,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("ShowData-------------", response.toString());
                        String s=null;
                        //TODO: check index out of bound
                        try
                        {
                            s="Name:-" +response.getString("waterbody")+"\nType:-"+
                            response.getString("type")+"\nColor:-";
                          s+=  response.getString("colour")+"\nOdour:-"+
                            response.getString("odour");
                          s+="\nType of uses:-"+response.getString("type_of_uses");
                          textView.setText(s);
                        }
                        catch (JSONException e)
                        {
                                            Log.e("String:-", s);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ShowData.this, "Show DATA: Error in Parsing", Toast.LENGTH_LONG).show();
                        Snackbar.make(findViewById(R.id.activity_show_data), "Server Error!", Snackbar.LENGTH_LONG).show();
                    }
                });
// Add the request to the RequestQueue.
        queue.add(JsonRequest);
    }
}
