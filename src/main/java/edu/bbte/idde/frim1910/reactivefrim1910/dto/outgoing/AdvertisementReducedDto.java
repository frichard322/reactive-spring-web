package edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdvertisementReducedDto extends BaseEntityDto {
    private String title;
    private Float price;
    private String carId;
}

