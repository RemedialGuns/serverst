/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author RemedialGuns
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static server.SimulatedAnnealing.acceptanceProbability;


public class Server {
    
    Location userLocation = new Location();
    int radius;
    String type;
    List<String> response = new ArrayList<>();
    List<String> extractedData = new ArrayList<>();
    List<Place> places= new ArrayList<>();

  
    
    public Server(Location location, int radius, String type){
        userLocation=location;
        this.radius=radius;
        this.type=type;
    }
    
    

    private final String APIKey ="AIzaSyAXsha1xAP2m1rZ_9ix-M0NTbNR9ocg1kE";
    
    
    public String makePlacesUrl( ){//Location location, int radius, String type
        String link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+userLocation.getLat()+","+userLocation.getLng()+"&radius="+radius+"&types="+type+"&key="+APIKey;
        return link;
    }
    
    public void getData(String link){
        try{
            URL url=new URL(link);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                response.add(strTemp);
                //System.out.println(strTemp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            }
//        for (int i = 0; i < response.size(); i++) {
//            System.out.println(response.get(i));
//        }
    }
    
    public void extracData(){
        
        String aux;
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).startsWith("               \"lat\" : ")) {//extract lat     //code 1
                aux=response.get(i).substring(23, response.get(i).length()-1);
                System.out.println("lat "+aux);
                extractedData.add("1"+aux);
                //lat=(double) response.get(i);
            }
            else if (response.get(i).startsWith("               \"lng\" : ")) {//extract lng    //code 2
                aux=response.get(i).substring(23, response.get(i).length()-1);
                System.out.println("lng "+aux);
                extractedData.add("2"+aux);
            }
            
            
            else if (response.get(i).startsWith("         \"name\" : ")) {//extact name     //code 3
                aux=response.get(i).substring(19, response.get(i).length()-2);
                System.out.println("name "+aux);
                extractedData.add("3"+aux);
            }
            
            else if (response.get(i).startsWith("               \"photo_reference\" : ")) {//extract photo reference
                aux=response.get(i).substring(36, response.get(i).length()-2);              //code 4
                System.out.println("photo "+aux);
                extractedData.add("4"+aux);
            }
            
