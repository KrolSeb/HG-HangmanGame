package com.wisielec.wisielec.domain;

/**
 * Created by Emerex on 14/01/2018.
 */
public class DifficultyLevel {
    private String name;
    private long points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "DifficultyLevel{" +
                "name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
