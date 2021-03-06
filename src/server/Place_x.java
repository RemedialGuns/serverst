/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package server;


/**
 *
 * @author Miguel
 */
public class Place_x  implements Comparable<Place_x> {
    //fitness de ahora
    static double alpha = -10;
    static double gamma = 1000;
    public String id;
    public String name;
    public String imageReference;
    public double rating;
    public Location location;
    public Location userLocation;


    Place_x(String id, String name, double rating, Location location, String imageReference){
    	this.id=id;
    	this.name=name;
    	this.rating=rating;
    	this.location=location;
        this.imageReference=imageReference;
    }

    public String getId(){
    	return id;
    }

    public String getName(){
    	return name;
    }

    public double getRating(){
    	return rating;
    }

    public Location getLocation(){
    	return location;
    }
    
    public String getImageReference(){
        return imageReference;
    }
    public void setUserLocation(Location location){
        this.userLocation = location;
    }
    
    // Gets the distance to given user
    public double distanceToUser(){
        double xDistance = Math.abs(location.getLat()  - userLocation.getLat() );
        double yDistance = Math.abs(location.getLng() - userLocation.getLng());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    
    // Get the fitness function of the place
    
    public  double fitnessFunc(){
		
        return ((alpha*rating)+(gamma*distanceToUser()));
    }
    @Override
    public String toString(){
        return "[ name: "+name+", rating: "+rating+ ",distance: "+distanceToUser()+"]";
    }

    @Override
    public int compareTo(Place_x that) {
        double thatDistance = that.distanceToUser();

	    if (this.distanceToUser() > thatDistance)
	        return 1;
	    else if (this.distanceToUser() < thatDistance)
	            return -1;
	    else
	        return 0;
    }
}
