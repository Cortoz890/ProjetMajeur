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

import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.project.tools.GisTools;

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

  	@RequestMapping(value = { "/facility" }, method = RequestMethod.GET)
		public FacilityDto[] getAllFacilities(Model model) throws IOException {  
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

		
		
		@RequestMapping(value = { "/moveVehicle/{teamuuid}/{id}" }, method = RequestMethod.GET)
		public double updateVehicle(@PathVariable String teamuuid, @PathVariable int id, @RequestBody VehicleDto vehicle) throws IOException, InterruptedException { 
		  double[] coordinatesF = getAllFire();
		  double[] coordinatesV = getVehicle(3266);
		  double fireIntensity = getOneFire((int)coordinatesF[3]);
		  
		  GisTools convertisseur = new GisTools();
		  
		  double[] nextCoordinates = getNextCoordinate(coordinatesF[0], coordinatesF[1], coordinatesV[0], coordinatesV[1]);
		  
		  JSONObject json = new JSONObject();
		  json.put("crewMember", vehicle.getCrewMember());
		  json.put("facilityRefID", 82);
		  json.put("fuel", vehicle.getFuel());
		  json.put("id", id);
		  json.put("lat", nextCoordinates[0]);
		  json.put("liquidQuantity", vehicle.getLiquidQuantity());
		  json.put("liquidType", vehicle.getLiquidType().toString());
		  json.put("lon", nextCoordinates[1]);
		  json.put("type", vehicle.getType().toString());
		  
		  HttpPost post = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid); 

		        post.setEntity(new StringEntity(json.toString(),ContentType.APPLICATION_JSON)); 

		        try (CloseableHttpClient httpClient = HttpClients.createDefault();
		             CloseableHttpResponse response = httpClient.execute(post)) { 

		            //System.out.println(EntityUtils.toString(response.getEntity()));
		        }
		  
		  
		  while(true) {
		  Thread.sleep(2000);
		  if(GisTools.computeDistance2(new Coord(coordinatesF[0],coordinatesF[1]), new Coord(coordinatesV[0],coordinatesV[1]))>5 && fireIntensity > 0) {
			  System.out.println("avance");
			  coordinatesF = getAllFire();
			  coordinatesV = getVehicle(3266);
			  fireIntensity = getOneFire((int)coordinatesF[3]);
			  
			  nextCoordinates = getNextCoordinate(coordinatesF[0], coordinatesF[1], coordinatesV[0], coordinatesV[1]);
			  
			  json = new JSONObject();
			  json.put("crewMember", vehicle.getCrewMember());
			  json.put("facilityRefID", 82);
			  json.put("fuel", vehicle.getFuel());
			  json.put("id", id);
			  json.put("lat", nextCoordinates[0]);
			  json.put("liquidQuantity", vehicle.getLiquidQuantity());
			  json.put("liquidType", vehicle.getLiquidType());
			  json.put("lon", nextCoordinates[1]);
			  json.put("type", vehicle.getType());
			  
			  post = new HttpPost("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid); 
	
			        post.setEntity(new StringEntity(json.toString(),ContentType.APPLICATION_JSON)); 
	
			        try (CloseableHttpClient httpClient = HttpClients.createDefault();
			             CloseableHttpResponse response = httpClient.execute(post)) { 
	
			            //System.out.println(EntityUtils.toString(response.getEntity()));
			        }
			  fireIntensity = getOneFire((int)coordinatesF[3]);
		  }
		  else {
			  System.out.println("eteint");
			  if(fireIntensity>0) {
				  Thread.sleep(1000);
			  }
			  
			  else {
				  coordinatesF = getAllFire();
				  fireIntensity = getOneFire((int)coordinatesF[3]);
			  }
		  }
	}
}
  	@RequestMapping(value = { "/vehicle/{teamuuid}/{id}" }, method = RequestMethod.DELETE)
		public String delVehicle(@PathVariable int teamuuid, @PathVariable int id) {  
  			int myTeamuuid = teamuuid;
  			int vehicleId = id;
		return "";
	}

  	
  	/*@RequestMapping(value = { "/eteindre"}, method = RequestMethod.GET)
  		public void eteindre_feu() {
  		
  	}*/
  	
  	public double[] getVehicle(int id) throws IOException, InterruptedException {
  		double[] coordinates= new double[2];
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
  					
  					coordinates[0] = Double.parseDouble(words[1]);
  					coordinates[1] = Double.parseDouble(words[2]);
  					  				}
  				in.close();
  				
  			} 
  		catch (MalformedURLException e) {
  				
  			}
  		return coordinates;
  }
  	
  	
  	
	public double[] getNextCoordinate(double latV, double lonV, double latF, double lonF) throws IOException, InterruptedException {		
		  
		
		double X = latV - latF;
		double Y = lonV - lonF;
				
		double d = 0.0001;
		double D = Math.sqrt((X*X)+(Y*Y));
		
		double deplacement_x = d*X/D;
		double deplacement_y = d*Y/D;
		
		
	
		double[] result = new double[2];
		result[0]=latV+deplacement_x;
		result[1]=lonV+deplacement_y;
		return result;
		
	}
}

