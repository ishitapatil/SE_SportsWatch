package com.SportsWatchProject.Controllers;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.SportsWatchProject.Models.TeamsEntity;
import com.SportsWatchProject.Repository.TeamsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
    public class teamController {
	@Autowired 
	private TeamsRepository teamsRepo;
	@GetMapping(path="/save")
	public String getdata(HttpSession session, Model Display) {
		
		List<TeamsEntity> team = (List<TeamsEntity>) teamsRepo.findByEmail(session.getAttribute("email").toString());
		Display.addAttribute("teams", team);
		return "index";
		
	}
		
	@GetMapping(path="/favteam")
	public @ResponseBody Iterable<TeamsEntity> getAllUsers() {
		return teamsRepo.findAll();
		
	}

	/*@GetMapping(path="/login")
	public String renderlogin() {
		return "login";
		
	}
*/
	@PostMapping("/addToFav")
	public ModelAndView renderFav(@RequestParam("teamid") String teamID,@RequestParam("teamabb") String teamabb ,@RequestParam("teamname") String teamName , HttpSession session ) {
		ModelAndView mmatr = new ModelAndView();
		TeamsEntity e =new TeamsEntity();
		if(session.getAttribute("email")==null) {
			mmatr.setViewName("redirect:/login");
			return mmatr;
		}
		String email=session.getAttribute("email").toString();
		List<TeamsEntity> tm = teamsRepo.findByEmail(email);
		TeamsEntity r =new TeamsEntity();
		r.setAbbreviation(teamabb);
		r.setFav_team_name(teamName);
		r.setTeam_id(Long.parseLong(teamID));
		boolean flag= true;
		for(TeamsEntity team : tm) {
			if(team.getAbbreviation().equals(teamabb)) {
				flag=false;
				break;
			}
		}
		if(flag) {
			e.setEmail(session.getAttribute("email").toString());
			e.setFav_team_name(teamName);
			e.setAbbreviation(teamabb);
			e.setTeam_id(Long.parseLong(teamID));
			teamsRepo.save(e);
			System.out.println();
		}
		else {
			System.out.println("Rohan Rocks");
		}
		mmatr.setViewName("redirect:/teams");
		
		return mmatr;
	}

	@RequestMapping("/FavTeam")
	public ModelAndView renderIndex() {
		ModelAndView mmatr = new ModelAndView();
		mmatr.setViewName("FavTeam");
		return mmatr;
	}

//	@PostMapping("/save")
//	public ModelAndView saveStuff(HttpSession session, HttpServletRequest request,@RequestParam("Select_Team") String Select_Team) {
//		
//			String userID = session.getAttribute("userID").toString();
//			if(userID == null) {;
//				ModelAndView aa = new ModelAndView("redirect:/login");
//				return aa;
//			}
//		ModelAndView aa = new ModelAndView("index");
//		System.out.println(request.getParameter("Select_Team"));
//		String s[]=Select_Team.split(",");
//		System.out.println("sadasdsadsa"+s);
//		teamsRepo.deleteAll();
//		for( int i=0;i<s.length;i++)
//		{
//		TeamsEntity tr2=new TeamsEntity();
//		String t[]=s[i].split("-");
//		System.out.println("3"+t[0]);
//		tr2.setFav_team_name(t[0]);
//		tr2.setAbbreviation(t[1]);
//		teamsRepo.save(tr2);
//		}
//
//		
//		List<TeamsEntity> team=new ArrayList<>();
//		team=teamsRepo.findByEmail(userID);
//		request.setAttribute("teams", team);
//		return aa;
//	}
	}

