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

import com.project.model.dto.FireDto;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
public class ProjectService {
	
	private VehicleDto[] vehicle_list;
	private FireDto[] fire_list;
	private ArrayList<FireDto> fire_list_A = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_B = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_C = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_D = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_E = new ArrayList<FireDto>();
	private ArrayList<VehicleDto> our_vehicle_list = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_CAR = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_FIRE_ENGINE = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_PUMPER_TRUCK = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_WATER_TENDERS = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_TURNTABLE_LADDER_TRUCK = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_TRUCK = new ArrayList<VehicleDto>();
	private Hashtable<VehicleDto, Boolean> dico = new Hashtable<VehicleDto, Boolean>();
	
	
	public ProjectService() {
		vehicle_list = getVehicle_list();
		fire_list = getFire_list();
		our_vehicle_list = getOurVehicles();
		setDico();
	}
	
	public void updateLists() {
		vehicle_list = getVehicle_list();
		fire_list = getFire_list();
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
			if (vehicle_list[i].getFacilityRefID() == 663497) {
				our_vehicle_list.add(vehicle_list[i]);
			}
		}
		return our_vehicle_list;
	}
	
	public void sort_vehicle_list() {
		reset_vehicle_lists();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (my_vehicle.getType() == VehicleType.CAR) {
				our_vehicle_list_CAR.add(my_vehicle);
			} else if (my_vehicle.getType() == VehicleType.FIRE_ENGINE) {
				our_vehicle_list_FIRE_ENGINE.add(my_vehicle);
			} else if (my_vehicle.getType() == VehicleType.PUMPER_TRUCK) {
				our_vehicle_list_PUMPER_TRUCK.add(my_vehicle);
			} else if (my_vehicle.getType() == VehicleType.WATER_TENDERS) {
				our_vehicle_list_WATER_TENDERS.add(my_vehicle);
			} else if (my_vehicle.getType() == VehicleType.TURNTABLE_LADDER_TRUCK) {
				our_vehicle_list_TURNTABLE_LADDER_TRUCK.add(my_vehicle);
			} else if (my_vehicle.getType() == VehicleType.TRUCK) {
				our_vehicle_list_TRUCK.add(my_vehicle);
			}
		}
	}
	
	public void reset_vehicle_lists() {
		our_vehicle_list_CAR = new ArrayList<VehicleDto>();
		our_vehicle_list_FIRE_ENGINE = new ArrayList<VehicleDto>();
		our_vehicle_list_PUMPER_TRUCK = new ArrayList<VehicleDto>();
		our_vehicle_list_WATER_TENDERS = new ArrayList<VehicleDto>();
		our_vehicle_list_TURNTABLE_LADDER_TRUCK = new ArrayList<VehicleDto>();
		our_vehicle_list_TRUCK = new ArrayList<VehicleDto>();
	}
	
	public VehicleDto getVehicleByID(int id) {
		updateLists();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (my_vehicle.getId() == id) {
				return my_vehicle;
			}
		}
		return null;
	}
	
	public FireDto[] getFire_list() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/fire", FireDto[].class);
		FireDto[] fires = result.getBody();
		return fires;
	}

	public void sort_fire_list() {
		reset_fire_lists();
		for (FireDto my_fire : fire_list) {
			if (my_fire.getType() == "A") {
				fire_list_A.add(my_fire);
			} else if ((my_fire.getType() == "B_Gasoline") & (my_fire.getType() == "B_Alcohol") & (my_fire.getType() == "B_Plastics")) {
				fire_list_B.add(my_fire);
			} else if (my_fire.getType() == "C_Flammable_Gases") {
				fire_list_C.add(my_fire);
			} else if (my_fire.getType() == "D_Metals") {
				fire_list_D.add(my_fire);
			} else if (my_fire.getType() == "E_Electric") {
				fire_list_E.add(my_fire);
			}
		}
	}
	
	public void reset_fire_lists() {
		fire_list_A = new ArrayList<FireDto>();
		fire_list_B = new ArrayList<FireDto>();
		fire_list_C = new ArrayList<FireDto>();
		fire_list_D = new ArrayList<FireDto>();
		fire_list_E = new ArrayList<FireDto>();
	}
	
	public FireDto getFireByID(int id) {
		updateLists();
		for (FireDto my_fire : fire_list) {
			if (my_fire.getId() == id) {
				return my_fire;
			}
		}
		return null;
	}
	
	public void setDico() {
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (!dico.containsKey(my_vehicle))
			dico.put(my_vehicle, true);
		}
	}
	
	public void changeDico(VehicleDto my_vehicle, boolean state) {
		dico.put(my_vehicle, !dico.get(my_vehicle));
	}
	
	public VehicleDto getBestVehicleForFireType(FireDto my_fire) {
		double best_eff = 0.0f;
		VehicleDto best_vehicle = new VehicleDto();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			double vehicle_eff = my_vehicle.getLiquidType().getEfficiency(my_fire.getType());
			if ((best_eff < vehicle_eff) & (dico.get(my_vehicle))) {
				best_eff = vehicle_eff;
				best_vehicle = my_vehicle;
			}
		}
		return best_vehicle;
	}
	
	public double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
		return Math.sqrt(Math.pow((lat1-lat2), 2) + Math.pow((lon1-lon2), 2));
	}
	
	public void assignFireToVehicle() {
		updateLists();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (dico.get(my_vehicle)) {
				double latV = my_vehicle.getLat();
				double lonV = my_vehicle.getLon();
				double lowest_distance = 10000;
				FireDto nearest_fire = new FireDto();
				for (FireDto my_fire : fire_list) {
					double latF = my_fire.getLat();
					double lonF = my_fire.getLon();
					double my_distance = distanceBetween(latV, lonV, latF, lonF);
					if (lowest_distance > my_distance) {
						lowest_distance = my_distance;
						nearest_fire = my_fire;
					}
				}
				sendVehicleToFire(my_vehicle, nearest_fire);
			}
		}
	}
	
	public void sendVehicleToFire(VehicleDto my_vehicle, FireDto my_fire) {
		
	}
	
}
