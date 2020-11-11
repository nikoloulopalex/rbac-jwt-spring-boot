package com.example.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.StudentNotFoundException;
import com.example.model.Shop;
import com.example.repository.ShopRepository;

@RestController
@RequestMapping("api/shops")
public class ShopController {

	private final ShopRepository repository;
	
	public ShopController(ShopRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public Iterable<Shop> getStudents() {
	    return repository.findAll();
	}   
	
	@GetMapping("{id}")
	public Shop getShop(@PathVariable int id) {
	    return repository.findById(id).orElseThrow(StudentNotFoundException::new);
	} 
	
	@PostMapping
	public Shop addShop(@RequestBody Shop shop) {
	    return repository.save(shop);
	} 
	
//	@PutMapping("{id}")
//	public Shop updateStudent(@PathVariable Long id, @RequestBody Shop shop) {
//	    Student studentToUpdate = repository.findById(id).orElseThrow(StudentNotFoundException::new);
//	 
//	        studentToUpdate.setFirstName(shop.getFirstName());
//	        studentToUpdate.setLastName(shop.getLastName());
//	        studentToUpdate.setYear(shop.getYear());
//	 
//	    return repository.save(studentToUpdate);
//	}   
	
	@DeleteMapping("/{id}")
	public void deleteShop(@PathVariable int id) {
	    repository.findById(id).orElseThrow(StudentNotFoundException::new);
	    repository.deleteById(id);
	}
	
}
