package com.wisielec.wisielec.domain;

/**
 * Created by Emerex on 12/01/2018.
 */
public class Rank {
    private long maximumPoints;
    private long minimumPoints;
    private String name;

    public long getMaximumPoints() {
        return maximumPoints;
    }

    public void setMaximumPoints(long maximumPoints) {
        this.maximumPoints = maximumPoints;
    }

    public long getMinimumPoints() {
        return minimumPoints;
    }

    public void setMinimumPoints(long minimumPoints) {
        this.minimumPoints = minimumPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
