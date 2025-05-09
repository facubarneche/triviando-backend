package com.example.proyecto2025_BE.controller;

import java.util.Map;

import com.example.proyecto2025_BE.model.dto.Racha;
import com.example.proyecto2025_BE.model.dto.UserRankingDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.example.proyecto2025_BE.model.dto.login.LoginRequestDTO;
import com.example.proyecto2025_BE.model.dto.login.LoginResponseDTO;
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
	@Operation(summary = "Login de usuario", description = "Autentica a un usuario basado en su correo y contraseña",
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuario autenticado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginInfo) {
		User logged = this.userService.findByEmailAndPassword(loginInfo);
        
        return ResponseEntity.ok(LoginResponseDTO.fromEntity(logged));
    }

	@GetMapping("/{usuarioId}/statistics")
	@Operation(summary = "Obtener estadísticas de un usuario", description = "Obtiene las estadísticas de un usuario por ID",
			responses = {
					@ApiResponse(responseCode = "200", description = "Estadísticas obtenidas",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatsResponse.class)))
			})
	public ResponseEntity<StatsResponse> getStatistics(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(statisticService.getStats(usuarioId));
	}
	
	@GetMapping("/{userId}/score")
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
	ResponseEntity<Page<UserRankingDTO>> getUsersOrderedByScoreDesc(Pageable pageable) {
		return ResponseEntity.ok(userService.getUsersOrderedByScoreDesc(pageable));
	}

	@GetMapping("/ranking/{userId}")
	ResponseEntity<Page<UserRankingDTO>> getUsersOrderedByScoreFromUser(@PathVariable Long userId, Pageable pageable) {
		return ResponseEntity.ok(userService.getUsersOrderedByScoreFromUser(userId, pageable));
	}

	@GetMapping("/racha/{userId}")
	@Operation(summary = "Obtener racha de un usuario", description = "Obtiene la racha de un usuario",
			responses = {
					@ApiResponse(responseCode = "200", description = "Racha obtenida",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
					@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)
			})
	public ResponseEntity<Racha> getRachaUsuario(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getRachaUsuario(userId));
	}
}
