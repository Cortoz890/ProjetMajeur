package com.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import javax.print.DocFlavor.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController 
public class ProjectController {
	
	
	  
//  	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
//  		public String index(Model model) {  
//  		return "index.html";
//  	}
  	

  	@RequestMapping(value = { "/facility" }, method = RequestMethod.GET)
		public StringBuffer getAllFacilities(Model model) throws IOException {  
  		
		try {
			URL url = new URL("http://vps.cpe-sn.fr:8081/facility");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return content;
		} catch (MalformedURLException e) {
			
		}
		return null;
	}
  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.GET)
		public StringBuffer getFacilitybyId(@PathVariable String id) throws IOException {  
  			try {
  				URL url = new URL("http://vps.cpe-sn.fr:8081/facility/"+id);
  				HttpURLConnection con = (HttpURLConnection) url.openConnection();
  				con.setRequestMethod("GET");
  				
  				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  				String inputLine;
  				StringBuffer content = new StringBuffer();
  				while ((inputLine = in.readLine()) != null) {
  					content.append(inputLine);
  				}
  				in.close();
  				return content;
  			} catch (MalformedURLException e) {
  				
  			}
  			return null;
	}
  	
  	@RequestMapping(value = { "/vehicule/{teamuuid}" }, method = RequestMethod.POST)
		public StringBuffer addVehicule(@PathVariable String teamuuid) throws IOException {  
			try {
  				URL url = new URL("http://vps.cpe-sn.fr:8081/vehicule/"+teamuuid);
  				HttpURLConnection con = (HttpURLConnection) url.openConnection();
  				con.setRequestMethod("POST");
  				
  				int status = con.getResponseCode();
  				
  				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  				String inputLine;
  				StringBuffer content = new StringBuffer();
  				while ((inputLine = in.readLine()) != null) {
  					content.append(inputLine);
  				}
  				in.close();
  				System.out.println(status);
  				return content;
  			} catch (MalformedURLException e) {
  				
  			}
  			return null;
	}
  	
  	@RequestMapping(value = { "/vehicle/{teamuuid}" }, method = RequestMethod.DELETE)
		public String delAllVehicle(@PathVariable int teamuuid) {  
  			int myTeamuuid = teamuuid;
		return "";
	}

  	@RequestMapping(value = { "/vehicle/{teamuuid}/{id}" }, method = RequestMethod.PUT)
		public String updateVehicle(@PathVariable int teamuuid, @PathVariable int id) {  
  			int myTeamuuid = teamuuid;
  			int vehicleId = id;
		return "";
	}
  	
  	@RequestMapping(value = { "/vehicle/{teamuuid}/{id}" }, method = RequestMethod.DELETE)
		public String delVehicle(@PathVariable int teamuuid, @PathVariable int id) {  
  			int myTeamuuid = teamuuid;
  			int vehicleId = id;
		return "";
	}

  	
  	
  	@RequestMapping(value = { "/gestion_vehicules" }, method = RequestMethod.GET)
		public String gestion_vehicules(Model model) {  
		return "gestion_vehicules";
	}
}
