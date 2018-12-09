package com.SportsWatchProject.Controllers;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


class team{
	@Override
	public String toString() {
		return "team [ID=" + ID + ", City=" + City + ", Name=" + Name + ", Abbreviation=" + Abbreviation + "]";
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAbbreviation() {
		return Abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		Abbreviation = abbreviation;
	}
	@JsonProperty("ID")
	String ID;
	@JsonProperty("City")
	String City;
	@JsonProperty("Name")
	String Name;
	@JsonProperty("Abbreviation")
	String Abbreviation;
}


class teamstandingsentry{
	team team;
	@Override
	public String toString() {
		return "teamstandingsentry [team=" + team + ", rank=" + rank + "]";
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public team getTeam() {
		return team;
	}
	public void setTeam(team team) {
		this.team = team;
	}
	Long rank;	
}


class overallteamstandings{
	String lastUpdatedOn;
	@Override
	public String toString() {
		return "overallteamstandings [lastUpdatedOn=" + lastUpdatedOn + ", teamstandingsentries=" + teamstandingsentries
				+ "]";
	}
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	@JsonProperty("teamstandingsentry")
	ArrayList<teamstandingsentry> teamstandingsentries;
	public ArrayList<teamstandingsentry> getTeamstandingsentries() {
		return teamstandingsentries;
	}
	public void setTeamstandingsentries(ArrayList<teamstandingsentry> teamstandingsentries) {
		this.teamstandingsentries = teamstandingsentries;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)

public class NBATeamStanding {
	overallteamstandings overallteamstandings;

	@Override
	public String toString() {
		return "NBATeamStanding [overallteamstandings=" + overallteamstandings + "]";
	}

	public overallteamstandings getOverallteamstandings() {
		return overallteamstandings;
	}

	public void setOverallteamstandings(overallteamstandings overallteamstandings) {
		this.overallteamstandings = overallteamstandings;
	}
	
}
