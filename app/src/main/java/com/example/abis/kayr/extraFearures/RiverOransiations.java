package com.example.abis.kayr.extraFearures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.abis.kayr.R;
import com.example.abis.kayr.VeryFirst;

public class RiverOransiations extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_oransiations);
        textView=(TextView)findViewById(R.id.wrisExtend  );

        textView.setText(VeryFirst.extendWaris);
    }
}
