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
import com.roles.exceptions.StudentNotFoundException;
import com.roles.model.Student;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public User addUser(@RequestBody User user) {
	    return repository.save(user);
	} 
	
//	@PutMapping("{id}")
//	public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
//	    Student studentToUpdate = repository.findById(id).orElseThrow(StudentNotFoundException::new);
//	 
//	        studentToUpdate.setFirstName(student.getFirstName());
//	        studentToUpdate.setLastName(student.getLastName());
//	        studentToUpdate.setYear(student.getYear());
//	 
//	    return repository.save(studentToUpdate);
//	}   
//	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
	    repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.deleteById(id);
	}
}

