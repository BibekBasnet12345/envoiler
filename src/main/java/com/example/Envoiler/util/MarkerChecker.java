package com.example.Envoiler.util;


import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class MarkerChecker {

    public void isMarkerChecker(String[] markerList, JSONObject rawData) throws JSONException {

        for (String marker : markerList) {
            if(marker.equalsIgnoreCase("Avg")||marker.equalsIgnoreCase("Average")){
                rawData.put("avg",":m");
            }
                if (marker.equalsIgnoreCase("Light") || marker.equalsIgnoreCase("Heating") || marker.equalsIgnoreCase("Temperature") ||
                        marker.equalsIgnoreCase("Energy") || marker.equalsIgnoreCase("Cooling") ||
                        marker.equalsIgnoreCase("Max")||marker.equalsIgnoreCase("Min")||marker.equalsIgnoreCase("Avg")) {
                    rawData.put(marker.toLowerCase(), ":m");
                }
        }

    }
}
