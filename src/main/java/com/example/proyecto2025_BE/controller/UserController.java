package com.example.proyecto2025_BE.controller;

import com.example.proyecto2025_BE.model.dto.Login;
import com.example.proyecto2025_BE.security.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.Ranking;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.views.Views;
import com.example.proyecto2025_BE.views.users.UserResponse4XX;
import com.example.proyecto2025_BE.views.users.get.GetUserResponse;
import com.example.proyecto2025_BE.views.users.login.request.UserLoginRequest;
import com.example.proyecto2025_BE.views.users.login.response.UserLoginResponse200;
import com.example.proyecto2025_BE.views.users.racha.UserRachaResponse;
import com.example.proyecto2025_BE.views.users.ranking.PageUserRankingResponse;
import com.example.proyecto2025_BE.views.users.register.request.UserRegisterRequest;
import com.example.proyecto2025_BE.views.users.register.response.UserRegisterResponse;
import com.example.proyecto2025_BE.views.users.score.UserScoreResponse;
import com.example.proyecto2025_BE.views.users.update.request.UpdateUserRequest;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API para la gestión de usuarios")
public class UserController {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtils;
	private final UserService userService;

	@PostMapping
	@JsonView(Views.Register.class)
	@Operation(summary = "Registrar usuario", description = "Se registra un nuevo usuario en el sistema")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Datos del usuario para el registro",
			required = true,
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = UserRegisterRequest.class)))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "409", description = "El usuario ya existe en el sistema",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public Login create(@RequestBody
									   @JsonView(Views.RegisterRequest.class)
									   @Validated(Views.RegisterRequest.class) User user) {

		this.userService.validateUsernameEmail(user);

		var encodedPass = encoder.encode(user.getPassword());
		user.setPassword(encodedPass);

		var userCreated = this.userService.create(user);

		var token = jwtUtils.generateToken(userCreated.getUsername());
		return Login.builder()
				.token(token)
				.username(userCreated.getUsername())
				.fullname(userCreated.getFullName())
				.id(userCreated.getId())
				.build();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.GetUser.class)
	@Operation(summary = "Obtener usuario", description = "Obtiene los detalles de un usuario por ID")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
    public ResponseEntity<User> retrieve(@PathVariable Long id) {
		User user = this.userService.retrieve(id);

		return ResponseEntity.ok(user);
	}
	
	@PutMapping
	@JsonView(Views.UpdateUser.class)
	@Operation(summary = "Actualizar usuario", description = "Actualiza los detalles de un usuario existente")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Datos del usuario para la actualización",
			required = true,
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = UpdateUserRequest.class)))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
    public ResponseEntity<Void> update(@RequestBody User user) {
    	this.userService.update(user);
    	
		return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND)})
	public ResponseEntity<String> delete(@PathVariable Long id) {
		this.userService.delete(id);
		return ResponseEntity.ok(Strings.EMPTY);
	}
	
	@PostMapping("/login")
	@Operation(summary = "Login de usuario", description = "Autentica a un usuario basado en su correo y contraseña")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Credenciales del usuario para el login", required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = UserLoginRequest.class)))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario autenticado",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public Login login(@RequestBody @Valid User user) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getUsername(),
						user.getPassword()
				)
		);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		var token = jwtUtils.generateToken(userDetails.getUsername());
		var fetchedUser = this.userService.findByUsername(userDetails.getUsername());
		return Login.builder()
				.token(token)
				.username(userDetails.getUsername())
				.fullname(fetchedUser.getFullName())
				.id(fetchedUser.getId())
				.build();
	}

	@GetMapping("/statistics/{usuarioId}")
	@Operation(summary = "Obtener estadísticas de un usuario", description = "Obtiene las estadísticas de un usuario por ID")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Estadísticas obtenidas",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatsResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public ResponseEntity<StatsResponse> getStatistics(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(userService.getStatisticsFromUser(usuarioId));
	}
	
	@GetMapping("/score/{userId}")
	@JsonView(Views.Score.class)
	@Operation(summary = "Obtener puntaje de un usuario", description = "Obtiene el puntaje de un usuario")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Puntaje encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserScoreResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
    public ResponseEntity<User> retrieveScore(@PathVariable Long userId) {
		User user = this.userService.retrieve(userId);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/ranking")
	@JsonView(Views.Ranking.class)
	@Operation(summary = "Obtener ranking de usuarios", description = "Obtiene el ranking de usuarios")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Ranking obtenido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageUserRankingResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	ResponseEntity<Page<User>> getUsersOrderedByScoreDesc(@RequestParam(defaultValue = "0") int page,
														  @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(userService.getUsersOrderedByScoreDesc(page,size));
	}

	@GetMapping("/ranking-semanal")
	@Operation(summary = "Ranking de usuarios", description = "Devuelve el ranking semanal de usuarios")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Ranking semanal obtenido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageUserRankingResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public ResponseEntity<Page<Ranking>> getWeeklyRanking(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(userService.getWeeklyRanking(page, size));
	}

	@GetMapping("/ranking-semanal/{userId}")
	@Operation(summary = "Ranking de usuarios", description = "Devuelve el ranking semanal de usuarios")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Ranking semanal obtenido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageUserRankingResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public ResponseEntity<Page<Ranking>> getWeeklyRankingByUser(
			@PathVariable Long userId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(userService.getWeeklyRanking(userId,page, size));
	}

	@GetMapping("/ranking/{userId}")
	@JsonView(Views.Ranking.class)
	@Operation(summary = "Obtener ranking del usuario", description = "Obtiene el ranking del usuario")
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Ranking obtenido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageUserRankingResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	ResponseEntity<Page<User>> getUsersOrderedByScoreFromUser(@PathVariable Long userId,
															  @RequestParam(defaultValue = "0") int page,
															  @RequestParam(defaultValue = "10") int size, @
															  RequestParam(required = false) String[] sort){
		return ResponseEntity.ok(userService.getUsersOrderedByScoreFromUser(userId, page,size));
	}

	@GetMapping("/racha/{userId}")
	@JsonView(Views.Racha.class)
	@Operation(summary = "Obtener racha de un usuario", description = "Obtiene la racha de un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Racha obtenida",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRachaResponse.class))),
			@ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse4XX.class)))})
	public ResponseEntity<User> getRachaUsuario(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.retrieve(userId));
	}
}