package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.*;
import ru.itmo.soalab2.model.Error;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CityValidator {
    private final HumanValidator humanValidator;
    private final CoordinatesValidator coordinatesValidator;

    public CityValidator() {
        humanValidator = new HumanValidator();
        coordinatesValidator = new CoordinatesValidator();
    }

    public City validate(CityFromClient city) throws IllegalAccessException, ValidateFieldsException {

        List<Error> errorList = new ArrayList<>();
        City validatedCity = new City();

        try {
            if (city.getCoordinates() != null) validatedCity.setCoordinates(coordinatesValidator.validate(city.getCoordinates()));
        } catch (ValidateFieldsException ex) {
            errorList.addAll(ex.getErrorMsg());
        }

        try {
            if (city.getGovernor() != null) validatedCity.setGovernor(humanValidator.validate(city.getGovernor()));
        } catch (ValidateFieldsException ex) {
            errorList.addAll(ex.getErrorMsg());
        }

        // проверка на null
        for (Field f : CityFromClient.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(city) == null) {
                errorList.add(new Error(700, f.getName(), (String.format("City %s is not specified", f.getName()))));
            }
        }

        if (city.getName() != null &&city.getName().isEmpty()) {
            errorList.add(new Error(701,"name", "City name should not be empty"));
        }  else {
            validatedCity.setName(city.getName());
        }
        // проверка на соответствие типу
        try {
            float area = 0F;
            if (city.getArea() != null) {
                area = Float.parseFloat(city.getArea());
            }
            if (area <= 0) {
                errorList.add(new Error(701, "area","City area should be bigger than 0"));
            } else {
                validatedCity.setArea(area);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "area", "The entered value is not a float value"));
        }
        try {
            int population = 0;
            if (city.getPopulation() != null) {
                population = Integer.parseInt(city.getPopulation());
            }
            if (population <= 0) {
                errorList.add(new Error(701, "population", "City population should be bigger than 0"));
            } else {
                validatedCity.setPopulation(population);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "population", "The entered value is not an integer value"));
        }
        try {
            Integer metersAboveSeaLevel = 0;
            if (city.getMetersAboveSeaLevel() != null) {
                metersAboveSeaLevel = Integer.parseInt(city.getMetersAboveSeaLevel());
            }
            validatedCity.setMetersAboveSeaLevel(metersAboveSeaLevel);
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "metersAboveSeaLevel", "The entered value is not an integer value"));
        }
        try {
            Double timezone = 0D;
            if (city.getTimezone() != null) {
                timezone = new Double(city.getTimezone());
            }
            if (timezone <= -13 || timezone > 15) {
                errorList.add(new Error(701, "timezone","City timezone should be between -12 and 15"));
            } else {
                validatedCity.setTimezone(timezone);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "timezone", "The entered value is not a double value"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        if (city.getId() != null) validatedCity.setId(city.getId());
        validatedCity.setGovernment(city.getGovernment());
        validatedCity.setStandardOfLiving(city.getStandardOfLiving());

        return validatedCity;
    }
}
