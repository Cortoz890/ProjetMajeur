package com.project.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
//import javax.print.DocFlavor.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.model.dto.FacilityDto;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.project.service.ProjectService;
import org.json.simple.JSONObject;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

// token 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
// team uuid : 7c1be29c-621b-4858-a972-ed0f4fe4a0d3

@RestController 
public class ProjectController {
	
	ProjectService project_service = new ProjectService();
	  
//  	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
//  		public String index(Model model) {  
//  		return "index";
// 
//  	}
  	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
		public String test(Model model) {  
  			return "abc";
	}

  	@RequestMapping(value = { "/facility" }, method = RequestMethod.GET)
		public FacilityDto[] getAllFacilities(Model model) {  
  			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<FacilityDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/facility", FacilityDto[].class);
			FacilityDto[] facilities = result.getBody();
			return facilities;
	}
  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.GET)
		public FacilityDto getFacilitybyId(@PathVariable String id) {  
  			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<FacilityDto> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/facility"+id, FacilityDto.class);
			FacilityDto facilities = result.getBody();
			return facilities;
	}
  	
  	@RequestMapping(value = { "/vehicle" }, method = RequestMethod.GET)
		public ArrayList<VehicleDto> getAllVehicles() { 
  			ArrayList<VehicleDto> vehicles = project_service.getOur_vehicle_list();
  			return vehicles;
	}
  	
  	@RequestMapping(value = { "/fire" }, method = RequestMethod.GET)
		public FireDto[] getAllFires() { 
  			FireDto[] fires = project_service.getFire_list();
  			return fires;
	}
  	
  	@RequestMapping(value = { "/vehicle/{id}" }, method = RequestMethod.GET)
		public VehicleDto getVehiclesById(@PathVariable String id) {  
  			RestTemplate restTemplate = new RestTemplate();
  			ResponseEntity<VehicleDto> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/vehicle/"+id, VehicleDto.class);
  			VehicleDto vehicles = result.getBody();
  			return vehicles;
  	}
	
  	@RequestMapping(value = { "/addVehicle" }, method = RequestMethod.POST)
		public JSONObject addVehicule(@RequestBody JSONObject my_json) throws IOException {  
	  		HttpPost post = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+"7c1be29c-621b-4858-a972-ed0f4fe4a0d3");
	        post.setEntity(new StringEntity(my_json.toString(),ContentType.APPLICATION_JSON));
	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post))
	        {
	            System.out.println(EntityUtils.toString(response.getEntity()));
	        }
	    	return my_json;
	}
  	
  	@RequestMapping(value = { "/deleteVehicle/{id}" }, method = RequestMethod.DELETE)
		public void delVehicle(@PathVariable String id) throws IOException {  
  			HttpDelete delete = new HttpDelete("http://vps.cpe-sn.fr:8081/vehicle/"+"7c1be29c-621b-4858-a972-ed0f4fe4a0d3/"+id);
  			//delete.setEntity(new StringEntity("",ContentType.APPLICATION_JSON));
  			CloseableHttpClient httpClient = HttpClients.createDefault();
  			CloseableHttpResponse response = httpClient.execute(delete);
	}
  	
  	
  	public double getOneFire(int fireId) throws IOException{ 
  		double fireIntensity = 0;
  		
  		try {
  				URL url = new URL("http://vps.cpe-sn.fr:8081/fire/"+fireId);
  				HttpURLConnection con = (HttpURLConnection) url.openConnection();
  				con.setRequestMethod("GET");
  				
  				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  		
  				String inputLine;
  				StringBuffer content = new StringBuffer();
  				while ((inputLine = in.readLine()) != null) {
  				
  					content.append(inputLine);
  					String[] words = inputLine.split(",");
  					
  					for(int i = 0; i < words.length; i++) {
  						words[i] = words[i].split(":")[1];
  					}
  					words[5] = words[5].substring(0,words[5].length()-1);
  					
  					fireIntensity = Double.parseDouble(words[2]);  
  					//fireId = Integer.parseInt(words[0]);  
  					//System.out.println(words[0]);
  				}
  				in.close();
  				
  			} catch (MalformedURLException e) {
  				
  			}
		return fireIntensity;
  			
	}

		public double[] getAllFire() throws IOException{  
			  
			  double[] coordinates= new double[4];
			  try {
			  URL url = new URL("http://vps.cpe-sn.fr:8081/fire/");
			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			  con.setRequestMethod("GET");
			  
			  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			  
			  String inputLine;
			  StringBuffer content = new StringBuffer();
			  while ((inputLine = in.readLine()) != null) {
			  
			  content.append(inputLine);
			  String[] words = inputLine.split(",");
			  
			  for(int i = 0; i < words.length; i++) {
			  words[i] = words[i].split(":")[1];
			  
			  }
			  words[5] = words[5].substring(0,words[5].length()-1);
			  
			  coordinates[0] = Double.parseDouble(words[5]);//lon
			  coordinates[1] = Double.parseDouble(words[4]);//lat
			  coordinates[2] = Double.parseDouble(words[2]);//intensity
			  coordinates[3] = Double.parseDouble(words[0]);//id
			  
			  }
			  in.close();
			  
			  } catch (MalformedURLException e) {
			  
			  }
			  return coordinates;

			}

		
		
		@RequestMapping(value = { "/moveVehicle/{teamuuid}" }, method = RequestMethod.GET)
		public double updateVehicle(@PathVariable String teamuuid, @PathVariable int id, @RequestBody VehicleDto vehicle) throws IOException, InterruptedException { 
		  double[] coordinates = getAllFire();
		  
		  double fireIntensity = getOneFire((int)coordinates[3]);
		  
		  coordinates = getAllFire();
		JSONObject json = new JSONObject();
		  json.put("crewMember", vehicle.getCrewMember());
		  json.put("facilityRefID", 82);
		  json.put("fuel", vehicle.getFuel());
		  json.put("id", id);
		  json.put("lat", coordinates[0]);
		  json.put("liquidQuantity", vehicle.getLiquidQuantity());
		  json.put("liquidType", vehicle.getLiquidType());
		  json.put("lon", coordinates[1]);
		  json.put("type", vehicle.getType());
		  
		  HttpPost post = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid); 

		        post.setEntity(new StringEntity(json.toString(),ContentType.APPLICATION_JSON)); 

		        try (CloseableHttpClient httpClient = HttpClients.createDefault();
		             CloseableHttpResponse response = httpClient.execute(post)) { 

		            //System.out.println(EntityUtils.toString(response.getEntity()));
		        }
		  
		  
		  while(true) {
		  
		  if(fireIntensity > 0) {  
		  
		  Thread.sleep(1000);
		  System.out.println(fireIntensity+" "+coordinates[3]);
		  }
		  
		  else {
		  Thread.sleep(5000);
		  coordinates = getAllFire();
		  JSONObject json2 = new JSONObject();
		  json.put("crewMember", vehicle.getCrewMember());
		    json.put("facilityRefID", 82);
		    json.put("fuel", vehicle.getFuel());
		    json.put("id", vehicle.getId());
		    json.put("lat", coordinates[0]);
		    json.put("liquidQuantity", vehicle.getLiquidQuantity());
		    json.put("liquidType", vehicle.getLiquidType());
		    json.put("lon", coordinates[1]);
		    json.put("type", vehicle.getType());
		    
		    HttpPost post2 = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid);

		          post2.setEntity(new StringEntity(json2.toString(),ContentType.APPLICATION_JSON));

		          try (CloseableHttpClient httpClient = HttpClients.createDefault();
		               CloseableHttpResponse response = httpClient.execute(post2)) {

		              //System.out.println(EntityUtils.toString(response.getEntity()));
		          }
		  }
		  fireIntensity = getOneFire((int)coordinates[3]);
		  }
}

  	
  	/*@RequestMapping(value = { "/eteindre"}, method = RequestMethod.GET)
  		public void eteindre_feu() {
  		
  	}*/
  	
  	@RequestMapping(value="/getVehicle/{id}", method=RequestMethod.GET)
  	public double[] getVehicle(@PathVariable int id) throws IOException, InterruptedException {
  		double[] coordinates= new double[3];
  		try {
  				URL url = new URL("http://vps.cpe-sn.fr:8081/vehicle/"+id);
  				HttpURLConnection con = (HttpURLConnection) url.openConnection();
  				con.setRequestMethod("GET");
  				
  				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  				System.out.println(in);
  				String inputLine;
  				StringBuffer content = new StringBuffer();
  				while ((inputLine = in.readLine()) != null) {
  					System.out.println(inputLine);
  					content.append(inputLine);
  					String[] words = inputLine.split(",");
  					
  					for(int i = 0; i < words.length; i++) {
  						words[i] = words[i].split(":")[1];
	  					//System.out.println(words[i]);
  					}
  					words[5] = words[5].substring(0,words[5].length()-1);
  					
  					coordinates[0] = Double.parseDouble(words[5]);
  					coordinates[1] = Double.parseDouble(words[4]);
  					coordinates[2] = Double.parseDouble(words[2]);
  					  				}
  				in.close();
  				
  			} 
  		catch (MalformedURLException e) {
  				
  			}
  		return coordinates;
  }
  	
  	
  	public void updateVehicle(String teamuuid,int id,double lat,double lon) throws IOException, InterruptedException { 
  		
  		JSONObject json = new JSONObject();
  		json.put("crewMember", 5);
  		json.put("facilityRefID", 82);
  		json.put("fuel", 100);
  		json.put("id", id);
  		json.put("lat", lat);
  		json.put("liquidQuantity", 100);
  		json.put("liquidType", "ALL");
  		json.put("lon", lon);
  		json.put("type", "CAR");
  	
  		HttpPost post = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid);

        post.setEntity(new StringEntity(json.toString(),ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
  	}
        
  	
	@RequestMapping(value="/moveLine/{teamuuid}/{id}", method=RequestMethod.GET)
	public void moveInLine(@PathVariable int id,@PathVariable String teamuuid) throws IOException, InterruptedException {
		double[] vehiculeCoordinates = getVehicle(id);
		
		double[] fireCoordinates = getAllFire();
		  
		
		double x = vehiculeCoordinates[0] - fireCoordinates[1];
		double y = vehiculeCoordinates[1] - fireCoordinates[0];
		
		double rapport = y/x;
		
		double deplacement = 0.0001;
		
		double deplacement_x = deplacement/(rapport*Math.sqrt(2));
		double deplacement_y = deplacement_x*rapport;
		
		double distance = (vehiculeCoordinates[0]-fireCoordinates[0])*(vehiculeCoordinates[0]-fireCoordinates[0]) + (vehiculeCoordinates[0]-fireCoordinates[0]);
		
		while(distance>deplacement){
			updateVehicle(teamuuid, id, vehiculeCoordinates[0]+deplacement_x, vehiculeCoordinates[1]+deplacement_y);
			distance = (vehiculeCoordinates[0]-fireCoordinates[0])*(vehiculeCoordinates[0]-fireCoordinates[0]) + (vehiculeCoordinates[0]-fireCoordinates[0]);
			x = vehiculeCoordinates[0] - fireCoordinates[0];
			y = vehiculeCoordinates[1] - fireCoordinates[1];
			
			Thread.sleep(100);
		}
		
	}
}

