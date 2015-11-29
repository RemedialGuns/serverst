/*
* TourManager.java
* Holds the cities of a tour
*/

package server;

import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationPlaces = new ArrayList<Place>();
    public static ArrayList other_places = new ArrayList<Place>(); 
    // Adds a destination city
    public static void addPlace(Place place) {
        destinationPlaces.add(place);
    }
    public static void addOtherPlace(Place place) {
        other_places.add(place);
    }
    // Get a city
    public static Place getPlace(int index){
        return (Place)destinationPlaces.get(index);
    }
    
     public static Place getOtherPlace(int index){
        return (Place)other_places.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfPlaces(){
        return destinationPlaces.size();
    }
    // Get the number of destination cities
    public static int numberOfOtherPlaces(){
        return other_places.size();
    }

    static void setOtherPlace(int tourPos2, Place placeSwap1) {
        other_places.set(tourPos2, placeSwap1);
    }
    
}