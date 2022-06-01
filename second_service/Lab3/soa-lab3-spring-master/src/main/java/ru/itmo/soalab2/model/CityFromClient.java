package ru.itmo.soalab2.model;

public class CityFromClient {
    private Long id;
    private String name;
    private CoordinatesFromClient coordinates;
    private String area;
    private String population;
    private String metersAboveSeaLevel;
    private String timezone;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private HumanFromClient governor;

    public CityFromClient(Long id, String name, CoordinatesFromClient coordinates, String area, String population, String metersAboveSeaLevel, String timezone, Government government, StandardOfLiving standardOfLiving, HumanFromClient governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesFromClient getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesFromClient coordinates) {
        this.coordinates = coordinates;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(String metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public HumanFromClient getGovernor() {
        return governor;
    }

    public void setGovernor(HumanFromClient governor) {
        this.governor = governor;
    }
}
