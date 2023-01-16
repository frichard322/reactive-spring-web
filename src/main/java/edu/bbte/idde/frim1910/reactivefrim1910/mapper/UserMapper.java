package edu.bbte.idde.frim1910.reactivefrim1910.mapper;

import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.UserCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.UserUpdateDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.UserDto;
import edu.bbte.idde.frim1910.reactivefrim1910.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ByteArrayMapper.class})
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User creationDtoToModel(UserCreationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateModelByDto(UserUpdateDto dto, @MappingTarget User model);

    UserDto modelToDto(User model);
}
