package edu.bbte.idde.frim1910.reactivefrim1910.service;

import edu.bbte.idde.frim1910.reactivefrim1910.criteria.AdvertisementCriteria;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Advertisement;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CarAdvertisementService {

    private final ReactiveMongoTemplate template;

    public Mono<Advertisement> save(String carId, Advertisement advertisement) {
        return template.save(advertisement)
            .flatMap(
                savedAdv -> template.findAndModify(
                    Query.query(Criteria.where("id").is(carId)),
                    new Update().push("advertisements", savedAdv),
                    Car.class
                ).thenReturn(savedAdv)
            );
    }

    public Flux<Advertisement> findAllByCarId(String carId, AdvertisementCriteria advCriteria, Pageable pageable) {
        return template.find(
            advCriteria.toQuery().addCriteria(Criteria.where("carId").is(carId)).with(pageable),
            Advertisement.class
        );
    }

    public Mono<Advertisement> deleteById(Car car, String advId) {
        return template.findAndRemove(
            Query.query(Criteria.where("id").is(advId)),
            Advertisement.class
        ).flatMap(
            removedAdv -> template.findAndModify(
                Query.query(Criteria.where("id").is(car.getId())),
                new Update().pull("advertisements", removedAdv),
                Car.class
            ).thenReturn(removedAdv)
        );
    }
}
