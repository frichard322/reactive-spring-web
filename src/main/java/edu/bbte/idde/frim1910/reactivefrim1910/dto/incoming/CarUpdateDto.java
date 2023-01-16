package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
public class CarUpdateDto implements Serializable {
    private String brand;
    private String model;
    private String type;
    @PositiveOrZero
    private Integer year;
    @PositiveOrZero
    private Float engine;
}
