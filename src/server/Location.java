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
public class Location {
    public double lat;
    public double lng;
    
    public Location(double lat, double lng){
        this.lat=lat;
        this.lng=lng;
    }
    
    public Location(){
        this.lat=0;
        this.lng=0;
    }
    
    public double getLat(){
        return lat;
    }
    
    public double getLng(){
        return lng;
    }
    
    @Override
    public String toString(){
        return "[ "+"lat: "+lat+", lng: "+lng+ "]";
    }
}
