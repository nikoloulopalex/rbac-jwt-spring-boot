package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Product;

import java.util.List;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
import java.util.Optional;


public interface ProductRepository  extends JpaRepository<Product, Integer> {
	  List<Product> findByShopId(int shopId);
	  Optional<Product> findByIdAndShopId(int id, int shopId);
}
