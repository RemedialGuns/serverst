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

public class Mensaje_data implements Serializable{
	   
    private static final long serialVersionUID = 9178463713495654837L;

     public String type;
    private String name;
    private double distance;
    private double rate;
    private double cost;
        
}
