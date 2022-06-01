package com.sp.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicule {
	private static int id_count = 0;
	private int id;
	private float lon;
	private float lat;
	private String name;
	private int maxVehicleSpace;
	private int peopleCapacity;
	private List<Integer> vehicleIdSet;
	private List<Integer> peopleIdSet;
	private String teamUuid;
	
	public Vehicule() {
		this.id = 0;
		this.lon = 0;
		this.lat = 0;
		this.name = "";
		this.maxVehicleSpace = 0;
		this.peopleCapacity = 0;
		this.vehicleIdSet = new ArrayList<Integer>();
		this.peopleIdSet = new ArrayList<Integer>();
		this.teamUuid = "";
	}

	public Vehicule(int id, float lon, float lat, String name, int maxVehicleSpace, int peopleCapacity,
			List<Integer> vehicleIdSet, List<Integer> peopleIdSet, String teamUuid) {
		this.id = getNewId();
		this.lon = lon;
		this.lat = lat;
		this.name = name;
		this.maxVehicleSpace = maxVehicleSpace;
		this.peopleCapacity = peopleCapacity;
		this.vehicleIdSet = vehicleIdSet;
		this.peopleIdSet = peopleIdSet;
		this.teamUuid = teamUuid;
	}
	
	private int getNewId() {
		id_count += 1;
		return id_count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxVehicleSpace() {
		return maxVehicleSpace;
	}

	public void setMaxVehicleSpace(int maxVehicleSpace) {
		this.maxVehicleSpace = maxVehicleSpace;
	}

	public int getPeopleCapacity() {
		return peopleCapacity;
	}

	public void setPeopleCapacity(int peopleCapacity) {
		this.peopleCapacity = peopleCapacity;
	}

	public List<Integer> getVehicleIdSet() {
		return vehicleIdSet;
	}

	public void setVehicleIdSet(List<Integer> vehicleIdSet) {
		this.vehicleIdSet = vehicleIdSet;
	}

	public List<Integer> getPeopleIdSet() {
		return peopleIdSet;
	}

	public void setPeopleIdSet(List<Integer> peopleIdSet) {
		this.peopleIdSet = peopleIdSet;
	}

	public String getTeamUuid() {
		return teamUuid;
	}

	public void setTeamUuid(String teamUuid) {
		this.teamUuid = teamUuid;
	}
}
