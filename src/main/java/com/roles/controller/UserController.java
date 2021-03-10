package com.roles.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roles.exceptions.ResourceNotFoundException;
import com.roles.model.User;
import com.roles.repository.UserRepository;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserRepository repository;
	
	public UserController(UserRepository repository) {
	    this.repository = repository;
	}
	
	@GetMapping
	public Iterable<User> getUsers() {
	    return repository.findAll();
	}   
	
	@GetMapping("/admins")
	public Iterable<User> getAdmins() {
	    return repository.findAll();
	}   
	
	@GetMapping("{id}")
	public User getUser(@PathVariable Long id) {
	    return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	} 
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public User addUser(@RequestBody User user) {
	    return repository.save(user);
	} 
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN') || hasRole('MODERATOR')" )
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
	    User userToUpdate = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	 
	    userToUpdate.setUsername(user.getUsername());
	    userToUpdate.setEmail(user.getEmail());
	    userToUpdate.setPassword(user.getPassword());
	 
	    return repository.save(userToUpdate);
	}   
	
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
	    repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.deleteById(id);
	}
}

