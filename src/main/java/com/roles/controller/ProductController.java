package com.roles.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roles.exceptions.ResourceNotFoundException;
import com.roles.model.Product;
import com.roles.repository.ProductRepository;
import com.roles.repository.ShopRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping
public class ProductController {
private final ProductRepository repository;
public ProductController(ProductRepository repository, ShopRepository shopRepository) {
		this.repository = repository;
	}
	
	@GetMapping("api/products")
	public Iterable<Product> getProducts() {
	    return repository.findAll();
	}   
	
	  @GetMapping("api/shops/{shopId}/products")
	    public List<Product> getAllProductsbyShopId(
	            @PathVariable(value = "shopId") int shopId) {
	        return repository.findByShopId(shopId);
	    }

	  
	
	@GetMapping("api/products/{id}")
	public Product getProduct(@PathVariable int id) {
	    return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	} 
	
	@PostMapping("api/products")
	public Product addProduct(@RequestBody Product product) {
	    return repository.save(product);
	} 
	
	@PostMapping("api/products/new")
	public ResponseEntity<String> addProductNew( @RequestParam("fileImage") MultipartFile multipartFile) {
		System.out.println(multipartFile);
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
	    String uploadDir = "media";
	    Path uploadPath = Paths.get(uploadDir);
	    if(!Files.exists(uploadPath)) {
	    	try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	   try( InputStream inputStream = multipartFile.getInputStream()){
	    Path filePath = uploadPath.resolve(fileName);
	    Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
	   }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
	} 
	
	


	@PutMapping("api/products/{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
	    Product productToUpdate = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	 
	    productToUpdate.setName(product.getName());
	    productToUpdate.setPrice(product.getPrice());
	    productToUpdate.setSalesPrice(product.getSalesPrice());
	    productToUpdate.setDescription(product.getDescription());
	    productToUpdate.setShop(product.getShop());
	 
	    return repository.save(productToUpdate);
	}   
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
	    repository.findById(id).orElseThrow(ResourceNotFoundException::new);
	    repository.deleteById(id);
	}
	
}
