package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Data
public class AdvertisementUpdateDto implements Serializable {
    private String title;
    private String description;
    private Date modifiedDate = new Date();
    @PositiveOrZero
    private Float price;
}
