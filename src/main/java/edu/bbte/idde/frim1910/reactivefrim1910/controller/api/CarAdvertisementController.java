package edu.bbte.idde.frim1910.reactivefrim1910.controller.api;

import edu.bbte.idde.frim1910.reactivefrim1910.criteria.AdvertisementCriteria;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.AdvertisementCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.AdvertisementDetailedDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.AdvertisementReducedDto;
import edu.bbte.idde.frim1910.reactivefrim1910.mapper.AdvertisementMapper;
import edu.bbte.idde.frim1910.reactivefrim1910.service.CarAdvertisementService;
import edu.bbte.idde.frim1910.reactivefrim1910.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars/{carId}/advertisements")
@RequiredArgsConstructor
@Log4j2
public class CarAdvertisementController {

    private final CarService carService;
    private final CarAdvertisementService advService;
    private final AdvertisementMapper advMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<AdvertisementReducedDto>>> findAllCarAdvertisements(
            @PathVariable("carId") String carId,
            AdvertisementCriteria advertisementCriteria,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "size", required = false) Integer pageSize
    ) {
        return carService.findById(carId)
            .map(car -> advService.findAllByCarId(
                car.getId(),
                advertisementCriteria,
                PageRequest.of(
                    pageNumber == null || pageNumber < 0 ? 0 : pageNumber,
                    pageSize == null || pageSize < 1 ? 10 : pageSize
                )
            ).map(advMapper::modelToReducedDto).doOnNext(log::info))
            .flatMap(advs -> Mono.just(new ResponseEntity<>(advs, HttpStatus.CREATED)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }

    @PostMapping
    public Mono<ResponseEntity<Mono<AdvertisementDetailedDto>>> createCarAdvertisement(
            @PathVariable("carId") String carId,
            @Valid @RequestBody AdvertisementCreationDto advDto
    ) {
        return carService.findById(carId)
            .map(car -> advService.save(car.getId(), advMapper.creationDtoToModel(advDto, car.getId()))
                .map(advMapper::modelToDetailedDto).doOnNext(log::info))
            .flatMap(adv -> Mono.just(new ResponseEntity<>(adv, HttpStatus.CREATED)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }

    @DeleteMapping("/{advId}")
    public Mono<ResponseEntity<Void>> deleteCarById(
            @PathVariable("carId") String carId,
            @PathVariable("advId") String advId
    ) {
        return carService.findById(carId)
            .flatMap(foundCar -> advService.deleteById(foundCar, advId))
            .flatMap(removedAdv -> {
                log.info("Removed: {}", removedAdv);
                return Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
            })
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnNext(log::info);
    }
}
