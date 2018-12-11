package com.SportsWatchProject.Controllers;

import com.SportsWatchProject.Models.Notifications;
import com.SportsWatchProject.Controllers.NotificationService;

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
//import com.example.demo.models.Profile;
import com.SportsWatchProject.Models.User;
import com.SportsWatchProject.Repository.UserRepository;


 @Controller
 public class userController{
	 @Autowired
	 public UserRepository userRepo;
	 
	 @Autowired
		private NotificationService notificationService;

	 @GetMapping(path="/login")
		public ModelAndView getHomepage() {
		 ModelAndView demo = new ModelAndView("login");
			return demo;
		
		}
	 @GetMapping("/error")
	 public ModelAndView showNotifications() {
		 return new ModelAndView("Error");
	 }
	 @GetMapping("/showNotifications")
		public ModelAndView showNotifications(HttpServletRequest request,@RequestParam("uemail") String userEmail) {
{

			ModelAndView modelAndView = new ModelAndView();
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("name");
			User u = userRepo.findByEmail(userEmail);
			
			List<Notifications> received = notificationService.getNotificationsForUser(u.getId());

			List<Notifications> toBeDisplayed = new ArrayList<>();

			for (int i=0;i<received.size();i++){
				if(!received.get(i).isVisited()){
					toBeDisplayed.add(received.get(i));
				}
			}

			modelAndView.addObject("list",toBeDisplayed);
			modelAndView.setViewName("notifications");
			return modelAndView;

		}
		
		
	}

	 @GetMapping(path="/logout")
		public String logout(HttpSession session) 
	 {
		session.invalidate();
		return "index";
			
		}
	 
	 @PostMapping("/login")
	 public ModelAndView handleLogin(
			 @RequestParam("userID") String userID,
			 @RequestParam("userName") String userName,
			 @RequestParam("userEmail") String userEmail,
			 HttpSession session
			 ) {
		 System.out.println("here");
		 if(userRepo.findByEmail(userEmail) == null) {
			 User user =new User();
			 user.setId_fb(Long.parseLong(userID));
			 user.setName(userName);
			 user.setEmail(userEmail);
			 boolean status=true;
			 user.setStatus(status);
			 userRepo.save(user);
			
		 }else {
			 User u = userRepo.findByEmail(userEmail);
			 if(!u.isStatus()) {
			 return new ModelAndView("redirect:/save");
			 }
			 
		 }
		 session.setAttribute("email", userEmail);
		 System.out.println(userID + userName + userEmail);
//		 return "index";
		 return new ModelAndView("redirect:/save");
		
	 }
	 } 
 
