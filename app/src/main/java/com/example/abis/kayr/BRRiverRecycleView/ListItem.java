package com.example.abis.kayr.BRRiverRecycleView;

/**
 * Created by abis.
 */

public class ListItem {
    private String RiverName, LastUpdate;

    public ListItem(String riverName, String lastUpdate) {
        RiverName = riverName;
        this.LastUpdate=lastUpdate;
    }

    public String getRiverName() {
        return RiverName.toUpperCase();
    }

    public String getLastUpdate() {
        return LastUpdate.toUpperCase();
    }
}
