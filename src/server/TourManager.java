/*
* TourManager.java
* Holds the cities of a tour
*/

package server;

import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationPlaces = new ArrayList<Place>();

    // Adds a destination city
    public static void addPlace(Place place) {
        destinationPlaces.add(place);
    }
    
    // Get a city
    public static Place getPlace(int index){
        return (Place)destinationPlaces.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfPlaces(){
        return destinationPlaces.size();
    }
    
}