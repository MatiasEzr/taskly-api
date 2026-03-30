package com.matias.taskly.mapper;

import com.matias.taskly.dto.user.AuthResponseDTO;
import com.matias.taskly.dto.user.RegisterUserRequestDTO;
import com.matias.taskly.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Cliente manda RegisterUserRequestDTO → convertís a entidad para guardar en la BD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(RegisterUserRequestDTO dto);

    // Entidad User → la convertís a AuthResponseDTO para devolver al cliente
    AuthResponseDTO toResponseDTO(User user);

    // Lista de entidades → lista de AuthResponseDTOs
    List<AuthResponseDTO> toResponseDTOList(List<User> users);
}