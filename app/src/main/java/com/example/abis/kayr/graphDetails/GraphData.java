package com.example.abis.kayr.graphDetails;

/**
 * Created by abis.
 */

public class GraphData {

    private int xAxes;
    private float pH_Data;
    private float tds_Data;
    private float do_Data;
    private float quantity;

    public GraphData(int xAxes, float pH_Data, float tds_Data, float do_Data, float quantity) {
        this.xAxes = xAxes;
        this.pH_Data = pH_Data;
        this.do_Data=do_Data;
        this.tds_Data=tds_Data;
        this.quantity=quantity;
    }

    public int getxAxes() {
        return xAxes;
    }

    public float getpH_Data() {
        return pH_Data;
    }

    public float getTds_Data() {
        return tds_Data;
    }

    public float getDo_Data() {
        return do_Data;
    }

    public float getQuantity() {
        return quantity;
    }
}
