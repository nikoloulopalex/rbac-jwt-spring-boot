package com.roles.repository;

import org.springframework.data.repository.CrudRepository;

import com.roles.model.Student;
 
public interface StudentRepository extends CrudRepository<Student, Long> {
 
}