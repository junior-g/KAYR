package com.example.abis.kayr.BRActivities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.VolleySingleton;
import com.example.abis.kayr.R;
import com.example.abis.kayr.loginRegister.utils.Constants;

import org.json.JSONObject;

public class NewData extends AppCompatActivity {

    private EditText etQuantity, etpH, etDO, etTDS, etTypesOfUses, drain1pH, drain2pH, drain3pH ;
    private EditText drain1tds, drain2tds, drain3tds;
    private TextView txShowResult, btYes, btNo;
    private Button btSubmit;
    private String sdUserID, sdRiverName;
    private TextInputLayout show1, show2, show3, show4, show5, sowdrain1pH, sowdrain2pH, sowdrain3pH;
    private TextInputLayout sowdrain1tds, sowdrain2tds, sowdrain3tds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);

        sdUserID=getIntent().getStringExtra("UserID");
        sdRiverName=getIntent().getStringExtra("RiverName");

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

    private void initAll()
    {
        etQuantity=(EditText)findViewById(R.id.et_quantity);
        etDO=(EditText)findViewById(R.id.et_do);
        etpH=(EditText)findViewById(R.id.et_pH);
        etTDS=(EditText)findViewById(R.id.et_tds);
        etTypesOfUses=(EditText)findViewById(R.id.et_typesOfUses);

        drain1pH=(EditText)findViewById(R.id.et_drain1pH);
        drain1tds=(EditText)findViewById(R.id.et_drain1tds);

        txShowResult=(TextView)findViewById(R.id.tx_ShowResult);
        btYes=(TextView)findViewById(R.id.bt_yes);
        btNo=(TextView)findViewById(R.id.bt_no);

        btSubmit=(Button)findViewById(R.id.bt_submit);

        show1=(TextInputLayout)findViewById(R.id.Show1);
        show2=(TextInputLayout)findViewById(R.id.Show2);
        show3=(TextInputLayout)findViewById(R.id.Show3);
        show4=(TextInputLayout)findViewById(R.id.Show4);
        show5=(TextInputLayout)findViewById(R.id.show12);

        sowdrain1pH=(TextInputLayout)findViewById(R.id.drain1pH);
        sowdrain1tds=(TextInputLayout)findViewById(R.id.drain1tds);
    }
    private void listnerall()
    {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Complete it
                String Quantity=etQuantity.getText().toString().trim();
                double DO=0.0;
                if(!etDO.getText().toString().trim().matches(""))
                    DO=Double.parseDouble(etDO.getText().toString().trim());
                Double pH=0.0;
                if(!etpH.getText().toString().trim().matches(""))
                 pH=Double.parseDouble(etpH.getText().toString().trim());
                String TDS=etTDS.getText().toString().trim();

                if(etQuantity.getText().toString().matches("")
                        ||etpH.getText().toString().matches("")
                        ||etDO.getText().toString().matches("")
                        ||etTDS.getText().toString().matches("")
                        ||etTypesOfUses.getText().toString().matches(""))
                {
                    txShowResult.setText("*fill all data");
                    txShowResult.setVisibility(View.VISIBLE);
                    txShowResult.setTextColor(getResources().getColor(R.color.RED));
                }

                else if(pH<0||pH>14)
                {
                    txShowResult.setText("*pH is Not Valid");
                    txShowResult.setVisibility(View.VISIBLE);
                    txShowResult.setTextColor(getResources().getColor(R.color.RED));
                }

                else if(DO<0||DO>100)
                {
                    txShowResult.setText("*Dissolve Oxygen Should be in range 0%-100%");
                    txShowResult.setVisibility(View.VISIBLE);
                    txShowResult.setTextColor(getResources().getColor(R.color.RED));
                }
                    else {
                    String result = "Want a update with\n Block ID:" + sdUserID
                            + "\t River:-" + sdRiverName.trim()
                            + "\n Quantity=" + etQuantity.getText().toString()
                            + "\n Dissolved Oxygen=" + etDO.getText().toString()
                            + "\n pH=" + etpH.getText().toString()
                            + "\n TDS=" + etTDS.getText().toString()
                            + "\n Types of Uses:- " + etTypesOfUses.getText().toString();
                    etQuantity.setVisibility(View.GONE);
                    etTDS.setVisibility(View.GONE);
                    etDO.setVisibility(View.GONE);
                    etpH.setVisibility(View.GONE);
                    btSubmit.setVisibility(View.GONE);
                    etTypesOfUses.setVisibility(View.GONE);

                    drain1pH.setVisibility(View.GONE);
                    drain2pH.setVisibility(View.GONE);
                    drain3pH.setVisibility(View.GONE);
                    drain1tds.setVisibility(View.GONE);
                    drain2tds.setVisibility(View.GONE);
                    drain3tds.setVisibility(View.GONE);

                    txShowResult.setText(result);
                    txShowResult.setVisibility(View.VISIBLE);
                    btYes.setVisibility(View.VISIBLE);
                    btNo.setVisibility(View.VISIBLE);

                    show1.setVisibility(View.GONE);
                    show2.setVisibility(View.GONE);
                    show3.setVisibility(View.GONE);
                    show4.setVisibility(View.GONE);
                    show5.setVisibility(View.GONE);

                    sowdrain1pH.setVisibility(View.GONE);
                    sowdrain2pH.setVisibility(View.GONE);
                    sowdrain3pH.setVisibility(View.GONE);
                    sowdrain1tds.setVisibility(View.GONE);
                    sowdrain2tds.setVisibility(View.GONE);
                    sowdrain3tds.setVisibility(View.GONE);
                }

            }
        });

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendJson();
                NewData.this.finish();
            }
        });
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewData.this.finish();
            }
        });
    }

    private void SendJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userID", sdUserID);
            jsonObject.put("river_name", sdRiverName.toLowerCase().trim());
            jsonObject.put("quantity", etQuantity.getText().toString());
            jsonObject.put("pH", etpH.getText().toString());
            jsonObject.put("tds", etTDS.getText().toString());
            jsonObject.put("dissolve_oxygen", etDO.getText().toString());
            jsonObject.put("type_of_uses", etTypesOfUses.getText().toString().toLowerCase().trim());

            //for drain1
            JSONObject d1=new JSONObject();
            d1.put("pH", drain1pH.getText().toString());
            d1.put("quantity", drain1tds.getText().toString());

            jsonObject.put("d1", d1);

            //for drain2
            JSONObject d2=new JSONObject();
            d2.put("pH", drain2pH.getText().toString());
            d2.put("quantity", drain2tds.getText().toString());

            jsonObject.put("d2", d2);

            //for drain3
            JSONObject d3=new JSONObject();
            d3.put("pH", drain3pH.getText().toString());
            d3.put("quantity", drain3tds.getText().toString());

            jsonObject.put("d3", d3);

        }
        catch (Exception e)
        {
            Log.e("NewData ", "creating JSON OBJECT");
        }
        Log.e("rivers", jsonObject.toString());

        RequestQueue MyRequestQueue = Volley.newRequestQueue(NewData.this);
        String url = Constants.BASE_URL+"update/";
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //TODO response Listener
                        Log.e("Update Respose", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance(NewData.this).addToRequestQueue(jsonObjectRequest);
    }
}
