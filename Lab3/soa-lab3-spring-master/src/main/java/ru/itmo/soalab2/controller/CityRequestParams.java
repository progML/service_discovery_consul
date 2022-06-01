package ru.itmo.soalab2.controller;

import org.springframework.data.domain.Sort;
import ru.itmo.soalab2.model.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityRequestParams {
    public String name;
    public String[] x;
    public String[] y;
    public String[] creationDate;
    public String[] area;
    public String[] population;
    public String[] metersAboveSeaLevel;
    public String[] timezone;
    public String[] government;
    public String[] standardOfLiving;
    public String[] birthday;
    public String[] height;
    public String[] sort;
    public int page;
    public int size;

    private static final String NAME_PARAM = "name";
    private static final String X_PARAM = "x";
    private static final String Y_PARAM = "y";
    private static final String AREA_PARAM = "area";
    private static final String CREATION_DATE_PARAM = "creation_date";
    private static final String POPULATION_PARAM = "population";
    private static final String METERS_ABOVE_SEA_LEVEL_PARAM = "meters_above_sea_level";
    private static final String TIMEZONE_PARAM = "timezone";
    private static final String GOVERNMENT_PARAM = "government";
    private static final String STANDARD_OF_LIVING_PARAM = "standard_of_living";
    private static final String HEIGHT_PARAM = "height";
    private static final String BIRTHDAY_PARAM = "birthday";
    private static final String SORTING_PARAM = "sort";
    private static final String PAGE_INDEX = "page";
    private static final String PAGE_SIZE_PARAM = "size";


    CityRequestParams(Map<String, String[]> info) {
        setCityRequestParams(info.get(NAME_PARAM),
                info.get(X_PARAM),
                info.get(Y_PARAM),
                info.get(AREA_PARAM),
                info.get(CREATION_DATE_PARAM),
                info.get(POPULATION_PARAM),
                info.get(METERS_ABOVE_SEA_LEVEL_PARAM),
                info.get(TIMEZONE_PARAM),
                info.get(GOVERNMENT_PARAM),
                info.get(STANDARD_OF_LIVING_PARAM),
                info.get(BIRTHDAY_PARAM),
                info.get(HEIGHT_PARAM),
                info.get(SORTING_PARAM),
                info.get(PAGE_INDEX),
                info.get(PAGE_SIZE_PARAM)
        );
    }

    private void setCityRequestParams(String[] name,
                                     String[] x,
                                     String[] y,
                                     String[] creationDate,
                                     String[] area,
                                     String[] population,
                                     String[] metersAboveSeaLevel,
                                     String[] timezone,
                                     String[] government,
                                     String[] standardOfLiving,
                                     String[] birthday,
                                     String[] height,
                                     String[] sort,
                                     String[] page,
                                     String[] size) {
        this.name = name == null ? null : name[0];
        this.x = x;
        this.y = y;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.birthday = birthday;
        this.height = height;
        this.sort = sort;
        this.page = page == null ? 0 : Integer.parseInt(page[0]);
        this.size = size == null ? 5 : Integer.parseInt(size[0]);
    }

    private String like(String val) {
        return "%" + val + "%";
    }

    public List<javax.persistence.criteria.Predicate> getPredicates(
            CriteriaBuilder cb,
            Root<City> root,
            Join<City, Coordinates> joinCoordinates,
            Join<City, Human> joinHuman
    ) throws ParseException {
        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        if (name != null)
            predicates.add(cb.like(root.get("name"), like(name)));

        if (x != null)
            if (x.length > 1) {
                if (x[0] != null && !x[0].isEmpty())
                    // больше или равен
                    predicates.add(cb.ge(joinCoordinates.get("x"), Integer.parseInt(x[0])));
                if (x[1] != null && !x[1].isEmpty())
                    // меньше или равен
                    predicates.add(cb.le(joinCoordinates.get("x"), Integer.parseInt(x[1])));
            } else if (x[0] != null && !x[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("x"), Integer.parseInt(x[0])));

        if (y != null)
            if (y.length > 1) {
                if (y[0] != null && !y[0].isEmpty())
                    predicates.add(cb.ge(joinCoordinates.get("y"), Long.parseLong(y[0])));
                if (y[1] != null && !y[1].isEmpty())
                    predicates.add(cb.le(joinCoordinates.get("y"), Long.parseLong(y[1])));
            } else if (y[0] != null && !y[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("y"), Long.parseLong(y[0])));

        if (creationDate != null)
            if (creationDate.length > 1) {
                if (creationDate[0] != null && !creationDate[0].isEmpty())
                    predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), new SimpleDateFormat("dd.MM.yyyy").parse(creationDate[0])));
                if (creationDate[1] != null && !creationDate[1].isEmpty())
                    predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), new SimpleDateFormat("dd.MM.yyyy").parse(creationDate[1])));
            } else if (creationDate[0] != null && !creationDate[0].isEmpty())
                predicates.add(cb.equal(root.get("creationDate"), new SimpleDateFormat("dd.MM.yyyy").parse(creationDate[0])));


        if (area != null)
            if (area.length > 1) {
                if (area[0] != null && !area[0].isEmpty())
                    predicates.add(cb.ge(joinCoordinates.get("area"), Float.parseFloat(area[0])));
                if (area[1] != null && !area[1].isEmpty())
                    predicates.add(cb.le(joinCoordinates.get("area"), Float.parseFloat(area[1])));
            } else if (area[0] != null && !area[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("area"), Float.parseFloat(area[0])));

        if (population != null)
            if (population.length > 1) {
                if (population[0] != null && !population[0].isEmpty())
                    // больше или равен
                    predicates.add(cb.ge(joinCoordinates.get("population"), Integer.parseInt(population[0])));
                if (population[1] != null && !population[1].isEmpty())
                    // меньше или равен
                    predicates.add(cb.le(joinCoordinates.get("population"), Integer.parseInt(population[1])));
            } else if (population[0] != null && !population[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("population"), Integer.parseInt(population[0])));

        if (metersAboveSeaLevel != null)
            if (metersAboveSeaLevel.length > 1) {
                if (metersAboveSeaLevel[0] != null && !metersAboveSeaLevel[0].isEmpty())
                    // больше или равен
                    predicates.add(cb.ge(joinCoordinates.get("metersAboveSeaLevel"), Integer.parseInt(metersAboveSeaLevel[0])));
                if (metersAboveSeaLevel[1] != null && !metersAboveSeaLevel[1].isEmpty())
                    // меньше или равен
                    predicates.add(cb.le(joinCoordinates.get("metersAboveSeaLevel"), Integer.parseInt(metersAboveSeaLevel[1])));
            } else if (metersAboveSeaLevel[0] != null && !metersAboveSeaLevel[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("metersAboveSeaLevel"), Integer.parseInt(metersAboveSeaLevel[0])));

        if (timezone != null)
            if (timezone.length > 1) {
                if (timezone[0] != null && !timezone[0].isEmpty())
                    predicates.add(cb.ge(root.get("timezone"), Double.parseDouble(timezone[0])));
                if (timezone[1] != null && !timezone[1].isEmpty())
                    predicates.add(cb.le(root.get("timezone"), Double.parseDouble(timezone[1])));
            } else if (timezone[0] != null && !timezone[0].isEmpty())
                predicates.add(cb.equal(root.get("timezone"), Double.parseDouble(timezone[0])));

        if (government != null)
            predicates.add(root.get("government").as(String.class).in(government));

        if (standardOfLiving != null)
            predicates.add(root.get("government").as(String.class).in(standardOfLiving));

        if (height != null)
            if (height.length > 1) {
                if (height[0] != null && !height[0].isEmpty())
                    predicates.add(cb.ge(joinHuman.get("height"), Double.parseDouble(height[0])));
                if (height[1] != null && !height[1].isEmpty())
                    predicates.add(cb.le(joinHuman.get("height"), Double.parseDouble(height[1])));
            } else if (height[0] != null && !height[0].isEmpty())
                predicates.add(cb.equal(joinHuman.get("height"), Double.parseDouble(height[0])));

        if (birthday != null)
            if (birthday.length > 1) {
                if (birthday[0] != null && !birthday[0].isEmpty())
                    predicates.add(cb.greaterThanOrEqualTo(joinHuman.get("birthday"), new SimpleDateFormat("dd.MM.yyyy").parse(birthday[0])));
                if (birthday[1] != null && !birthday[1].isEmpty())
                    predicates.add(cb.lessThanOrEqualTo(joinHuman.get("birthday"), new SimpleDateFormat("dd.MM.yyyy").parse(birthday[1])));
            } else if (birthday[0] != null && !birthday[0].isEmpty())
                predicates.add(cb.equal(joinHuman.get("birthday"), new SimpleDateFormat("dd.MM.yyyy").parse(birthday[0])));

        return predicates;
    }

    public Sort parseSorting() throws ParseException {
        String[] args = sort[0].split("_", 2);
        if (args.length != 2)
            throw new ParseException("incorrect sort parameter " + sort[0], 0);
        if (args[0].equals(X_PARAM) || args[0].equals(Y_PARAM)) {
            args[0] = "coordinates." + args[0];
        }
        if (args[0].equals(HEIGHT_PARAM) || args[0].equals(BIRTHDAY_PARAM)) {
            args[0] = "governor." + args[0];
        }
        String field = args[0];
        Sort currentSorting = Sort.by(field);
        if (args[1].equals("asc"))
            currentSorting = currentSorting.ascending();
        else if (args[1].equals("desc"))
            currentSorting = currentSorting.descending();
        else
            throw new ParseException("incorrect sort parameter " + sort[0], 0);
        return currentSorting;
    }
}
