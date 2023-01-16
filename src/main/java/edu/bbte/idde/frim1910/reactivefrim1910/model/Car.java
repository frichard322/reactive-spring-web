package edu.bbte.idde.frim1910.reactivefrim1910.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class Car extends BaseEntity {
    private String brand;
    private String model;
    private String type;
    private Integer year;
    private Float engine;

    @DocumentReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<Advertisement> advertisements = new LinkedHashSet<>();

    public static Collection<String> getFieldNames() {
        return Arrays.stream(Car.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }
}
