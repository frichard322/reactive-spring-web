package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Data
public class AdvertisementCreationDto implements Serializable {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private Date createdDate = new Date();
    @NotNull
    private Date modifiedDate = new Date();
    @NotNull
    @PositiveOrZero
    private Float price;
}
