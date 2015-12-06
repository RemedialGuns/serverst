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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.SimulatedAnnealing.acceptanceProbability;


public class Server {
    
    Location userLocation = new Location();
    int radius;
    String type="";
    List<String> response = new ArrayList<>();
    List<String> extractedData = new ArrayList<>();
    List<Place_x> places= new ArrayList<>();
    String[] placesType = {"random","museum","amusement_park","night_club","night_club","bar",
        "movie_theater","shopping_mall","art_gallery","bowling_alley","food","amusement_park","book_store","church","park","park","museum"};
    int checkBoxAnswers[];
    String gender;
    int age;
    String country;
    
    Socket skCliente;
	ServerSocket skServidor;
    
        Socket skCliente1;
	ServerSocket skServidor1;
        ObjectOutputStream oos;
	String datareceived, substring1, substring2;
	final int PUERTO = 5555;// Puerto que utilizara el servidor utilizar este
							// mismo en el cliente
	String IP_client;
	Mensaje_data mdata;
	String TimeStamp;
        String TimeStamp1;
  
    
    @SuppressWarnings({"empty-statement", "empty-statement"})
    public Server(Location location, int radius){
        userLocation=location;
        this.radius=radius;
        
        
        try {
			System.out.println("************ SERVER ****************");
			// creamos server socket
			skServidor = new ServerSocket(PUERTO);
			System.out.println("Escuchando el puerto " + PUERTO);
			System.out.println("En Espera....");

			TimeStamp = new java.util.Date().toString();

			try {
				// Creamos socket para manejar conexion con cliente
				skCliente = skServidor.accept(); // esperamos al cliente
				// una vez q se conecto obtenemos la ip
				IP_client = skCliente.getInetAddress().toString();
				System.out.println("[" + TimeStamp + "] Conectado al cliente "
						+ "IP:" + IP_client);

			
					// Manejamos flujo de Entrada
					ObjectInputStream ois = new ObjectInputStream(
							skCliente.getInputStream());
                                       
                                        //Cremos objetos con lo recibido del cliente
					Object cba = ois.readObject();// leemos las checkbox answers
                                       Object gender = ois.readObject();//leemos gender
                                       Object age = ois.readObject();
                                       Object country = ois.readObject();
                                      skCliente.close();
                                      skServidor.close();
                                       if(cba instanceof int[]){
                                        
                                            this.checkBoxAnswers = (int[])cba;
                                            for(int i =0;i<17;i++){
                                                
                                                if(this.checkBoxAnswers[i] == 1)
                                                    this.type += this.placesType[i]+"|";
                   
                                                
                                            }
                                        this.type = this.type.substring(0, this.type.length()-1);
                                            
                                      
                                         System.out.println(this.type);
                                            
                                       }
                                       if(gender instanceof String){
                                           this.gender = (String)gender; 
                                           System.out.println(this.gender);
                                       }
                                       this.age = (int)age; 
                                       System.out.println(this.age);
                                       
                                       if(country instanceof String){
                                       this.country  = (String)country;
                                       System.out.println((String)country);
                                       }
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[" + TimeStamp + "] Error ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + TimeStamp + "] Error ");
		}
        
       
     
    }
    
    

    private final String APIKey ="AIzaSyAXsha1xAP2m1rZ_9ix-M0NTbNR9ocg1kE";
    
    
    public String makePlacesUrl( ){//Location location, int radius, String type
        String link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+userLocation.getLat()+","+userLocation.getLng()+"&radius="+radius+"&types="+this.type+"&key="+APIKey;
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
            Place_x adding=new Place_x(id, name, rating, saveLocation, imageReference);
            adding.setUserLocation(userLocation);
            places.add(adding);
    }
    
    public Tour TempladoSimulado() throws IOException{
        
        
        Tour returnValue;
        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.0003;

        // Initialize intial solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        if(TourManager.numberOfPlaces()<=12){
                System.out.println("-------------------------Lugares en la solución  final----------------------------");
            for (int i = 0; i < TourManager.numberOfPlaces(); i++) {
                System.out.println("place #"+i+" "+TourManager.getPlace(i));

            }
                returnValue = currentSolution;
                
        
        }
        else{
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
                Place_x placeSwap1 = newSolution.getPlace(tourPos1);
                //Place from the other places to enter
                Place_x placeSwap2 = TourManager.getOtherPlace(tourPos2);//newSolution.ge(tourPos2);

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

                // Cool system
                temp *= 1-coolingRate;
            }

            System.out.println("-------------------------Lugares en la solución  final----------------------------");
            for (int n = 0; n < best.tourSize(); n++) {
                System.out.println("place #"+n+" "+ best.getPlace(n));

            }


            System.out.println("Final solution fitness: " + best.getDistance());

            returnValue = best;
        }
        try {
			System.out.println("************ SERVER ****************");
			// creamos server socket
			skServidor1 = new ServerSocket(PUERTO);
			System.out.println("Escuchando el puerto " + PUERTO);
			System.out.println("En Espera....");

			TimeStamp1 = new java.util.Date().toString();

			try {
				// Creamos socket para manejar conexion con cliente
				skCliente1 = skServidor1.accept(); // esperamos al cliente
				// una vez q se conecto obtenemos la ip
				IP_client = skCliente1.getInetAddress().toString();
				System.out.println("[" + TimeStamp + "] Conectado al cliente "
						+ "IP:" + IP_client);
                                        // Manejamos flujo de salida
					oos = new ObjectOutputStream(
							skCliente1.getOutputStream());
                                       // oos.writeObject(returnValue.tourSize());
                                       for(int h =0;h<returnValue.tourSize();h++){
                                        
                                        oos.writeObject(new Object[]{returnValue.getPlace(h).getName()
                                                , returnValue.getPlace(h).distanceToUser(), returnValue.getPlace(h).rating});
                                      
                                       System.out.println(returnValue.getPlace(h).getName());
                                       }
                        } catch (Exception e) {
				e.printStackTrace();
				System.out.println("[" + TimeStamp + "] Error ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + TimeStamp + "] Error ");
		} 
    
        return returnValue;
    }
      
    private static Boolean flag = new Boolean(false) ;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //This array saves the places wich aren't in tne moment in the tour maanager 
        
            Server server=new Server(new Location(4.667149,-74.055111), 5000);
            server.getData(server.makePlacesUrl());
            //System.out.println(server.response);
            server.extracData();
            server.createPlaces();
            Collections.sort(server.places);
            /*for(Place_x x:server.places){
                System.out.println(x);
            }*/
            if(server.places.size()>=12)
                for(int j = 0;j<12;j++ ){
                    TourManager.addPlace(server.places.get(j));
                }
            else
                for(int j = 0;j<server.places.size();j++ ){
                    TourManager.addPlace(server.places.get(j));
                }


            for(int k =0;k<server.places.size();k++){
                flag = false;
                if(server.places.size()>=12)
                for(int l =0;l<12;l++)
                if(server.places.get(k) == TourManager.getPlace(l)){
                    flag  = true;
                }
                if(server.places.size()>=12)
                if(!flag)
                    TourManager.addOtherPlace(server.places.get(k));
            }

           
        try {
            server.TempladoSimulado();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
					
       
        
        
    }
}