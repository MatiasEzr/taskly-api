package com.matias.taskly.controller;

import com.matias.taskly.dto.user.AuthResponseDTO;
import com.matias.taskly.dto.user.LoginRequestDTO;
import com.matias.taskly.dto.user.RegisterUserRequestDTO;
import com.matias.taskly.dto.user.UserDTO;
import com.matias.taskly.mapper.UserMapper;
import com.matias.taskly.model.User;
import com.matias.taskly.security.JwtService;
import com.matias.taskly.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserController(UserService userService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    /**
     * Login con email y password.
     *
     * Spring Security NO intercepta este endpoint — lo manejamos manualmente
     * porque nuestra API es REST stateless y necesitamos devolver un JWT.
     *
     * El flujo es:
     * 1. UserService valida las credenciales contra la BD
     * 2. JwtService genera el token firmado con el email del usuario
     * 3. Devolvemos el token al frontend en el body (JSON)
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        // Valida credenciales — lanza excepción si email no existe o password es incorrecto
        User user = userService.loginUser(loginRequestDTO.email(), loginRequestDTO.password());

        // Genera el JWT con el email como subject
        String token = jwtService.generateToken(user.getEmail());

        // Construye la respuesta incluyendo el token
        AuthResponseDTO response = new AuthResponseDTO(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                token
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Registro de nuevo usuario.
     * Devuelve el usuario creado. Si querés auto-login al registrarse,
     * podés agregar el token acá también.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO) {

        User user = userMapper.toEntity(registerUserRequestDTO);
        User saved = userService.registerUser(user);

        // Generamos el token también al registrar — así el usuario queda logueado automáticamente
        String token = jwtService.generateToken(saved.getEmail());

        AuthResponseDTO response = new AuthResponseDTO(
                saved.getId(),
                saved.getNickname(),
                saved.getEmail(),
                token
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    /**
     * Devuelve los datos del usuario autenticado a partir del JWT.
     *
     * Se usa principalmente después del login con Google OAuth2:
     * el frontend recibe solo el token en la URL, llama a este endpoint
     * con ese token en el header Authorization, y obtiene el id, email
     * y nickname para completar el objeto de usuario en memoria.
     *
     * authentication.getName() devuelve el email porque así lo configuramos
     * en JwtService — el subject del JWT es el email del usuario.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getEmail(), user.getNickname()));
    }


}