package com.roles.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roles.exceptions.ResourceNotFoundException;
import com.roles.model.Shop;
import com.roles.repository.ShopRepository;

@RestController
@RequestMapping("api/shops")
public class ShopController {

	private final ShopRepository repository;
	
	public ShopController(ShopRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public Iterable<Shop> getShops() {
	    return repository.findAll();
	}   
	
	@GetMapping("{id}")
	public Shop getShop(@PathVariable int id) {
	    return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	} 
	
	@PostMapping
	public Shop addShop(@RequestBody Shop shop) {
	    return repository.save(shop);
	} 
	
	@PutMapping("{id}")
	public Shop udateShop(@PathVariable Integer id, @RequestBody Shop shop) {
	    Shop shopToUpdate = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	 
	    shopToUpdate.setname(shop.getname());
	    shopToUpdate.setlocation(shop.getlocation());
	    shopToUpdate.setmaxProducts(shop.getmaxProducts());
	 
	    return repository.save(shopToUpdate);
	}   
	
	@DeleteMapping("/{id}")
	public void deleteShop(@PathVariable int id) {
	    repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.deleteById(id);
	}
	
}
