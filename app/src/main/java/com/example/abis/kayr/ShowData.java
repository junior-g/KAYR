package com.example.abis.kayr;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.graphDetails.GraphData;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowData extends AppCompatActivity {

    private TextView txData, txRiver, txPollution;
    private CardView dataCard;
    private ProgressBar lodingData;
    private String RiverName;

    private LineChart graphpH;
    private LineChart graphtds;
    private LineChart graphdo;
    private LineChart graphquantity;


    private List<Entry> pH_data;
    private List<Entry> tds_data;
    private List<Entry> do_data;
    private List<Entry> quantity_data;
    private  LineDataSet dataSet_pH;
    private  LineDataSet dataSet_tds;
    private  LineDataSet dataSet_do;
    private  LineDataSet dataSet_quantity;
    private Double current_pH, current_tds, current_do, current_quantity;
    private String approxDate, current_update, uses, pollution_level, result1;

    List<String> xLable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        RiverName=getIntent().getStringExtra("River_Name");

        initviews();


        lodingData.setVisibility(View.VISIBLE);
        ReadDB();
    }
    private void textViewAssignment()
    {
        txRiver.setText(RiverName);
        txData.setText("Last Update:- "+current_update
                         +"\nPH:- "+current_pH+"\n"+"TDS:- "+current_tds+" ppm"
                        +"\nQUANTITY:- "+current_quantity+" m^3"
                        +"\nDISSOLVE OXYGEN:- "+current_do+" %"
                        +"\nUSES OF THIS RIVER:- "+uses
                        +"\n \nRiver Expected to be Polluted by:- "+approxDate
                        +"\n \n River dry out by:- "+result1);
        txPollution.setText("Pollution Due to Drains:- "+ pollution_level+"%");
    }

    private void initviews()
    {
        txData=(TextView)findViewById(R.id.tx_uData);
        txRiver=(TextView)findViewById(R.id.tx_uRiverName);
        txPollution=(TextView)findViewById(R.id.tx_udrainPollution);

        dataCard=(CardView)findViewById(R.id.Crd_RiverDetails);
        lodingData=(ProgressBar)findViewById(R.id.loding_RiverDetails);

        graphpH=(LineChart)findViewById(R.id.graph_pH);
        graphdo=(LineChart)findViewById(R.id.graph_do);
        graphtds=(LineChart)findViewById(R.id.graph_tds);
        graphquantity=(LineChart)findViewById(R.id.graph_quantity);

        graphpH.setNoDataText("Please Tap to View Data");
        graphdo.setNoDataText("Please Tap to View Data");
        graphtds.setNoDataText("Please Tap to View Data");
        graphquantity.setNoDataText("Please Tap to View Data");

        pH_data=new ArrayList<>();
        tds_data=new ArrayList<>();
        do_data=new ArrayList<>();
        quantity_data=new ArrayList<>();
        xLable=new ArrayList<>();

        graphpH.getDescription().setText("pH Graph");
        graphtds.getDescription().setText("TDS Graph");
        graphdo.getDescription().setText("Dissolve Oxygen Graph");
        graphquantity.getDescription().setText("Quantity Graph");

        graphpH.getDescription().setTextSize(12.0f);
        graphtds.getDescription().setTextSize(12.0f);
        graphdo.getDescription().setTextSize(12.0f);
        graphquantity.getDescription().setTextSize(12.0f);

    }

    private void ReadDB(int k)
    {
        for(int i=0; i<5; ++i)
        {
            //set to graph
            pH_data.add(new Entry(i, 5));
        }
        for(int i=0; i<5; ++i)
        {
            tds_data.add(new Entry(i, 5));
        }
        for(int i=0; i<5; ++i)
        {
            do_data.add(new Entry(i, 5));
        }
        for(int i=0; i<5; ++i)
        {
            quantity_data.add(new Entry(i, 5));
        }

        for(int i=0; i<5; ++i)
        {
            xLable.add("a");
        }
    }

    private void ReadDB()
    {

        JSONObject SendJson=new JSONObject();
        try
        {
            SendJson.put("river_name",RiverName.toLowerCase());
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
                        //TODO: check index out of bound
                        try
                        {
                            try {

                                JSONArray doArray=new JSONArray();
                                JSONArray pHArray=new JSONArray();
                                JSONArray quantityArray=new JSONArray();
                                JSONArray tdsArray=new JSONArray();
                                JSONArray updateArray=new JSONArray();


                                 doArray=(JSONArray)response.get("dissolve_oxygen");
                             pHArray=(JSONArray)response.get("pH");
                                 quantityArray=(JSONArray)response.get("quantity");
                             tdsArray=(JSONArray)response.get("tds");
                             updateArray=(JSONArray)response.get("update");
                            Log.e("Show DATA", ""+pHArray.length()+" "+
                                                        tdsArray.length()+" "
                                                        +doArray.length()+" "
                                                        +quantityArray.length());
                            //TODO: read time format
                                Log.e("Show DATA", doArray.toString());

                            for(int i=0; i<pHArray.length(); ++i)
                            {
                                //set to graph
                                pH_data.add(new Entry(i, (float)pHArray.getDouble(i)));
                            }
                            for(int i=0; i<tdsArray.length(); ++i)
                            {
                                tds_data.add(new Entry(i, (float)tdsArray.getDouble(i)));
                            }
                            for(int i=0; i<doArray.length(); ++i)
                            {
                                do_data.add(new Entry(i, (float)doArray.getDouble(i)));
                            }
                            for(int i=0; i<quantityArray.length(); ++i)
                            {
                                quantity_data.add(new Entry(i, (float)quantityArray.getDouble(i)));
                            }


                            for(int i=0; i<updateArray.length(); ++i)
                            {
                                xLable.add(updateArray.getString(i));
                            }

                           // Current Condition

                                current_do = doArray.getDouble(doArray.length() - 1);
                                current_pH = pHArray.getDouble(pHArray.length() - 1);
                                current_tds = tdsArray.getDouble(tdsArray.length() - 1);
                                current_quantity = quantityArray.getDouble(quantityArray.length() - 1);
                                current_update = updateArray.getString(updateArray.length() - 1);
                                approxDate=response.getString("result");
                                uses=response.getString("type_of_uses");
                                pollution_level=response.getString("pollution");
                                result1=response.getString("result1");

                            }
                            catch (IndexOutOfBoundsException e)
                            {
                                Log.e("ShowData", "Getting Index OUT OF bound");
                            }
                            lodingData.setVisibility(View.GONE);
                            GraphDesign();
                            drawGraph();
                            textViewAssignment();
                        }
                        catch (JSONException e)
                        {
                            Snackbar.make(findViewById(R.id.activity_show_data), "Problem in Parsing!", Snackbar.LENGTH_LONG).show();
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

        lodingData.setVisibility(View.GONE);

    }


    private void GraphDesign()
    {
        dataSet_pH=new LineDataSet(pH_data, "pH value");
        dataSet_pH.setColor(getResources().getColor(R.color.RED));
        dataSet_pH.setValueTextColor(getResources().getColor(R.color.Ph_Text));

        dataSet_tds=new LineDataSet(tds_data, "TDS value");
        dataSet_tds.setColor(getResources().getColor(R.color.RED));
        dataSet_tds.setValueTextColor(getResources().getColor(R.color.Ph_Text));

        dataSet_do=new LineDataSet(do_data, "Dissolve oxygen value");
        dataSet_do.setColor(getResources().getColor(R.color.RED));
        dataSet_do.setValueTextColor(getResources().getColor(R.color.Ph_Text));

        dataSet_quantity=new LineDataSet(quantity_data, "Quantity Value");
        dataSet_quantity.setColor(getResources().getColor(R.color.RED));
        dataSet_quantity.setValueTextColor(getResources().getColor(R.color.Ph_Text));
    }
    private void drawGraph()
    {
        LineData lineData_pH=new LineData(dataSet_pH);
        graphpH.setData(lineData_pH);

        LineData lineData_tds=new LineData(dataSet_tds);
        graphtds.setData(lineData_tds);

        LineData lineData_do=new LineData(dataSet_do);
        graphdo.setData(lineData_do);

        LineData lineData_quantity=new LineData(dataSet_quantity);
        graphquantity.setData(lineData_quantity);


        //setting date;
        XAxis xAxis_pH=graphpH.getXAxis();

        xAxis_pH.setLabelRotationAngle(60.0f);

        xAxis_pH.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_pH.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLable.get((int) value);
            }
        });

        //setting date;
        XAxis xAxis_tds=graphtds.getXAxis();

        xAxis_tds.setLabelRotationAngle(60.0f);

        xAxis_tds.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_tds.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLable.get((int) value);
            }
        });

        //setting date;
        XAxis xAxis_do=graphdo.getXAxis();

        xAxis_do.setLabelRotationAngle(60.0f);

        xAxis_do.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_do.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLable.get((int) value);
            }
        });

        //setting date;
        XAxis xAxis_quantity=graphquantity.getXAxis();

        xAxis_quantity.setLabelRotationAngle(60.0f);

        xAxis_quantity.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_quantity.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLable.get((int) value);
            }
        });


      //  graphpH.invalidate(); //refresh
        Log.e("ShowData", "I am HERE-----------at ShowData Graph Ploting");
    }
}
