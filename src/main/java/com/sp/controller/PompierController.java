package com.sp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

  
@Controller 
public class PompierController {
	  
  	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
  		public String index(Model model) {  
  		return "index";
  	}
  	
  	@RequestMapping(value = { "gestion_vehicules" }, method = RequestMethod.GET)
		public String gestion_vehicules(Model model) {  
		return "gestion_vehicules";
	}
}
