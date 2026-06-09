package com.matias.taskly.service;

import com.matias.taskly.exceptions.ExistEmailException;
import com.matias.taskly.exceptions.InvalidCredentialsException;
import com.matias.taskly.exceptions.UserNotFoundException;
import com.matias.taskly.model.User;
import com.matias.taskly.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Valida las credenciales del usuario para el login.
     *
     * No genera el JWT acá — esa responsabilidad es del Controller.
     * El Service solo verifica que el usuario exista y que el password coincida.
     *
     * Nota: usamos passwordEncoder.matches() y NO .equals()
     * porque el password en la BD es un hash BCrypt — nunca texto plano.
     */
    public User loginUser(String email, String password) {

        // Si el usuario no existe → lanza UserNotFoundException → GlobalExceptionHandler devuelve 404
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // BCrypt compara el password plano del request contra el hash almacenado en la BD
        // .equals() nunca funcionaría porque el hash cambia con cada encode
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    /**
     * Registra un nuevo usuario aplicando BCrypt a la contraseña.
     */
    public User registerUser(User user) {

        // Si ya existe el email, lanzamos la excepción
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ExistEmailException(user.getEmail());
        }

        // Encriptamos el password plano antes de guardar en la BD
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}