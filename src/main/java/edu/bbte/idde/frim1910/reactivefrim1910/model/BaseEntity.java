package edu.bbte.idde.frim1910.reactivefrim1910.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable {
    @Id
    protected String id;
}
