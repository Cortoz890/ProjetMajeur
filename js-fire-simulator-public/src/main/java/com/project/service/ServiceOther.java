package com.project.service;

import java.util.ArrayList;
import java.util.Hashtable;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class ServiceOther {
	
	private Hashtable<VehicleDto, Boolean> dico = new Hashtable<VehicleDto, Boolean>();
	ServiceFire service_fire = new ServiceFire();
	ServiceVehicle service_vehicle = new ServiceVehicle();
	ArrayList<VehicleDto> our_vehicle_list;
	ArrayList<FireDto> our_fire_list;
	
	public ServiceOther() {
		our_vehicle_list = service_vehicle.getOur_vehicle_list();
		our_fire_list = service_fire.getOur_fire_list();
		setDico();
	}
	
	public void updateLists() {
		our_vehicle_list = service_vehicle.getOur_vehicle_list();
		our_fire_list = service_fire.getOur_fire_list();
		setDico();
	}
	
	/*-------------------------------------------------------------------------------*/
	
	public void setDico() {
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (!dico.containsKey(my_vehicle))
			dico.put(my_vehicle, true);
		}
	}
	
	public void changeDico(VehicleDto my_vehicle) {
		dico.put(my_vehicle, !dico.get(my_vehicle));
	}
	
	/*-------------------------------------------------------------------------------*/
	
	public void assignVehicleToFire_basedOnEfficiency(FireDto my_fire) {
		double best_eff = 0.0f;
		VehicleDto best_vehicle = new VehicleDto();
		for (VehicleDto my_vehicle : our_vehicle_list) {
			double vehicle_eff = my_vehicle.getLiquidType().getEfficiency(my_fire.getType());
			if ((best_eff < vehicle_eff) & (dico.get(my_vehicle))) {
				best_eff = vehicle_eff;
				best_vehicle = my_vehicle;
			}
		}
		sendVehicleToFire(best_vehicle, my_fire);
	}
	
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
	
	public void assignFireToVehicle_basedOnDistance() {
		for (VehicleDto my_vehicle : our_vehicle_list) {
			if (dico.get(my_vehicle)) {
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
			if ((best_quality > my_quality) & (dico.get(my_vehicle))) {
				best_quality = my_quality;
				best_vehicle = my_vehicle;
			}
		}
		sendVehicleToFire(best_vehicle, my_fire);
	}
	
	public void sendVehicleToFire(VehicleDto my_vehicle, FireDto my_fire) {
		changeDico(my_vehicle);
		// goToFire
		// when fire extinguished :
		changeDico(my_vehicle);
	}
	
}
