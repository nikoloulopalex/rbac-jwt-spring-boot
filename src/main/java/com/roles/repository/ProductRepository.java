package com.roles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roles.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductRepository  extends JpaRepository<Product, Integer> {
	  List<Product> findByShopId(int shopId);
	  Optional<Product> findByIdAndShopId(int id, int shopId);
}
