package com.crudapi.jwt.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crudapi.jwt.entity.Student;
import com.crudapi.jwt.exception.StudentNotFoundException;
import com.crudapi.jwt.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepo;
	
	/*@GetMapping("/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
        return "redirect:/";  
     } */

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@GetMapping("/students")
	public List<Student> retrieveAllStudents(){
		return studentRepo.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Optional<Student> retrieveStudent(@PathVariable int id){
		Optional<Student> student = studentRepo.findById(id);
		
		if(!student.isPresent()) {
			throw new StudentNotFoundException("id-"+id);
		}
		
		return student;
	}
	
	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable int id) {
		studentRepo.deleteById(id);
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedStudent=studentRepo.save(student);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable int id){
		Optional<Student> retrievedStudent = studentRepo.findById(id);
		
		if(!retrievedStudent.isPresent()) {
			throw new StudentNotFoundException("id-"+id);
		}
		student.setId(id);
		
		studentRepo.save(student);
		
		return ResponseEntity.noContent().build();
	}
}