            else if (response.get(i).startsWith("         \"place_id\" : ")) {//extact place id     //code 5
                aux=response.get(i).substring(23, response.get(i).length()-2);
                System.out.println("place_id "+aux);
                extractedData.add("5"+aux);
            }
            else if (response.get(i).startsWith("         \"rating\" : ")) {
                aux=response.get(i).substring(20, response.get(i).length()-1);
                System.out.println("rating "+aux);
                extractedData.add("6"+aux);
            }
        }
    }
    
    public void createPlaces(){
        String id="";
        String name="";
        String imageReference="";
        double rating=0;
        double lat=0;
        double lng=0;
        int code=0;
        boolean flag=false;
        
        
        for (int i = 0; i < extractedData.size(); i++) {
            String pls=extractedData.get(i).toString();
            pls=pls.substring(0,1);
            code=Integer.parseInt(pls);
            switch(code){
                
                case 1:
                    if (flag) {
                        save(lat, lng, id, name, rating, imageReference);
                        //System.out.println("PLACES======="+places);
                    }
                    lat=Double.parseDouble(extractedData.get(i).substring(1));
                    //System.out.println("lat case 1 "+lat);
                    
                  break;
                    
                case 2:
                    lng=Double.parseDouble(extractedData.get(i).substring(1));
                    //System.out.println("lng case 2 "+lng);
                    break;
                    
                case 3:
                    name=extractedData.get(i).substring(1);
                    //System.out.println("name case 3 "+name);
                    break;
                    
                case 4:
                    imageReference=extractedData.get(i).substring(1);
                    //System.out.println("image case 4 "+imageReference);
                    break;
                    
                case 5:
                    id=extractedData.get(i).substring(1);
                    flag=true;
                    //System.out.println("id case 5 "+id);
                    break;
                
                case 6:
                    rating=Double.parseDouble(extractedData.get(i).substring(1));
                    //System.out.println("rating case 6 "+rating);
                    break;
            }
        }
        //save(lat, lng, id, name, rating);
        //System.out.println("places... "+places);
    }
    
    public void save(double lat, double lng, String id, String name, double rating, String imageReference){
        System.out.println("saving");
        Location saveLocation = new Location(lat, lng);
            Place adding=new Place(id, name, rating, saveLocation, imageReference);
            adding.setUserLocation(userLocation);
            places.add(adding);
    }
      
    private static Boolean flag = new Boolean(false) ;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //This array saves the places wich aren't in tne moment in the tour maanager 
        Server server=new Server(new Location(4.667149,-74.055111), 500, "food+culture+amusement");
        server.getData(server.makePlacesUrl());
        //System.out.println(server.response);
        server.extracData();
        server.createPlaces();
        Collections.sort(server.places);
        
        for(int j = 0;j<12;j++ ){
            TourManager.addPlace(server.places.get(j));
        }
        
        
        
        for(int k =0;k<server.places.size();k++){
            flag = false;
            for(int l =0;l<12;l++)
            if(server.places.get(k) == TourManager.getPlace(l)){
                flag  = true;
            }
            
            if(!flag)
                TourManager.addOtherPlace(server.places.get(k));
        }
        
        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.0003;

        // Initialize intial solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        System.out.println("-------------------------Lugares en la solución  inicial----------------------------");
        for (int i = 0; i < TourManager.numberOfPlaces(); i++) {
            System.out.println("place #"+i+" "+TourManager.getPlace(i));
            
        }
        System.out.println("Initial solution fitness: " + currentSolution.getDistance());
        /* System.out.println("------------Lugares que no estan en la solución inicial---------------------------------");
        for (int o = 0; o < TourManager.numberOfOtherPlaces(); o++) {
            System.out.println("place #"+o+" "+ TourManager.getOtherPlace(o));
        
        }*/
        // Set as current best
        Tour best = new Tour(currentSolution.getTour());
        //The auxiliar to find the minumun change
        double minAux = -1000000;
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getTour());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
             int tourPos2 = 0;
            //We need to find the city to change with in the other_cities array 
            for(int i = 0; i < TourManager.numberOfOtherPlaces();i++){
                if( (TourManager.getOtherPlace(i).distanceToUser() - TourManager.getPlace(tourPos1).distanceToUser())
                        <minAux){
                    minAux = (TourManager.getOtherPlace(i).distanceToUser() - TourManager.getPlace(tourPos1).distanceToUser());
                    tourPos2 = i;
                }
            }
           

            // Now we must change the tourPosition in the new solution with the place tha will enter 
            
            //Place to change
            Place placeSwap1 = newSolution.getPlace(tourPos1);
            //Place from the other places to enter
            Place placeSwap2 = TourManager.getOtherPlace(tourPos2);//newSolution.ge(tourPos2);

            // Swap them
            newSolution.setPlace(tourPos1, placeSwap2);
            TourManager.setOtherPlace(tourPos2,placeSwap1);
            // Get energy of solutions
            double currentEnergy = currentSolution.getDistance();
            double neighbourEnergy = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }
            //If the solution isn´t accepted we need to change again in the other_places because
            //it wont enter
            else{
                TourManager.setOtherPlace(tourPos2,placeSwap2);
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getTour());
            }
     /*          System.out.println("-------------------------solución acutal----------------------------");
        for (int i = 0; i < currentSolution.tourSize(); i++) {
            System.out.println("place #"+i+" "+ currentSolution.getPlace(i));
            
        }
            System.out.println("----------------------------------------------------------------------");
        for (int i = 0; i < TourManager.numberOfOtherPlaces(); i++) {
            System.out.println("place #"+i+" "+ TourManager.getOtherPlace(i));
            
        }*/
            // Cool system
            temp *= 1-coolingRate;
        }
        
        System.out.println("-------------------------Lugares en la solución  final----------------------------");
        for (int n = 0; n < best.tourSize(); n++) {
            System.out.println("place #"+n+" "+ best.getPlace(n));
            
        }
        System.out.println("Final solution fitness: " + best.getDistance());
        /* System.out.println("-------------lugares que no entran en la solucion final-----------------------------");
        /*for (int m = 0; m < TourManager.numberOfOtherPlaces(); m++) {
            System.out.println("place #"+m+" "+ TourManager.getOtherPlace(m));
        
        System.out.println("Tour: " + best);
        }*/
        
    }
}