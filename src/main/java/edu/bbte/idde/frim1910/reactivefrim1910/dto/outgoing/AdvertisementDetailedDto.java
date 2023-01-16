package edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdvertisementDetailedDto extends AdvertisementReducedDto {
    private String createdDate;
    private String modifiedDate;
    private String description;
}
