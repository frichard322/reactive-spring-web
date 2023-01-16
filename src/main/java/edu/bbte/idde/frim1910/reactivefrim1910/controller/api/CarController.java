package edu.bbte.idde.frim1910.reactivefrim1910.controller.api;

import edu.bbte.idde.frim1910.reactivefrim1910.criteria.CarCriteria;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.CarCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.CarUpdateDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.CarDto;
import edu.bbte.idde.frim1910.reactivefrim1910.mapper.CarMapper;
import edu.bbte.idde.frim1910.reactivefrim1910.scheduler.Scheduler;
import edu.bbte.idde.frim1910.reactivefrim1910.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Log4j2
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;
    private final Scheduler scheduler;

    @PostConstruct
    public void startStatisticTask() {
        scheduler.initializeTask();
    }

    @PreDestroy
    public void stopStatisticTask() {
        scheduler.stopInitialTask();
    }

    @GetMapping
    public Flux<CarDto> findAllCars(
            CarCriteria carCriteria,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "size", required = false) Integer pageSize
    ) {
        return carService.findAll(
                carCriteria,
                PageRequest.of(
                    pageNumber == null || pageNumber < 0 ? 0 : pageNumber,
                    pageSize == null || pageSize < 1 ? 10 : pageSize
                )
            )
            .map(carMapper::modelToDto)
            .doOnNext(log::info);
    }

    @PostMapping
    public Mono<ResponseEntity<CarDto>> createCar(@Valid @RequestBody CarCreationDto car) {
        return carService.save(carMapper.creationDtoToModel(car))
            .map(model -> new ResponseEntity<>(carMapper.modelToDto(model), HttpStatus.CREATED))
            .doOnNext(log::info);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CarDto>> findCarById(@PathVariable("id") String id) {
        return carService.findById(id)
            .map(model -> new ResponseEntity<>(carMapper.modelToDto(model), HttpStatus.OK))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<CarDto>> updateCar(
            @PathVariable("id") String id,
            @Valid @RequestBody CarUpdateDto car
    ) {
        return carService.findById(id)
            .flatMap(existingModel -> carService.save(carMapper.updateModelByDto(car, existingModel)))
            .map(updatedModel -> new ResponseEntity<>(carMapper.modelToDto(updatedModel), HttpStatus.OK))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCarById(@PathVariable("id") String id) {
        return carService.deleteById(id)
            .flatMap(removedCar -> {
                log.info("Removed: {}", removedCar);
                return Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
            })
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }
}
