package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
     
    private String name;
     
    private String location;
     
    private String maxProducts;
    
    @JsonBackReference
    @OneToMany(mappedBy = "shop")
    private Set<Product> products = new HashSet<>();
    
  
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getname() {
        return name;
    }
 
    public void setname(String name) {
        this.name = name;
    }
 
    public String getlocation() {
        return location;
    }
 
    public void setlocation(String location) {
        this.location = location;
    }
 
    public String getmaxProducts() {
        return maxProducts;
    }
 
    public void setmaxProducts(String maxProducts) {
        this.maxProducts = maxProducts;
    }


}
