package com.project.service;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.project.model.dto.FireDto;

public class ServiceFire {
	
	private ArrayList<FireDto> our_fire_list = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_A = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_B = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_C = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_D = new ArrayList<FireDto>();
	private ArrayList<FireDto> fire_list_E = new ArrayList<FireDto>();
	
	public ServiceFire() {
		getOurFires();
		sort_fire_list();
	}
	
	public ArrayList<FireDto> getOur_fire_list() {
		getOurFires();
		return our_fire_list;
	}
	
	public void getOurFires(){	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/fire", FireDto[].class);
		FireDto[] fires = result.getBody();
		ArrayList<FireDto> my_fire_list = new ArrayList<FireDto>();
		for (int i=0 ; i < fires.length ; i++) {
			my_fire_list.add(fires[i]);
		}
		our_fire_list = my_fire_list;
	}
	
	public void updateFires(){	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> result = restTemplate.getForEntity("http://vps.cpe-sn.fr:8081/fire", FireDto[].class);
		FireDto[] fires = result.getBody();
		ArrayList<FireDto> my_fire_list = new ArrayList<FireDto>();
		for (int i=0 ; i < fires.length ; i++) {
			my_fire_list.add(fires[i]);
			if (getFireByID(fires[i].getId(), our_fire_list) == null) {
				our_fire_list.add(fires[i]);
			}
		}
		for (int i=0 ; i < our_fire_list.size() ; i++) {
			if (getFireByID(our_fire_list.get(i).getId(), my_fire_list) == null) {
				our_fire_list.remove(our_fire_list.get(i));
			}
		}
	}
	
	public void sort_fire_list() {
		reset_fire_lists();
		for (FireDto my_fire : our_fire_list) {
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
	
	public FireDto getFireByID(int id, ArrayList<FireDto> my_list) {
		for (FireDto my_fire : my_list) {
			if (my_fire.getId() == id) {
				return my_fire;
			}
		}
		return null;
	}

	
	
}
