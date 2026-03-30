package com.matias.taskly.service;

import com.matias.taskly.exceptions.ExistEmailException;
import com.matias.taskly.exceptions.InvalidCredentialsException;
import com.matias.taskly.exceptions.UserNotFoundException;
import com.matias.taskly.model.User;
import com.matias.taskly.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User loginUser(String email, String password) {
        // Si el usuario no existe → lanza UserNotFoundException → GlobalExceptionHandler devuelve 404
        User user = userRepository.findByEmail(email).orElseThrow( () -> new UserNotFoundException(email));

        // Si la password no coincide → lanza InvalidCredentialsException → GlobalExceptionHandler devuelve 401
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException();
        }

        return user;

    }

    public User registerUser(User user) {
        //Si ya existe el email, mandamos la excepcion
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ExistEmailException(user.getEmail());
        }


        //Devuelve el user con todos los valores seteados
        return userRepository.save(user);
    }




}
