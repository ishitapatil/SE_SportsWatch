package com.SportsWatchProject.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

// This tells Hibernate to make a table out of this class
@Table(name="team")
public class TeamsEntity {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int id;
	private String email;
	private String abbreviation;
	private String fav_team_name;

	private long team_id;
	 public long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(long team_id) {
		this.team_id = team_id;
	}

	public long getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}


	public String getEmail() {
		return email;
	}

	public void setEmail(String user_id) {
		this.email = user_id;
	}



	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getFav_team_name() {
		return fav_team_name;
	}

	public void setFav_team_name(String fav_team_name) {
		this.fav_team_name = fav_team_name;
	}

	
    
    
}

