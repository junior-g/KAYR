package com.example.abis.kayr.BRActivities;

import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import 	android.content.pm.PackageManager;
import android.Manifest;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.R;
import com.example.abis.kayr.VeryFirst;
import com.example.abis.kayr.loginRegister.ProfileActivity;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.example.abis.kayr.CustomJsonRequest.CustomRequest;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.vision.text.Text;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AddRiver extends AppCompatActivity {

    private EditText  etRiverName,  etState, etTypes;
    private TextView txShowResult, btYes, btNo;
    private Button btSubmit;
    private String sdUserID, sdRiverName;
    private TextInputLayout show1, show2, show3, show4, show5, show6, show7, show8, show9, sowdrain1pH, sowdrain2pH, sowdrain3pH;
    private TextInputLayout sowdrain1tds, sowdrain2tds, sowdrain3tds;


    double latitude;
    double longitude;
    private List<String> correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_river);

        getLatLang();

        sdUserID=getIntent().getStringExtra("UserID");

        initAll();
        listnerall();
    }


    @Override
    protected void onStop() {

        for(int i=0; i<10000000; ++i)
        {
            if(i==999999)
                super.onStop();
        }
        Log.e("On stop", ":stopped");

    }

    private void getLatLang()
    {

        if (ContextCompat.checkSelfPermission(AddRiver.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(AddRiver.this, Manifest.permission.ACCESS_COARSE_LOCATION)
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

    private void initAll()
    {

        etRiverName=(EditText)findViewById(R.id.et_RiverName);
        etState=(EditText)findViewById(R.id.et_State);
        etTypes=(EditText)findViewById(R.id.et_typesOfUses);


        txShowResult=(TextView)findViewById(R.id.tx_ShowResult);
        btYes=(TextView)findViewById(R.id.bt_yes);
        btNo=(TextView)findViewById(R.id.bt_no);

        btSubmit=(Button)findViewById(R.id.bt_submit);

        show1=(TextInputLayout)findViewById(R.id.show1);
        show2=(TextInputLayout)findViewById(R.id.show2);
        show3=(TextInputLayout)findViewById(R.id.show3);


        correct=new ArrayList<>();
        correct.add("rush");
        correct.add("waste");
        correct.add("sh");
    }

    private void listnerall()
    {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Complete it
                String RiverName=etRiverName.getText().toString().trim().toLowerCase();
                String State=etState.getText().toString().trim().toLowerCase();

                if(etRiverName.getText().toString().matches("")
                        ||etState.getText().toString().matches(""))
                {
                    txShowResult.setText("*Empty data!! Fill again");
                    txShowResult.setVisibility(View.VISIBLE);
                    txShowResult.setTextColor(getResources().getColor(R.color.RED));

                }
                if(correct.contains(etTypes.getText().toString().trim().toLowerCase()))
                {
                    txShowResult.setText("*Not a Valid word! please try again");
                    txShowResult.setVisibility(View.VISIBLE);
                    txShowResult.setTextColor(getResources().getColor(R.color.RED));
                }
                else {




                    String result = "Want to add the following water body"
                            + "\t Name:-" + etRiverName.getText().toString()
                            +"\n State:-"+etState.getText().toString()
                            +"\n Latitude:-"+latitude
                            +"\n Longitude:-"+longitude;
                    btSubmit.setVisibility(View.GONE);
                    etRiverName.setVisibility(View.GONE);
                    etState.setVisibility(View.GONE);
                    etTypes.setVisibility(View.GONE);

                    txShowResult.setText(result);
                    txShowResult.setVisibility(View.VISIBLE);
                    btYes.setVisibility(View.VISIBLE);
                    btNo.setVisibility(View.VISIBLE);

                    show1.setVisibility(View.GONE);
                    show2.setVisibility(View.GONE);
                    show3.setVisibility(View.GONE);

                    RadioGroup bttour, btcol, btodr, bttype;
                    TextView txty, txcol, txodr, txtour;
                    txty=(TextView)findViewById(R.id.txType);
                    txcol=(TextView)findViewById(R.id.txcolor);
                    txodr=(TextView)findViewById(R.id.txOdor);
                    txtour=(TextView)findViewById(R.id.txtour);

                    bttour=(RadioGroup)findViewById(R.id.btTour);
                    btcol=(RadioGroup)findViewById(R.id.btColor);
                    btodr=(RadioGroup)findViewById(R.id.btOdor);
                    bttype=(RadioGroup)findViewById(R.id.btType);

                    txty.setVisibility(View.GONE);
                    txcol.setVisibility(View.GONE);
                    txodr.setVisibility(View.GONE);
                    txtour.setVisibility(View.GONE);

                    btcol.setVisibility(View.GONE);
                    btodr.setVisibility(View.GONE);
                    bttour.setVisibility(View.GONE);
                    bttype.setVisibility(View.GONE);
                }

            }
        });

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendJson();
                AddRiver.this.finish();
            }
        });
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRiver.this.finish();
            }
        });
    }
    private void SendJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {


            RadioGroup ty=(RadioGroup)findViewById(R.id.btType);
            RadioGroup col=(RadioGroup)findViewById(R.id.btColor);
            RadioGroup odr=(RadioGroup)findViewById(R.id.btOdor);
            RadioGroup tourist=(RadioGroup)findViewById(R.id.btTour);

            int tyid=ty.getCheckedRadioButtonId();
            int colid=col.getCheckedRadioButtonId();
            int odrid=odr.getCheckedRadioButtonId();
            int tourid=tourist.getCheckedRadioButtonId();

            RadioButton btty=(RadioButton)findViewById(tyid);
            RadioButton btcolid=(RadioButton)findViewById(colid);
            RadioButton btodrid=(RadioButton)findViewById(odrid);
            RadioButton bttourid=(RadioButton)findViewById(tourid);

            jsonObject.put("state",etState.getText().toString().trim().toLowerCase() );
            jsonObject.put("waterbody",etRiverName.getText().toString().trim().toLowerCase());
            jsonObject.put("type_of_uses",etTypes.getText().toString().trim().toLowerCase());
            jsonObject.put("latitude",latitude);
            jsonObject.put("longitude",longitude);
            jsonObject.put("colour",btcolid.getText().toString().trim().toLowerCase());
            jsonObject.put("odour",btodrid.getText().toString().trim().toLowerCase());
                jsonObject.put("tourist",bttourid.getText().toString().trim().toLowerCase());
            jsonObject.put("type",btty.getText().toString().trim().toLowerCase());
        }
        catch (Exception e)
        {
            Log.e("AddRiver ", "creating JSON OBJECT");
        }
        Log.e("Sending", jsonObject.toString());
        Log.e("SendingJson", jsonObject.toString());

        RequestQueue MyRequestQueue = Volley.newRequestQueue(AddRiver.this);
        String url = Constants.BASE_URL;
        CustomRequest stringRequest = new CustomRequest(Request.Method.DEPRECATED_GET_OR_POST, url,jsonObject,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Add River", "Response parsing problem");
                    }
                });
// Add the request to the RequestQueue.
        MyRequestQueue.add(stringRequest);
    }



}
