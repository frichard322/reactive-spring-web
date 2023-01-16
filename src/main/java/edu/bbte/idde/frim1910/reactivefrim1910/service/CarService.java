package edu.bbte.idde.frim1910.reactivefrim1910.service;

import edu.bbte.idde.frim1910.reactivefrim1910.criteria.CarCriteria;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Advertisement;
import edu.bbte.idde.frim1910.reactivefrim1910.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CarService {

    private final ReactiveMongoTemplate template;

    public Mono<Car> save(Car car) {
        return template.save(car);
    }

    public Flux<Car> findAll() {
        return template.findAll(Car.class);
    }

    public Flux<Car> findAll(CarCriteria carCriteria, Pageable pageable) {
        return template.find(
            carCriteria.toQuery().with(pageable),
            Car.class
        );
    }

    public Mono<Car> findById(String id) {
        return template.findById(id, Car.class);
    }

    public Mono<Car> deleteById(String id) {
        return template.findAndRemove(
            Query.query(Criteria.where("id").is(id)),
            Car.class
        ).flatMap(
            car -> template.findAllAndRemove(
                Query.query(Criteria.where("carId").is(id)),
                Advertisement.class
            ).then(Mono.just(car))
        );
    }
}
