package com.example.abis.kayr.loginRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.abis.kayr.BRActivities.AddRiver;
import com.example.abis.kayr.BRRiverRecycleView.BRAdapterClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.abis.kayr.loginRegister.fragments.ChangePasswordDialog;
import com.example.abis.kayr.loginRegister.model.Response;
import com.example.abis.kayr.loginRegister.model.User;
import com.example.abis.kayr.loginRegister.network.NetworkUtil;
import com.example.abis.kayr.loginRegister.utils.Constants;
import com.example.abis.kayr.CustomJsonRequest.*;
import com.example.abis.kayr.R;
import com.example.abis.kayr.BRRiverRecycleView.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfileActivity extends AppCompatActivity implements ChangePasswordDialog.Listener {

    public static final String TAG = ProfileActivity.class.getSimpleName();

    public static List<String> Rivers;

    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvDate;
    private Button mBtChangePassword;
    private Button mBtLogout;
    private Button mBtaddNewRiver;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    int i=0;

    private ProgressBar mProgressbar;
    private ProgressBar tmPblist;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    public static String mUserID;
    List<String> RiversList;
    List<ListItem> listItems;

    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSubscriptions = new CompositeSubscription();
        initViews();
        tmPblist.setVisibility(View.VISIBLE);
        initSharedPreferences();
        loadProfile();

        //River's data
//        tmPblist.setVisibility(View.VISIBLE);
//        ReadDB();
        AddingToList();
//        tmPblist.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {

        for( i=0; i<10000000; ++i) {
            if(i==999999) {
                super.onResume();
            }
        }

        Log.e(TAG, "onResume: IN resume="+i);


        tmPblist.setVisibility(View.VISIBLE);;

        listItems.clear();
        adapter.notifyDataSetChanged();
        ReadDB();


    }

    @Override
    protected void onPause() {


            Log.e("After is added", "On Pause="+i);

        super.onPause();
    }

    private void ReadDB()
    {

        tmPblist.setVisibility(View.VISIBLE);
        Log.e("Reading", "Data="+i);

        JSONObject SendJson=new JSONObject();
        try
        {
            SendJson.put("userID",mUserID);
        }catch (Exception e)
        {
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =Constants.BASE_URL+"river/";
        //Json
        // Request a string response from the provided URL.
        CustomRequest stringRequest = new CustomRequest(Request.Method.DEPRECATED_GET_OR_POST, url,SendJson,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray root = response;
                            Log.e("GettingRivers", response.toString());
                            for(int i=0; i<root.length(); ++i)
                            {
                                JSONObject item=root.getJSONObject(i);
                                String RiverName, LastUpdate;
                                if(!item.isNull("river_name"))
                                {
                                    Rivers.add(item.getString("river_name").trim().toLowerCase());

                                    RiverName=item.getString("river_name").toUpperCase();
                                    LastUpdate=item.getString("updated_at");
                                    String tds=item.getString("tds");
                                    String pH=item.getString("pH");
                                    String dois=item.getString("dissolve_oxygen");
                                    String quantity=item.getString("quantity");
                                    ListItem listItem=new ListItem(RiverName,
                                                        LastUpdate
                                                        +"\nTDS:-"+tds+" ppm"
                                                        +"\nDissolve Oxygen:-"+dois+" %"
                                                        +"\npH:-"+pH
                                                        +"\nQuantity:-"+quantity+" m^3");
                                    listItems.add(listItem);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            tmPblist.setVisibility(View.GONE);
                        }catch (JSONException e) {
                            // If an error is thrown when executing any of the above statements in the "try" block,
                            // catch the exception here, so the app doesn't crash. Print a log message
                            // with the message from the exception.
                            Log.e("UpdateRiver", "Problem parsing the  JSON results", e);
                        }
                    }
                },
            new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ProfileActivity", "Response parsing problem");
        }
    });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


    private void initViews() {

        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvEmail = (TextView) findViewById(R.id.tv_email);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mBtChangePassword = (Button) findViewById(R.id.btn_change_password);
        mBtLogout = (Button) findViewById(R.id.btn_logout);
        mBtaddNewRiver = (Button) findViewById(R.id.bt_AddNewRiver) ;
        mProgressbar = (ProgressBar) findViewById(R.id.progresslist);
        tmPblist=(ProgressBar)findViewById(R.id.progress);

        mBtChangePassword.setOnClickListener(view -> showDialog());
        mBtLogout.setOnClickListener(view -> logout());

        recyclerView=(RecyclerView)findViewById(R.id.RecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems=new ArrayList<>();

        RiversList=new ArrayList<>();

        Rivers=new ArrayList<>();
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mUserID = mSharedPreferences.getString(Constants.EMAIL,"");
        //for current use
        //TODO comment this line
       // mEmail="ankit6480@gmail.com";

        //calling activity to add new rivers

        mBtaddNewRiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(ProfileActivity.this, AddRiver.class);
                i.putExtra("UserID", mUserID);
                ProfileActivity.this.startActivity(i);
            }
        });

    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        finish();
    }

    private void showDialog(){

        ChangePasswordDialog fragment = new ChangePasswordDialog();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mUserID);
        bundle.putString(Constants.TOKEN,mToken);
        fragment.setArguments(bundle);

        fragment.show(getFragmentManager(), ChangePasswordDialog.TAG);
    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mUserID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(User user) {

        mProgressbar.setVisibility(View.GONE);
        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());
        mTvDate.setText(user.getCreated_at());
    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();

    }
    private void AddingToList()
    {
        adapter=new BRAdapterClass(listItems, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("Password Changed Successfully !");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Please logout", Toast.LENGTH_SHORT).show();
    }
}
