package com.SportsWatchProject.Repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.SportsWatchProject.Models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByName(String name);

	User findByEmail(String email);

	User findUsersById(long id);

	List<User> findAll();
}


