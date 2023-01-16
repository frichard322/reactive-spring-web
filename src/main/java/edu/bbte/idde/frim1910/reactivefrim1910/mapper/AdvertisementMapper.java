package edu.bbte.idde.frim1910.reactivefrim1910.mapper;

import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.AdvertisementCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.AdvertisementDetailedDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.AdvertisementReducedDto;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ByteArrayMapper.class})
public interface AdvertisementMapper {
    @Mapping(target = "id", ignore = true)
    Advertisement creationDtoToModel(AdvertisementCreationDto advertisementDto, String carId);

    AdvertisementReducedDto modelToReducedDto(Advertisement model);

    AdvertisementDetailedDto modelToDetailedDto(Advertisement model);
}
