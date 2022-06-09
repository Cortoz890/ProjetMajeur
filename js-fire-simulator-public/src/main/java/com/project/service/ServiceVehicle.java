package com.project.service;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
public class ServiceVehicle {
	
	private ArrayList<VehicleDto> our_vehicle_list = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_CAR = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_FIRE_ENGINE = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_PUMPER_TRUCK = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_WATER_TENDERS = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_TURNTABLE_LADDER_TRUCK = new ArrayList<VehicleDto>();
	private ArrayList<VehicleDto> our_vehicle_list_TRUCK = new ArrayList<VehicleDto>();

	private static final int facilityRefID = 663497;
	
	public ServiceVehicle() {
		getOurVehicles();
		sort_vehicle_list();
	}
	
	public ArrayList<VehicleDto> getOur_vehicle_list() {
		getOurVehicles();
		return our_vehicle_list;
	}
	
	public void getOurVehicles() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<VehicleDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/vehicle", VehicleDto[].class);
		VehicleDto[] vehicles = result.getBody();
		ArrayList<VehicleDto> my_vehicle_list = new ArrayList<VehicleDto>();
		for (int i=0 ; i < vehicles.length ; i++) {
			if ((vehicles[i].getFacilityRefID() == facilityRefID) & !my_vehicle_list.contains(vehicles[i])) {
				my_vehicle_list.add(vehicles[i]);
			}
		}
		our_vehicle_list =  my_vehicle_list;
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
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (my_vehicle.getId() == id) {
				return my_vehicle;
			}
		}
		return null;
	}
	
	
}
