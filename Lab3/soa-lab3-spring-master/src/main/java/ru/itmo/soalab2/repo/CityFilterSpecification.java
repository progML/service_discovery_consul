package ru.itmo.soalab2.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.itmo.soalab2.controller.CityRequestParams;
import ru.itmo.soalab2.model.City;
import ru.itmo.soalab2.model.Coordinates;
import ru.itmo.soalab2.model.Human;
import javax.persistence.criteria.*;
import java.text.ParseException;

public class CityFilterSpecification implements Specification<City> {

    private CityRequestParams filterParams;

    public CityFilterSpecification(CityRequestParams filterParams) {
        this.filterParams = filterParams;
    }

    @Override
    public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<City, Coordinates> coordinatesJoin =  root.join("coordinates");
        Join<City, Human> humanJoin =  root.join("governor");
        try {
            return criteriaBuilder.and(filterParams.getPredicates(criteriaBuilder,root, coordinatesJoin,humanJoin).toArray(new Predicate[0]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
