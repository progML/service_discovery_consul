package ru.itmo.soalab2.model;

import java.io.Serializable;

public class SumPopulation implements Serializable {
    private long sum_population;

    public SumPopulation(long sum_population) {
        this.sum_population = sum_population;
    }

    public long getSum_population() {
        return sum_population;
    }

    public void setSum_population(long sum_population) {
        this.sum_population = sum_population;
    }
}
