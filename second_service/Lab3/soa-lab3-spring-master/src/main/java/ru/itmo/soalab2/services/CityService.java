package ru.itmo.soalab2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import ru.itmo.soalab2.controller.CityRequestParams;
import ru.itmo.soalab2.model.*;
import ru.itmo.soalab2.repo.CityFilterSpecification;
import ru.itmo.soalab2.repo.CityRepository;
import ru.itmo.soalab2.validators.CityValidator;
import ru.itmo.soalab2.validators.ValidateFieldsException;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CityService {
    private final CityValidator cityValidator;
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityValidator = new CityValidator();
        this.cityRepository = cityRepository;
    }

    public ResponseEntity<?> createCity(CityFromClient newCity) throws Exception {
        try {
            City validCity = cityValidator.validate(newCity);
            validCity.setCreationDate(ZonedDateTime.now());
            Long id = cityRepository.save(validCity).getId();
            return ResponseEntity.status(201).body(new OperationResponse(id, "City created successfully"));
        } catch (ValidateFieldsException ex) {
            return sendErrorList(ex);
        }
    }

    public ResponseEntity<?> updateCity(CityFromClient updatedCity) throws Exception {
        try {
            City validCity = cityValidator.validate(updatedCity);
            boolean isFound = cityRepository.existsById(updatedCity.getId());
            if (isFound) {
                validCity.setCreationDate(cityRepository.findCreationDateByCityId(updatedCity.getId()));
                cityRepository.save(validCity);
                return ResponseEntity.status(200).body(new OperationResponse(updatedCity.getId(), "City updated successfully"));
            } else {
                return ResponseEntity.status(404).body(new OperationResponse(updatedCity.getId(), "Cannot find city with id " + updatedCity.getId()));
            }
        } catch (ValidateFieldsException ex) {
            return sendErrorList(ex);
        }
    }

    public ResponseEntity<?> deleteCity(Long id) {
        boolean isFound = cityRepository.existsById(id);
        cityRepository.deleteById(id);
        if (isFound) {
            return ResponseEntity.status(200).body(new OperationResponse(id, "City deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new OperationResponse(id, "Cannot find city with id " + id));
        }
    }

    public ResponseEntity<?> getAllCities(CityRequestParams filterParams) {
        CityFilterSpecification spec = new CityFilterSpecification(filterParams);
        try {
            Sort currentSorting = filterParams.parseSorting();
            Pageable sortedBy = PageRequest.of(filterParams.page, filterParams.size, currentSorting);
            Page<City> res = cityRepository.findAll(spec, sortedBy);
            long count = cityRepository.count(spec);
            PaginationResult r = new PaginationResult(filterParams.size, filterParams.page, count, res.getContent());
            return ResponseEntity.status(200).body(r);
        } catch (ParseException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    public ResponseEntity<List<City>> filterCitiesByMetersAboveSeaLevel(int metersAboveSeaLevel) {
        List<City> cities = cityRepository.findByMetersAboveSeaLevelGreaterThan(metersAboveSeaLevel);
        if (cities.isEmpty()) {
            return ResponseEntity.status(404).body(cities);
        } else {
            return ResponseEntity.status(200).body(cities);
        }
    }

    private ResponseEntity<?> sendErrorList(ValidateFieldsException ex) {
        return ResponseEntity.status(400).body(ex.getErrorMsg());
    }

    public ResponseEntity<List<City>> getCitiesByName(String name) {
        List<City> cities = cityRepository.findByNameContaining(name);
        if (cities.isEmpty()) {
            return ResponseEntity.status(404).body(cities);
        } else {
            return ResponseEntity.status(200).body(cities);
        }
    }

    public ResponseEntity<List<String>> getUniqueMetersAboveSeeLevel() {
        List<String> meters = cityRepository.findDistinctMetersAboveSeaLevel();
        if (meters.isEmpty()) {
            return ResponseEntity.status(404).body(meters);
        } else {
            return ResponseEntity.status(200).body(meters);
        }
    }

    public ResponseEntity<SumPopulation> getById(long id1, long id2, long id3) {
        City c1 = cityRepository.findById(id1);
        City c2 = cityRepository.findById(id2);
        City c3 = cityRepository.findById(id3);
        long l = c1.getPopulation() + c2.getPopulation() + c3.getPopulation();
        SumPopulation sumPopulation = new SumPopulation(l);
        return ResponseEntity.status(200).body(sumPopulation);
    }

    public ResponseEntity<City> doGenocide(long id){

        City min = cityRepository.findCityWithMinPop();
        City cur = cityRepository.findById(id);
        cityRepository.ch2(cur.getPopulation(),min.getId());
        cityRepository.ch1(id);
        long i = min.getId();
        City n = cityRepository.findById(i);
        return ResponseEntity.status(200).body(n);
    }
}
