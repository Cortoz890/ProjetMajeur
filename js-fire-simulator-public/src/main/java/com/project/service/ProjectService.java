package com.project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.VehicleDto;;

@Service
public class ProjectService {
	
	private VehicleDto[] vehicle_list;
	private ArrayList<VehicleDto> our_vehicle_list = new ArrayList<VehicleDto>();
	public Dictionary<VehicleDto, Boolean> dico = new Hashtable<VehicleDto, Boolean>();

	public ProjectService() {
		vehicle_list = getVehicle_list();
		our_vehicle_list = getOurVehicles();
		setDico();
	}
	
	public ArrayList<VehicleDto> getOur_vehicle_list() {
		return our_vehicle_list;
	}
	
	public VehicleDto[] getVehicle_list() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<VehicleDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/vehicle", VehicleDto[].class);
		VehicleDto[] vehicles = result.getBody();
		return vehicles;
	}
	
	public ArrayList<VehicleDto> getOurVehicles() {
		for (int i=0 ; i < vehicle_list.length ; i++) {
			if (vehicle_list[i].getFacilityRefID() == 82) {
				our_vehicle_list.add(vehicle_list[i]);
			}
		}
		return our_vehicle_list;
	}
	
	public void setDico() {
		for (VehicleDto my_vehicle : our_vehicle_list) {
			dico.put(my_vehicle, true);
		}
	}
	
	public void changeDico(VehicleDto my_vehicle, boolean state) {
		dico.put(my_vehicle, !dico.get(my_vehicle));
	}
	
}
