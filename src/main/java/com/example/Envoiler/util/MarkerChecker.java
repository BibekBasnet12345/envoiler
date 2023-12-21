package com.example.Envoiler.util;


import org.springframework.boot.configurationprocessor.json.JSONException;

public class MarkerChecker {

    public String isMarkerChecker(String[] markerList) throws JSONException {

        for (String marker : markerList) {

            if (marker.equalsIgnoreCase("Avg") || marker.equalsIgnoreCase("Average")) {
                return "avg";
            } else if (marker.equalsIgnoreCase("Max") || marker.equalsIgnoreCase("Maximum")) {
                return "max";
            } else if (marker.equalsIgnoreCase("Min") || marker.equalsIgnoreCase("Minimum")) {
                return "min";
            } else if (marker.equalsIgnoreCase("Light") || marker.equalsIgnoreCase("Heating") || marker.equalsIgnoreCase("Temperature") ||
                    marker.equalsIgnoreCase("Energy") || marker.equalsIgnoreCase("Cooling")
            ) {
                return marker.toLowerCase();
            }
        }

        return null;
    }
}
