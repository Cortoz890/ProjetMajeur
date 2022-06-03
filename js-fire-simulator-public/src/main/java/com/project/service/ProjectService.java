package com.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.project.model.dto.VehicleDto;;

@Service
public class ProjectService {
	
	private List<VehicleDto> vehicle_list;


	public ProjectService() {
		setVehicle_list(new ArrayList<VehicleDto>());
		//vehicle_list.add(new VehicleDto());
	}


	public List<VehicleDto> getVehicle_list() {
		return vehicle_list;
	}


	public void setVehicle_list(List<VehicleDto> vehicle_list) {
		this.vehicle_list = vehicle_list;
	}
	
	
	
	
}
