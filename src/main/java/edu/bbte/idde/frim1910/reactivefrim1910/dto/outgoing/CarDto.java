package edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarDto extends BaseEntityDto {
    private String brand;
    private String model;
    private String type;
    private Integer year;
    private Float engine;
}

