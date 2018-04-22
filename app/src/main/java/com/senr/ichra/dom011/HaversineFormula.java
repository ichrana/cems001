package com.senr.ichra.dom011;

import java.util.ArrayList;

/**
 * This is the implementation Haversine Distance Algorithm between two places
 * R = earth’s radius (mean radius = 6,371km)
 * Δlat = lat2− lat1
 * Δlong = long2− long1
 * a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
 * c = 2.atan2(√a, √(1−a))
 * d = R.c
 */


public class HaversineFormula {
// TWO distanceCalculate methods with different parameters and returns
    public static ArrayList<Place> distanceCalculate(ArrayList<Place> plc, double currentLat, double currentlng) {
        final int R = 6371; // Radious of the earth

        Double lat1 = currentLat;
        Double lon1 = currentlng;
        Double placeLat, placeLng;

        for (int i = 0; i < plc.size(); i++) {
            placeLat = plc.get(i).getLat();
            placeLng = plc.get(i).getLng();
            Double lat2 = placeLat;
            Double lon2 = placeLng;
            Double latDistance = toRad(lat2 - lat1);
            Double lonDistance = toRad(lon2 - lon1);
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double distance = R * c;
            plc.get(i).setDistance(distance);
            System.out.println("The distance between two lat and long is::" + distance);
        }
        return plc;
    }

    public static Double distanceCalculate(double placeLat, double placeLng, double currentLat, double currentlng) {

        final int R = 6371; // Radious of the earth

        Double lat1 = currentLat;
        Double lon1 = currentlng;

        Double lat2 = placeLat;
        Double lon2 = placeLng;
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c;
        System.out.println("The distance between two lat and long is::" + distance);

        return distance;
    }


    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
