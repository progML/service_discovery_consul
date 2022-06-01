package ru.itmo.soalab2.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.soalab2.model.City;

import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface  CityRepository extends PagingAndSortingRepository<City, Long>, JpaSpecificationExecutor<City> {
    List<City> findByNameContaining(String name);
    List<City> findByMetersAboveSeaLevelGreaterThan(int meters);
    @Query("SELECT DISTINCT c.metersAboveSeaLevel FROM City c ORDER BY c.metersAboveSeaLevel ASC")
    List<String> findDistinctMetersAboveSeaLevel();
    @Query("SELECT c.creationDate FROM City c where c.id = ?1 ")
    ZonedDateTime findCreationDateByCityId(long id);
    City findById(long id);
    @Query("select c FROM City c where c.population = (select min (f.population) from City f)")
    City findCityWithMinPop();

    @Transactional
    @Modifying
    @Query("update City c set c.population = 0 where c.id = :id")
    void ch1(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("update City c set c.population = c.population + :pop where c.id = :id")
    void ch2(@Param("pop") int pop, @Param("id") long id);
}
