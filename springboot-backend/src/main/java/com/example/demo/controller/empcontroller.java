package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.resourcenotfound;
import com.example.demo.model.employee;
import com.example.demo.repository.employeeRepo;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class empcontroller {
	
	@Autowired
	private employeeRepo emprepo;
	
	//get all employees
	@GetMapping("/employees")
	public List<employee> getAllemployeeRepo()
	{
		return emprepo.findAll();
	}
    //create employee
	@PostMapping("/employees")
	public employee createEmployee(@RequestBody employee employee)
	{
		System.out.println(employee.getName());
		return emprepo.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<employee> getEmployeeById(@PathVariable Long id)
	{
		employee emp= emprepo.findById(id)
				.orElseThrow(() -> new resourcenotfound("Employee with id not found: "+id));
		return ResponseEntity.ok(emp);
	}
	//put
	@PutMapping("/employees/{id}")
	public ResponseEntity<employee> updateemployee(@PathVariable Long id,@RequestBody employee employee){		
		employee emp= emprepo.findById(id)
				.orElseThrow(() -> new resourcenotfound("Employee with id not found: "+id));
		emp.setName(employee.getName());
		employee updatedEmployee =emprepo.save(emp);
		return ResponseEntity.ok(updatedEmployee);
		
		
	}
	//delete
	@DeleteMapping("/employees/{id}")
	public ResponseEntity< Map<String,Boolean>> deleteemp(@PathVariable Long id){
		employee emp=emprepo.findById(id)
				.orElseThrow(()->new resourcenotfound("Employee not exist with id:"+id));
		emprepo.delete(emp);
		Map<String,Boolean> response =new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
}
