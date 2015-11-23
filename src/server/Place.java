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
public class Place {
    public String id;
    public String name;
    public String imageReference;
    public double rating;
    public Location location;


    Place(String id, String name, double rating, Location location, String imageReference){
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
    
    @Override
    public String toString(){
        return "[ id: "+id+", name: "+name+", rating: "+rating+", imageReference: "+imageReference+" ]";
    }
}
