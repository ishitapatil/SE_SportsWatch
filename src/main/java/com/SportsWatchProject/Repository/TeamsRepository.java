package com.SportsWatchProject.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.SportsWatchProject.Models.TeamsEntity;
import com.SportsWatchProject.Models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TeamsRepository extends CrudRepository<TeamsEntity, Integer> {
	
	
public TeamsEntity findById(int id); 
public List<TeamsEntity> findByEmail(String email);
public List<TeamsEntity> findAll();


/*
@Override
void delete(User entity);*/
}
