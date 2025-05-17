package com.example.proyecto2025_BE.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.example.proyecto2025_BE.model.dto.register.UserRequestDTO;
import com.example.proyecto2025_BE.model.dto.register.UserResponseDTO;
import com.example.proyecto2025_BE.service.StatisticService;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API para la gestión de usuarios")
public class UserController {

	private final UserService userService;
	private final StatisticService statisticService;

	@PostMapping
	@Operation(summary = "Registrar usuario", description = "Se registra un nuevo usuario en el sistema",
	responses = {
	    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
	                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "409", description = "El usuario ya existe en el sistema")
	})
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO user) {

    	var userCreated = this.userService.create(UserRequestDTO.toEntity(user));

        return ResponseEntity.ok(UserResponseDTO.fromUser(userCreated));
    }
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener usuario", description = "Obtiene los detalles de un usuario por ID",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<UserResponseDTO> retrieve(@PathVariable Long id) {
		User user = this.userService.retrieve(id);

		return ResponseEntity.ok(UserResponseDTO.fromUser(user));
	}
	
	@PatchMapping("/{id}")
	@Operation(summary = "Actualizar usuario", description = "Actualiza los detalles de un usuario existente",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> properties) {
		var userToUpdate = this.userService.retrieve(id);

    	var updatedUser = this.userService.update(userToUpdate, properties);

		return ResponseEntity.ok(UserResponseDTO.fromUser(updatedUser));
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
	@JsonView(Views.Login.class)
	@Operation(summary = "Login de usuario", description = "Autentica a un usuario basado en su correo y contraseña",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario autenticado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<User> login(@RequestBody @Valid User user) {
		User logged = this.userService.findByEmailAndPassword(user);
        
        return ResponseEntity.ok(logged);
    }

	@GetMapping("/statistics/{usuarioId}")
	@Operation(summary = "Obtener estadísticas de un usuario", description = "Obtiene las estadísticas de un usuario por ID",
			responses = {
					@ApiResponse(responseCode = "200", description = "Estadísticas obtenidas",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatsResponse.class)))
			})
	public ResponseEntity<StatsResponse> getStatistics(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(statisticService.getStats(usuarioId));
	}
	
	@GetMapping("/score/{userId}")
	@JsonView(Views.Score.class)
	@Operation(summary = "Obtener puntaje de un usuario", description = "Obtiene el puntaje de un usuario",
    responses = {
        @ApiResponse(responseCode = "200", description = "Puntaje encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<User> retrieveScore(@PathVariable Long userId) {
		User user = this.userService.retrieve(userId);

		return ResponseEntity.ok(user);
	}

	@GetMapping("/ranking")
	@JsonView(Views.Ranking.class)
	ResponseEntity<Page<User>> getUsersOrderedByScoreDesc(@RequestParam(defaultValue = "0") int page,
														  @RequestParam(defaultValue = "10") int size,
														  @RequestParam(required = false) String[] sort) {
		return ResponseEntity.ok(userService.getUsersOrderedByScoreDesc(page,size,sort));
	}

	@GetMapping("/ranking/{userId}")
	@JsonView(Views.Ranking.class)
	ResponseEntity<Page<User>> getUsersOrderedByScoreFromUser(@PathVariable Long userId,
															  @RequestParam(defaultValue = "0") int page,
															  @RequestParam(defaultValue = "10") int size, @
															  RequestParam(required = false) String[] sort){
		return ResponseEntity.ok(userService.getUsersOrderedByScoreFromUser(userId, page,size,sort));
	}

	@GetMapping("/racha/{userId}")
	@JsonView(Views.Racha.class)
	@Operation(summary = "Obtener racha de un usuario", description = "Obtiene la racha de un usuario",
			responses = {
					@ApiResponse(responseCode = "200", description = "Racha obtenida",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
					@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
			})
	public ResponseEntity<User> getRachaUsuario(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.retrieve(userId));
	}
}