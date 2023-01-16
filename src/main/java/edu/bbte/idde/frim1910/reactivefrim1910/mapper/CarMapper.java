package edu.bbte.idde.frim1910.reactivefrim1910.mapper;

import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.CarCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.CarUpdateDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.CarDto;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ByteArrayMapper.class})
public interface CarMapper {
    @Mapping(target = "id", ignore = true)
    Car creationDtoToModel(CarCreationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Car updateModelByDto(CarUpdateDto dto, @MappingTarget Car model);

    CarDto modelToDto(Car model);
}
