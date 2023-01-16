package edu.bbte.idde.frim1910.reactivefrim1910.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class Advertisement extends BaseEntity {
    private String title;
    private String description;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;
    private Float price;
    private String carId;

    public static Collection<String> getFieldNames() {
        return Arrays.stream(Advertisement.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }
}
