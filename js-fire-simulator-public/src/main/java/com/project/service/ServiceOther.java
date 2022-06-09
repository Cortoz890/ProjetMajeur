package com.project.service;

import java.util.ArrayList;
import java.util.Hashtable;

import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class ServiceOther {
	
	private Hashtable<VehicleDto, Boolean> availabilityDico = new Hashtable<VehicleDto, Boolean>();
	private Hashtable<VehicleDto, Coord> assignmentDico = new Hashtable<VehicleDto, Coord>();
	ServiceFire service_fire = new ServiceFire();
	ServiceVehicle service_vehicle = new ServiceVehicle();
	ArrayList<VehicleDto> our_vehicle_list;
	ArrayList<FireDto> our_fire_list;
	
	private static final double facility_lat = 1.45; // à modifier
	private static final double facility_lon = 4.53;
	private static final Coord facility_coord = new Coord(facility_lat, facility_lon);
	
	public ServiceOther() {
		our_vehicle_list = service_vehicle.getOur_vehicle_list();
		our_fire_list = service_fire.getOur_fire_list();
		setAvailabilityDico();
	}
	
	public void updateLists() {
		our_vehicle_list = service_vehicle.getOur_vehicle_list();
		service_fire.updateFires();
		our_fire_list = service_fire.getOur_fire_list();
		setAvailabilityDico();
	}
	
	/*------------------------------------dico---------------------------------------*/
	
	public void setAvailabilityDico() {
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (!availabilityDico.containsKey(my_vehicle))
				availabilityDico.put(my_vehicle, true);
		}
	}
	
	public void changeAvailabilityDico(VehicleDto my_vehicle) {
		availabilityDico.put(my_vehicle, !availabilityDico.get(my_vehicle));
	}
	
	/*--------------------------------assignations-----------------------------------*/
	
	public void assignVehicleToFire_basedOnEfficiency(FireDto my_fire) {
		double best_eff = 0.0f;
		VehicleDto best_vehicle = new VehicleDto();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			double vehicle_eff = my_vehicle.getLiquidType().getEfficiency(my_fire.getType());
			if ((best_eff < vehicle_eff) & (availabilityDico.get(my_vehicle))) {
				best_eff = vehicle_eff;
				best_vehicle = my_vehicle;
			}
		}
		sendVehicleToFire(best_vehicle, my_fire);
		assignmentDico.put(best_vehicle, new Coord(my_fire.getLat(), my_fire.getLon()));
	}
	
	public void assignFireToVehicle_basedOnDistance() {
		updateLists();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (availabilityDico.get(my_vehicle)) {
				double latV = my_vehicle.getLat();
				double lonV = my_vehicle.getLon();
				double lowest_distance = 10000;
				FireDto nearest_fire = new FireDto();
				for (FireDto my_fire : our_fire_list) {
					double latF = my_fire.getLat();
					double lonF = my_fire.getLon();
					double my_distance = distanceBetween(latV, lonV, latF, lonF);
					if (lowest_distance > my_distance) {
						lowest_distance = my_distance;
						nearest_fire = my_fire;
					}
				}
				sendVehicleToFire(my_vehicle, nearest_fire);
				assignmentDico.put(my_vehicle, new Coord(nearest_fire.getLat(), nearest_fire.getLon()));
			}
		}
	}
	
	public void assignVehicleToFire_basedOnEfficiencyAndDistance(FireDto my_fire) {
		double best_quality = 10000;
		VehicleDto best_vehicle = new VehicleDto();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			double my_eff = my_vehicle.getLiquidType().getEfficiency(my_fire.getType());
			double my_distance = distanceBetween(my_vehicle, my_fire);
			double my_quality = my_distance / my_eff;
			// la formule pour my_quality peut être ajustée, mais ya moyen que ça soit déjà bon.
			if ((best_quality > my_quality) & (availabilityDico.get(my_vehicle))) {
				best_quality = my_quality;
				best_vehicle = my_vehicle;
			}
		}
		sendVehicleToFire(best_vehicle, my_fire);
		assignmentDico.put(best_vehicle, new Coord(my_fire.getLat(), my_fire.getLon()));
	}
	
	/*--------------------------------calcul-Distance--------------------------------*/
	
	public double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
		return Math.sqrt(Math.pow((lat1-lat2), 2) + Math.pow((lon1-lon2), 2));
	}
	
	public double distanceBetween(VehicleDto my_vehicle, FireDto my_fire) {
		double latV = my_vehicle.getLat();
		double lonV = my_vehicle.getLon();
		double latF = my_fire.getLat();
		double lonF = my_fire.getLon();
		return Math.sqrt(Math.pow((latV-latF), 2) + Math.pow((lonV-lonF), 2));
	}
	
	/*----------------------------------move-Vehicle---------------nul---------------*/
	
	// Je crois que de cette manière on ne peut faire bouger qu'un seul véhicule à la fois du coup on peut
	// aussi faire avec le "assignmentDico". où on attribue à chaque véhicule des coords de destination (feu ou caserne).
	// Puis à chaque itération de déplacement, on parcours ce dico et on fait un déplacement élémentaire pour chaque véhicule
	// vers les coords assignées.
	
	public void sendVehicleToFire(VehicleDto my_vehicle, FireDto my_fire) {
		changeAvailabilityDico(my_vehicle);
		// goToFire ( = moveVehicleTo() mais avec ordre d'attendre sur place le temps que le feu s'éteigne )
		// when has arrived :
		assignmentDico.remove(my_vehicle);
		// when fire extinguished :
		our_fire_list.remove(my_fire);
		changeAvailabilityDico(my_vehicle);
	}
	
	public void moveVehicleTo(VehicleDto my_vehicle, double lat, double lon) {
		changeAvailabilityDico(my_vehicle);
		// goToCoords
		// when has arrived :
		changeAvailabilityDico(my_vehicle);
		assignmentDico.remove(my_vehicle);
	}

	/*----------------------------------move-Vehicle--------------mieux--------------*/
	
	public void moveAllAssignedVehicles() {
		assignmentDico.forEach((my_vehicle, coord) -> {
			elementaryMove(my_vehicle, assignmentDico.get(my_vehicle));
		});
	}
	
	public void elementaryMove(VehicleDto my_vehicle, Coord my_coord) {
		// effectue un mvmt élémentaire.
	}
	
	/*------------------------------------checks-------------------------------------*/
	
	// Possibilité de faire les checks soit pour un véhicule, soit pour tous ( voir respectivement checkFuelToFacility(my_vehicle) et checkLiquid() )
	
	public void checkLiquid() { 
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if ((my_vehicle.getFuel() < 0)) {
				moveVehicleTo(my_vehicle, facility_lat, facility_lon);
				assignmentDico.replace(my_vehicle, facility_coord);
			}
		}
	}
	
	public void checkFuelToFacility(VehicleDto my_vehicle) {
		double vehicle_lat = my_vehicle.getLat();
		double vehicle_lon = my_vehicle.getLon();
		double my_distance = distanceBetween(vehicle_lat, vehicle_lon, facility_lat, facility_lon);
		if ((my_distance + 1) > my_vehicle.getFuel()) { // La formule est à ajuster.
			moveVehicleTo(my_vehicle, facility_lat, facility_lon);
			assignmentDico.replace(my_vehicle, facility_coord);
		}
	}
	
}
