package ru.itmo.soalab2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.soalab2.model.City;
import ru.itmo.soalab2.model.CityFromClient;
import ru.itmo.soalab2.repo.CityRepository;
import ru.itmo.soalab2.services.CityService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private CityService cityService;

    CitiesController(CityRepository cityRepository) {
        this.cityService = new CityService(cityRepository);
    }

    private CityRequestParams getFilterParams(Map<String, String[]> map) {
        return new CityRequestParams(map);
    }

    @GetMapping
    ResponseEntity<?> getAllCities(HttpServletRequest httpServletRequest ) {
        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
        CityRequestParams filterParams = this.getFilterParams(requestParameterMap);
        return cityService.getAllCities(filterParams);
    }

    @PostMapping
    ResponseEntity<?> addCity(@RequestBody CityFromClient newCity) throws Exception {
        return cityService.createCity(newCity);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateCity(@RequestBody CityFromClient updatedCity) throws Exception {
        return cityService.updateCity(updatedCity);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteCity(@PathVariable Long id) {
        return cityService.deleteCity(id);
    }

    @GetMapping(params = "byname")
    ResponseEntity<List<City>> getCitiesByName(@RequestParam String byname) {
        return cityService.getCitiesByName(byname);
    }

    @GetMapping(params = "meters-above-sea-level")
    ResponseEntity<?> getCitiesByMetersAboveSeaLevel(@RequestParam(name= "meters-above-sea-level", defaultValue = "0") int metersAboveSeaLevel) {
        return cityService.filterCitiesByMetersAboveSeaLevel(metersAboveSeaLevel);
    }

    @GetMapping(value = "/meters-above-sea-level")
    ResponseEntity<List<String>> getUniqueMetersAboveSeaLevel() {
        return cityService.getUniqueMetersAboveSeeLevel();
    }

    @GetMapping(value = "/getById/{id1}/{id2}/{id3}")
    ResponseEntity<?> getById(@PathVariable long id1, @PathVariable long id2, @PathVariable long id3) {
        return cityService.getById(id1, id2, id3);
    }

    @GetMapping(value = "/doGenocide/{id}")
    ResponseEntity<?> doGenocide(@PathVariable long id){
        System.out.println("Получили данные на геноцид, производим");
        return cityService.doGenocide(id);
    }
}
