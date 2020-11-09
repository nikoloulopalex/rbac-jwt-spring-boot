package com.example.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.StudentNotFoundException;
import com.example.model.Product;
import com.example.model.Shop;
import com.example.repository.ProductRepository;
import com.example.repository.ShopRepository;
import java.util.List;

@RestController
@RequestMapping
public class ProductController {
private final ProductRepository repository;
private final ShopRepository shopRepository;
	
	public ProductController(ProductRepository repository, ShopRepository shopRepository) {
		this.repository = repository;
		this.shopRepository = shopRepository;
	}
	
	@GetMapping("/products")
	public Iterable<Product> getProducts() {
	    return repository.findAll();
	}   
	
	  @GetMapping("/shops/{shopId}/products")
	    public List<Product> getAllProductsbyShopId(
	            @PathVariable(value = "shopId") int shopId) {
	        return repository.findByShopId(shopId);
	    }

	  
	
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable int id) {
	    return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	} 
	
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
	    return repository.save(product);
	} 
//	
//
//
//    @PostMapping("/shops/{shopId}/products")
//    public ResponseEntity<Product> createProduct(
//            @PathVariable(value = "shopId") int shopId,
//            @Valid @RequestBody Product productRequest
//    ) {
//    	return shopRepository.findById(shopId)
//    			.map(shop->{
//    				productRequest.setShop(shop);
//    				return repository.save(productRequest);
//    	})
//    			.map(product-> ResponseEntity.created(resourceUri(product.getId()))
//                        .body(product)
//                        ).orElseThrow(ResourceNotFoundException::new);
//    }
//    

//private URI resourceUri(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody Product Product) {
	    Product productToUpdate = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	 
	    productToUpdate.setName(Product.getName());
	    productToUpdate.setPrice(Product.getPrice());
	    productToUpdate.setsalesPrice(Product.getsalesPrice());
	    productToUpdate.setDescription(Product.getDescription());
	    productToUpdate.setShop(Product.getShop());
	 
	    return repository.save(productToUpdate);
	}   
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
	    repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.deleteById(id);
	}
	
}
