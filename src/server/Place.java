/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author santiago
 */
import java.io.Serializable;

public class Place implements Serializable{
    private static final long serialVersionUID = 9178463713495654837L;
    public String type;
    private String name;
    private double distance;
    private double rate;
    private double cost;


    public  Place(String type, String name, double distance, double rate, double cost){
        
        this.type=type;
        this.name=name;
        this.distance=distance;
        this.rate=rate;
        this.cost=cost;
    }

    public String getName(){
        return name;
    }

    public double getDistance(){
        return distance;
    }

    public double getRate(){
        return rate;
    }

    public double getCost(){
        return cost;
    }

    public String getType(){
        return type;
    }

}
