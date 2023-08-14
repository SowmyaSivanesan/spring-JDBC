package com.jdbc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.dao.SignUpDao;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class SignUpController {

	@Autowired
	SignUpDao dao;

	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody SignUpModel values) {
		return ResponseEntity.ok(dao.createUser(values));
	}
	@GetMapping("/get")
	public ResponseEntity<Response> getUser() {
		return ResponseEntity.ok(dao.getUser());
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Response> updateUser(@RequestParam String email,@RequestParam String s_no){
		return ResponseEntity.ok(dao.updateUser(email,s_no));
	}
	
	@GetMapping("/getOneAPI")
	public ResponseEntity<Response> getOneAPI(@RequestParam String s_no){
		return ResponseEntity.ok(dao.getOneAPI(s_no));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String s_no){
		return ResponseEntity.ok(dao.deleteUser(s_no));
	}
	
	@PostMapping("/scam")
	public ResponseEntity<Response> scam(@RequestParam String s_no){
		return ResponseEntity.ok(dao.scam(s_no));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestParam String email, @RequestParam String pswrd){
		return ResponseEntity.ok(dao.login(email,pswrd));
	}

}
