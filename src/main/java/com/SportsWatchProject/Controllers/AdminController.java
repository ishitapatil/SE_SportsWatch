package com.SportsWatchProject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SportsWatchProject.Models.TeamsEntity;
import com.SportsWatchProject.Models.User;
import com.SportsWatchProject.Repository.UserRepository;


import com.SportsWatchProject.Repository.UserRepository;

@Controller
public class AdminController{

	 @Autowired
	 public UserRepository userRepo;
	 @GetMapping(path="/admin")
		public ModelAndView getHomepage(HttpSession session) {
		 if(!session.getAttribute("isAdmin").equals("admin")) {
			 ModelAndView demo = new ModelAndView("redirect:/adminLogin");
			 return demo;
		 }
		 
		 List<User> u = userRepo.findAll();
		 ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		 for (User user : u) {
			HashMap<String,String> gameDetail = new HashMap<String, String>();
			gameDetail.put("name",user.getName());
		 	gameDetail.put("email",user.getEmail());
		 	if(user.isStatus()) {
		 		gameDetail.put("status","Active");
			 	
		 	}else {
		 		gameDetail.put("status","Blocked");
		 	}
	 	gameDetails.add(gameDetail);
		 }
		 ModelAndView demo = new ModelAndView("Admin");
		demo.addObject("ListUser" ,gameDetails);
		 return demo;
		
		}
	 @GetMapping(path="/adminLogin")
		public ModelAndView getLogin() {
		 ModelAndView demo = new ModelAndView("AdminLogin");
		 return demo;
		
		}
	 
	 @PostMapping(path="/adminLogout")
		public ModelAndView logout(HttpSession session) {
		 session.invalidate();
		 ModelAndView demo = new ModelAndView("AdminLogin");
		 return demo;
		
		}
	 
	 @PostMapping(path="/loginasadmin")
		public ModelAndView renderAdminDashboard( 
				 @RequestParam("usname") String userName,
				 @RequestParam("password") String password,
				 HttpSession session) {
		
		if(userName.equals("admin") && password.equals("admin")) {
			session.setAttribute("isAdmin", userName);
			 ModelAndView demo = new ModelAndView("redirect:/admin");
			return demo;
		}else {
			 ModelAndView demo = new ModelAndView("Error");
			return demo;
		}
		
		}
	 
	 @PostMapping(path="/blockUser")
		public ModelAndView renderDashboard( 
				 @RequestParam("uname") String userName,
				 @RequestParam("uemail") String userEmail) {
		 ModelAndView demo = new ModelAndView("redirect:/admin");
		 User u = userRepo.findByEmail(userEmail);
		 u.setStatus(false);
		 userRepo.save(u);
		return demo;
		
		}
	 
	 @PostMapping(path="/unblockUser")
		public ModelAndView renderUser( 
				 @RequestParam("uname") String userName,
				 @RequestParam("uemail") String userEmail) {
		 ModelAndView demo = new ModelAndView("redirect:/admin");
		 User u = userRepo.findByEmail(userEmail);
		 u.setStatus(true);
		 userRepo.save(u);
		return demo;
		
		}
	 
	 
}
	