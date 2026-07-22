package ru.shift.userimporter.api.mapper;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shift.userimporter.api.dto.ClientResponseDto;
import ru.shift.userimporter.core.model.Users;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ClientsMapper {

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "birthDate", target = "birthdate")
    @Mapping(source = "createdAt", target = "creationTime")
    @Mapping(source = "updatedAt", target = "updateTime")
    ClientResponseDto toDto(Users entity);

    List<ClientResponseDto> toDtoList(List<Users> entities);
}