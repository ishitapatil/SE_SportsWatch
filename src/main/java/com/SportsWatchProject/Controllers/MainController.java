package com.SportsWatchProject.Controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	
	
	//Using PoJo Classes
	@GetMapping("/teams")
	public ModelAndView getTeams() {
		ModelAndView showTeams = new ModelAndView("showTeams");
		showTeams.addObject("name", "Human"); 
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//OverallTeamStandings
		
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("d5800231-5f79-4e80-972f-50e95f:DCtrue@03".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NBATeamStanding> response = restTemplate.exchange(url, HttpMethod.GET, request, NBATeamStanding.class);
		NBATeamStanding ts = response.getBody(); 
        System.out.println(ts.toString());
		//Send the object to view
        showTeams.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());
        
		return showTeams;
	}

	
	 
	
	
	@GetMapping("/team")
	public ModelAndView getTeamInfo(
			@RequestParam("id") String teamID 
			) {
		ModelAndView teamInfo = new ModelAndView("teamInfo");
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/team_gamelogs.json?team=" + teamID;
		String encoding = Base64.getEncoder().encodeToString("d5800231-5f79-4e80-972f-50e95f:DCtrue@03".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
	        System.out.println(root.get("teamgamelogs").get("lastUpdatedOn").asText());
	        System.out.println(root.get("teamgamelogs").get("gamelogs").getNodeType());
	       
	        

	        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");
	        
	        HashMap<String,String> teamDetail = new HashMap<String, String>();
	        JsonNode game2 = gamelogs.get(1).get("team");
	        System.out.println(game2.get("Name"));
	        teamDetail.put("Name",game2.get("Name").asText());
	        teamDetail.put("ID",game2.get("ID").asText());
	        teamDetail.put("Abb",game2.get("Abbreviation").asText());
	        
	        teamInfo.addObject("TeamName", teamDetail);
	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("game");
	        		JsonNode game1 = gamelog.get("stats");
	        		
	        		
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("name",game.get("homeTeam").get("Name").asText());
	        		gameDetail.put("date", game.get("date").asText());
	        		gameDetail.put("time", game.get("time").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Name").asText());
	        		gameDetail.put("Wins", game1.get("Wins").get("#text").asText());
	        		gameDetail.put("Losses", game1.get("Losses").get("#text").asText());
	        		gameDetails.add(gameDetail);
	        		
	        	});
	  
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*------------------------------------------------------*/
		
		String urlsc = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/full_game_schedule.json?team=" + teamID+"&date=since-yesterday";
		RestTemplate restTemplatesc = new RestTemplate();
		ArrayList<HashMap<String, String>> gameSchedules = new ArrayList<HashMap<String, String>>();
		ResponseEntity<String> response1 = restTemplatesc.exchange(urlsc, HttpMethod.GET, request, String.class);
		String strsc = response1.getBody(); 
		try {
			JsonNode root = mapper.readTree(strsc);
			System.out.println("TGis"+strsc);
			 JsonNode gameschedules = root.get("fullgameschedule").get("gameentry");
			  if(gameschedules.isArray()) {
		        	
		        	gameschedules.forEach(gamelog -> {
		        		HashMap<String,String> gameDetail = new HashMap<String, String>();
		        		gameDetail.put("name",gamelog.get("homeTeam").get("Name").asText());
		        		gameDetail.put("date", gamelog.get("date").asText());
		        		gameDetail.put("time", gamelog.get("time").asText());
		        		gameDetail.put("awayTeam", gamelog.get("awayTeam").get("Name").asText());
		        		gameSchedules.add(gameDetail);
		        	});
			  }
		}catch (Exception e) {
			// TODO: handle exception
		}
		/*---------------------------------------------------------------*/
		teamInfo.addObject("gameSchedules",gameSchedules);
		teamInfo.addObject("gameDetails", gameDetails);
		
        
		return teamInfo;
	}


@Controller
public class teamScoresController {
	
		@GetMapping("/teamScores")
	public ModelAndView getTeamScores(
						) {
		ModelAndView teamInfo = new ModelAndView("teamScores");
		
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datetoday = format.format(date);
		//String datetoday = "20181102";
		
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate="+datetoday;
		System.out.println(url);
		String encoding = Base64.getEncoder().encodeToString("d5800231-5f79-4e80-972f-50e95f:DCtrue@03".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		if(str!=null) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
	        System.out.println(root.get("scoreboard").get("lastUpdatedOn").asText());
	        System.out.println(root.get("scoreboard").get("gameScore").getNodeType());
	        JsonNode gameScore = root.get("scoreboard").get("gameScore");
	        
	        if(gameScore.isArray()) {
	        	
	        	gameScore.forEach(gameScores -> {
	        		JsonNode game = gameScores.get("game");
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", game.get("ID").asText());
	        		gameDetail.put("date", game.get("date").asText());
	        		gameDetail.put("time", game.get("time").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
	        		gameDetail.put("homeTeam", game.get("homeTeam").get("Abbreviation").asText());
	        		gameDetail.put("awayScore", gameScores.get("awayScore").asText());
	        		gameDetail.put("homeScore", gameScores.get("homeScore").asText());
	        		gameDetails.add(gameDetail);
	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		teamInfo.addObject("gameDetails", gameDetails);
		
		}
		else {
			System.out.println("ERROOR no gameS");
		}
		return teamInfo;
	}
	
	
}
}
