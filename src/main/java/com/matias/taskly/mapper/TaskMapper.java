package com.matias.taskly.mapper;

import com.matias.taskly.dto.task.TaskCreateRequestDTO;
import com.matias.taskly.dto.task.TaskResponseDTO;
import com.matias.taskly.dto.task.TaskUpdateRequestDTO;
import com.matias.taskly.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    // Cliente manda TaskCreateRequestDTO → convertís a entidad para guardar en la BD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task createDtoToEntity(TaskCreateRequestDTO dto);


    // Actualiza una entidad Task existente usando los datos del DTO.
    // No crea un nuevo objeto, sino que modifica el mismo (entity).
    // Se usa en updates para no perder datos que no vienen en el DTO.
    void updateEntityFromDto(TaskUpdateRequestDTO dto, @MappingTarget Task entity);



    //Para delete y get no hace falta porque se envia el ID por Path Param


    // Entidad Task → la convertís a ResponseDTO para devolver al cliente
    //Como en el DTO tengo el user_id como Long y en mi entidad como User, MapStruct no sabe como mpear
    //source indica que busque en la entidad User el atributo Id y lo ponga en el userId del DTO
    @Mapping(source = "user.id", target = "userId")
    TaskResponseDTO toResponseDTO(Task task);

    // Lista de entidades → lista de ResponseDTOs
    List<TaskResponseDTO> toResponseDTOList(List<Task> tasks);
}
