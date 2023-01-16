package edu.bbte.idde.frim1910.reactivefrim1910.criteria;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@AllArgsConstructor
@Data
public class CarCriteria {
    private String brand;
    private String model;
    private String type;
    private Integer yearMin;
    private Integer yearMax;
    private Float engineMin;
    private Float engineMax;
    private String sortType;
    private String sortBy;

    public void checkQueryParams() {
        this.brand = brand == null ? ".*" : brand;
        this.model = model == null ? ".*" : model;
        this.type = type == null ? ".*" : type;
        this.yearMin = yearMin == null ? Integer.valueOf(Integer.MIN_VALUE) : yearMin;
        this.yearMax = yearMax == null ? Integer.valueOf(Integer.MAX_VALUE) : yearMax;
        this.engineMin = engineMin == null ? Float.valueOf(Float.MIN_VALUE) : engineMin;
        this.engineMax = engineMax == null ? Float.valueOf(Float.MAX_VALUE) : engineMax;
    }

    public Query toQuery() {
        checkQueryParams();
        return new Query()
            .with(Sort.by(
                "ASC".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC,
                Car.getFieldNames().contains(sortBy) ? sortBy : "year")
            )
            .addCriteria(Criteria.where("brand").regex(brand))
            .addCriteria(Criteria.where("model").regex(model))
            .addCriteria(Criteria.where("type").regex(type))
            .addCriteria(Criteria.where("year").gte(yearMin).lte(yearMax))
            .addCriteria(Criteria.where("engine").gte(engineMin).lte(engineMax));
    }
}
