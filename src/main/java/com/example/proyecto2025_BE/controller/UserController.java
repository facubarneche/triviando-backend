package com.example.proyecto2025_BE.controller;

import org.apache.logging.log4j.util.Strings;
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

import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")	// TODO: Cambiar luego por los dominios que permitiremos que consuman esta api
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
    	this.userService.create(user);
        
        return ResponseEntity.ok(user);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<User> retrieve(@PathVariable Long id) {
		User user = this.userService.retrieve(id);
		
		return ResponseEntity.ok(user);
	}
	
	@PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
    	this.userService.update(user);

		return ResponseEntity.ok(user);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		this.userService.delete(id);

		return ResponseEntity.ok(Strings.EMPTY);
	}
	
	@PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
		User logged = this.userService.findByEmailAndPassword(user);
        
        return ResponseEntity.ok(logged);
    }
}
