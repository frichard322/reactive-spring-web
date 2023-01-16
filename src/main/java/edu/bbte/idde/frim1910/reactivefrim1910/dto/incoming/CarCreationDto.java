package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
public class CarCreationDto implements Serializable {
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotEmpty
    private String type;
    @NotNull
    @PositiveOrZero
    private Integer year;
    @NotNull
    @PositiveOrZero
    private Float engine;
}
