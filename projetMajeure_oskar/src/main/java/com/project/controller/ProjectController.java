package com.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import com.project.model.dto.VehicleDto;

@RestController 
public class ProjectController {
	
  	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
  	public String index(Model model) {  
  		return "index";
  	}
  	
  	// VEHICLE
  	
  	@RequestMapping(value = { "/vehicle" }, method = RequestMethod.GET)
	public StringBuffer getAllFacility(Model model) throws IOException {  
			try {
				
				System.out.println("blabla");

				URL url = new URL("http://vps.cpe-sn.fr:8081/vehicle/");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				System.out.println(in);
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
					System.out.println(inputLine);
					String[] words = inputLine.split(",");
					
					for(int i = 0; i < words.length; i++) {
						words[i] = words[i].split(":")[1];
						System.out.println(words[i]);
					}
					words[words.length-1] = words[words.length-1].substring(0,words[words.length-1].length()-1);
					System.out.println(words[words.length-1]);
				}
				in.close();
				return content;
			} catch (MalformedURLException e) {
				
			}
			return null;
  	}
  	
  	@RequestMapping(value = { "/vehicle/{id}" }, method = RequestMethod.GET)
	public StringBuffer getFacilitybyId(@PathVariable String id) throws IOException {  
			try {
				URL url = new URL("http://vps.cpe-sn.fr:8081/vehicle/"+id);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				System.out.println(in);
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
					System.out.println(inputLine);
					String[] words = inputLine.split(",");
					
					for(int i = 0; i < words.length; i++) {
						words[i] = words[i].split(":")[1];
						System.out.println(words[i]);
					}
					words[words.length-1] = words[words.length-1].substring(0,words[words.length-1].length()-1);
					System.out.println(words[words.length-1]);
				}
				in.close();
				return content;
			} catch (MalformedURLException e) {
				
			}
			return null;
  	}
  	
  	@RequestMapping(value = { "/addVehicle" }, method = RequestMethod.GET)
	public String addMyVehicule(Model model) throws IOException {  
    	VehicleDto my_vehicle = new VehicleDto();
    	model.addAttribute("my_vehicle", my_vehicle);
    	System.out.println("blabla");
    	return "addVehicle";
	}
  	
  	@RequestMapping(value = { "/addVehicle" }, method = RequestMethod.POST)
	public VehicleDto addVehicule(@ModelAttribute("my_vehicle") VehicleDto my_vehicle) throws IOException {  
    	return my_vehicle;
	}
  	
  	@RequestMapping(value = { "/vehicle/{teamuuid}/{id}" }, method = RequestMethod.PUT)
  	public StringBuffer updateVehicle(@PathVariable String teamuuid, @PathVariable String id) throws IOException {
  		return null;
	}
  
  	@RequestMapping(value = { "/vehicle/{teamuuid}" }, method = RequestMethod.DELETE)
	public String delAllVehicle(@PathVariable int teamuuid) throws IOException {  
		try {
			URL url = new URL("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
		} catch (MalformedURLException e) {
		}
		return null;
	}
  	
  	@RequestMapping(value = { "/vehicle/{teamuuid}/{id}" }, method = RequestMethod.DELETE)
	public StringBuffer delVehicle(@PathVariable int teamuuid, @PathVariable int id) throws IOException {  
		try {
			URL url = new URL("http://vps.cpe-sn.fr:8081/vehicle/"+teamuuid+"/"+id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
		} catch (MalformedURLException e) {
		}
		return null;
  	}
  	
  	// FACILITY
  	
  	@RequestMapping(value = { "/facility" }, method = RequestMethod.GET)
	public StringBuffer getFacility(Model model) throws IOException {  
  		return null;
	}
  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.GET)
  	public StringBuffer getAllFacilities(@PathVariable String id) throws IOException {
  		return null;
	}
  	
  	@RequestMapping(value = { "/facility/object/{id}" }, method = RequestMethod.GET)
	public StringBuffer getFacilityObj(@PathVariable String id) throws IOException {  
  		return null;
	}
  	  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.POST)
  	public StringBuffer addFacility(@PathVariable String id) throws IOException {
  		return null;
	}
  	
  	@RequestMapping(value = { "/facility" }, method = RequestMethod.DELETE)
	public String delAllFacillities(Model model) {  
		return null;
	}
  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.DELETE)
	public String delFacility(@PathVariable String id) {  
		return null;
	}
  	
  	// FIRE
  	
  	@RequestMapping(value = { "/fire" }, method = RequestMethod.GET)
	public StringBuffer getFire(Model model) throws IOException {  
  		return null;
	}
  	
  	@RequestMapping(value = { "/fire/{id}" }, method = RequestMethod.GET)
  	public StringBuffer getAllFires(@PathVariable String id) throws IOException {
  		return null;
	}
  	
  	@RequestMapping(value = { "/fire/distance" }, method = RequestMethod.GET)
	public StringBuffer getDistanceBetweenCoords(Model model) throws IOException {  
  		return null;
	}
  	  	
  	@RequestMapping(value = { "/fire" }, method = RequestMethod.DELETE)
  	public StringBuffer removeAllFires(Model model) throws IOException {
  		return null;
	}
  	
  	// TEAM
  	
  	@RequestMapping(value = { "/team" }, method = RequestMethod.GET)
	public StringBuffer getAllTeams(Model model) throws IOException {  
  		return null;
	}
  	
  	@RequestMapping(value = { "/team" }, method = RequestMethod.POST)
  	public StringBuffer addTeam(@PathVariable String id) throws IOException {
  		return null;
	}
  	
  	@RequestMapping(value = { "/team" }, method = RequestMethod.PUT)
	public StringBuffer updateTeam(@PathVariable String id) throws IOException {  
  		return null;
	}
  	  	
  	@RequestMapping(value = { "/team" }, method = RequestMethod.DELETE)
  	public StringBuffer delAllTeams(@PathVariable String id) throws IOException {
  		return null;
	}
  	
  	@RequestMapping(value = { "/team/{id}" }, method = RequestMethod.GET)
	public String getTeam(Model model) {  
		return null;
	}
  	
  	@RequestMapping(value = { "/team/{id}" }, method = RequestMethod.DELETE)
	public String delTeam(@PathVariable String id) {  
		return null;
	}

}
