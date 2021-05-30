package com.crudapi.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudapi.jwt.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
