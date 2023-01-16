package edu.bbte.idde.frim1910.reactivefrim1910.controller.stream;

import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.CarDto;
import edu.bbte.idde.frim1910.reactivefrim1910.mapper.CarMapper;
import edu.bbte.idde.frim1910.reactivefrim1910.service.CarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream/cars")
@Log4j2
public class CarStreamController {

    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    public CarStreamController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CarDto> streamAllCars() {
        return carService.findAll().map(carMapper::modelToDto).doOnNext(log::info);
    }
}
