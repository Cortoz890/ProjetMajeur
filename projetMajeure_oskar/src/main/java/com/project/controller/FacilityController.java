package com.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class FacilityController {
	
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
  	
  	@RequestMapping(value = { "/facility/{id}" }, method = RequestMethod.GET)
	public StringBuffer getFacilitybyId(@PathVariable String id) throws IOException {  
			try {
				
				System.out.println("blabla");

				URL url = new URL("http://vps.cpe-sn.fr:8081/facility/"+id);
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
}

