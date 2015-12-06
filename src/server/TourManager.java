/*
* TourManager.java
* Holds the cities of a tour
*/

package server;

import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationPlaces = new ArrayList<Place_x>();
    public static ArrayList other_places = new ArrayList<Place_x>(); 
    // Adds a destination city
    public static void addPlace(Place_x place) {
        destinationPlaces.add(place);
    }
    public static void addOtherPlace(Place_x place) {
        other_places.add(place);
    }
    // Get a city
    public static Place_x getPlace(int index){
        return (Place_x)destinationPlaces.get(index);
    }
    
     public static Place_x getOtherPlace(int index){
        return (Place_x)other_places.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfPlaces(){
        return destinationPlaces.size();
    }
    // Get the number of destination cities
    public static int numberOfOtherPlaces(){
        return other_places.size();
    }

    static void setOtherPlace(int tourPos2, Place_x placeSwap1) {
        other_places.set(tourPos2, placeSwap1);
    }
    
}