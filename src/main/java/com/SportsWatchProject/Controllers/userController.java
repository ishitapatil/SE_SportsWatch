package com.SportsWatchProject.Controllers;

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
	 @GetMapping(path="/login")
		public ModelAndView getHomepage() {
		 ModelAndView demo = new ModelAndView("login");
			return demo;
		
		}
	 
	 @PostMapping("/login")
	 public ModelAndView handleLogin(
			 @RequestParam("userID") String userID,
			 @RequestParam("userName") String userName,
			 @RequestParam("userEmail") String userEmail,
			 HttpSession session
			 ) {
		 if(userRepo.findByName(userName) == null) {
			 User user =new User();
			 user.setId_fb(Long.parseLong(userID));
			 user.setName(userName);
			 user.setEmail(userEmail);
			 userRepo.save(user);
		 }
		 session.setAttribute("userEmail", userEmail);
		 System.out.println(userID + userName + userEmail);
		 
		 session.setAttribute("userID", userID);
		 return new ModelAndView("redirect:/save");
	 }
	 } 
 
