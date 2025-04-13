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

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")	// TODO: Cambiar luego por los dominios que permitiremos que consuman esta api
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API para la gestión de usuarios")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	@Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema",
	responses = {
	    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
	                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
	    @ApiResponse(responseCode = "409", description = "El usuario ya existe en el sistema")
	})
    public ResponseEntity<User> create(@RequestBody User user) {
    	this.userService.create(user);

        return ResponseEntity.ok(user);
    }
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener usuario", description = "Obtiene los detalles de un usuario por ID",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<User> retrieve(@PathVariable Long id) {
		User user = this.userService.retrieve(id);

		return ResponseEntity.ok(user);
	}
	
	@PutMapping
	@Operation(summary = "Actualizar usuario", description = "Actualiza los detalles de un usuario existente",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<User> update(@RequestBody User user) {
    	this.userService.update(user);

		return ResponseEntity.ok(user);
    }
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
	public ResponseEntity<String> delete(@PathVariable Long id) {
		this.userService.delete(id);

		return ResponseEntity.ok(Strings.EMPTY);
	}
	
	@PostMapping("/login")
	@Operation(summary = "Login de usuario", description = "Autentica a un usuario basado en su correo y contraseña",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario autenticado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<User> login(@RequestBody User user) {
		User logged = this.userService.findByEmailAndPassword(user);
        
        return ResponseEntity.ok(logged);
    }
}
