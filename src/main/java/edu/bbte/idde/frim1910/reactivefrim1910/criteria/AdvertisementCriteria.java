package edu.bbte.idde.frim1910.reactivefrim1910.criteria;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdvertisementCriteria {
    private String title;
    private String description;
    private Date createdDateBefore;
    private Date createdDateAfter;
    private Date modifiedDateBefore;
    private Date modifiedDateAfter;
    private Float priceMin;
    private Float priceMax;
    private String sortType;
    private String sortBy;

    public void checkDate() {
        long min = 0L;
        long max = Long.MAX_VALUE;
        this.createdDateBefore = createdDateBefore == null ? new Date(min) : createdDateBefore;
        this.createdDateAfter = createdDateAfter == null ? new Date(max) : createdDateAfter;
        this.modifiedDateBefore = modifiedDateBefore == null ? new Date(min) : modifiedDateBefore;
        this.modifiedDateAfter = modifiedDateAfter == null ? new Date(max) : modifiedDateAfter;
    }

    public void checkQueryParams() {
        this.title = title == null ? ".*" : title;
        this.description = description == null ? ".*" : description;
        this.priceMin = priceMin == null ? Float.valueOf(Float.MIN_VALUE) : priceMin;
        this.priceMax = priceMax == null ? Float.valueOf(Float.MAX_VALUE) : priceMax;
    }

    public Query toQuery() {
        checkDate();
        checkQueryParams();
        return new Query()
            .with(Sort.by(
                "ASC".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC,
                Advertisement.getFieldNames().contains(sortBy) ? sortBy : "createdDate")
            )
            .addCriteria(Criteria.where("title").regex(title))
            .addCriteria(Criteria.where("description").regex(description))
            .addCriteria(Criteria.where("price").gte(priceMin).lte(priceMax))
            .addCriteria(Criteria.where("createdDate").gte(createdDateBefore).lte(createdDateAfter))
            .addCriteria(Criteria.where("modifiedDate").gte(modifiedDateBefore).lte(modifiedDateAfter));
    }
}
