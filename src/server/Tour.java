/*
* Tour.java
* Stores a candidate tour through all cities
*/

package server;

import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList tour = new ArrayList<Place>();
    // Cache
    private int distance = 0;
    
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < TourManager.numberOfPlaces(); i++) {
            tour.add(null);
        }
    }
    
    // Constructs a tour from another tour
    public Tour(ArrayList tour){
        this.tour = (ArrayList) tour.clone();
    }
    
    // Returns tour information
    public ArrayList getTour(){
        return tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination places and add them to our tour
        for (int placeIndex = 0; placeIndex < TourManager.numberOfPlaces(); placeIndex++) {
          setPlace(placeIndex, TourManager.getPlace(placeIndex));
        }
       
    }

    // Gets a place from the tour
    public Place getPlace(int tourPosition) {
        return (Place)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setPlace(int tourPosition, Place place) {
        tour.set(tourPosition, place);
        // If the tours been altered we need to reset the fitness and distance
        distance = 0;
    }
    
    // Gets the total distance of the tour
    public double getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int placeIndex=0; placeIndex < tourSize(); placeIndex++) {
                // Get city we're traveling from
                tourDistance += getPlace(placeIndex).fitnessFunc();
            
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getPlace(i)+"|";
        }
        return geneString;
    }
}
