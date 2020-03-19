package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Locations implements Serializable {

    private final int locationId;
    private final String description;
    Map<String, Integer> exits;

    private static long serialVersionUID = 1L;

    public Locations(int locationId, String description, Map<String, Integer> exits) {
        this.locationId = locationId;
        this.description = description;
        if(exits != null){
            this.exits   = new LinkedHashMap<>(exits);
        }else{
            this.exits = new LinkedHashMap<>();
        }

        this.exits.put("Q", 0);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    protected void addExits(String direction, int location){
        exits.put(direction, location);
    }
}
