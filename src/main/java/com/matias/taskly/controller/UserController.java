package com.matias.taskly.controller;

import com.matias.taskly.dto.user.AuthResponseDTO;
import com.matias.taskly.dto.user.LoginRequestDTO;
import com.matias.taskly.dto.user.RegisterUserRequestDTO;
import com.matias.taskly.mapper.UserMapper;
import com.matias.taskly.model.User;
import com.matias.taskly.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        User userLogin= userService.loginUser(loginRequestDTO.email(), loginRequestDTO.password());

        //Devolvemos status ok y el AuthResponseDTO
        return ResponseEntity.ok(userMapper.toResponseDTO(userLogin));

    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        // Mapper convierte DTO a entidad
        User user = userMapper.toEntity(registerUserRequestDTO);

        // Service recibe la entidad, no sabe nada de DTOs
        User saved = userService.registerUser(user);

        // Mapper convierte la entidad guardada a AuthResponseDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(saved));
    }

}
