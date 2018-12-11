package com.SportsWatchProject.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notifications {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private long userId;

	private int updateId;

	private long team_id;
	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(long team_id) {
		this.team_id = team_id;
	}

	public long getWL_date() {
		return WL_date;
	}

	public void setWL_date(long wL_date) {
		WL_date = wL_date;
	}

	private long WL_date;
	private String message;

	private boolean isVisited;

	private long toBeNotified;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return updateId;
	}

	public void setPostId(int postId) {
		this.updateId = postId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean visited) {
		isVisited = visited;
	}

	public long getToBeNotified() {
		return toBeNotified;
	}

	public void setToBeNotified(long toBeNotified) {
		this.toBeNotified = toBeNotified;
	}
}
