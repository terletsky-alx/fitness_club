package org.fitness_club.statement.model;

import lombok.Data;

@Data
public class Workout {
    private Long id;
    private Long trainerId;
    private String name;
    private Integer durationMinutes;

    public Workout() {}

    public Workout(Long trainerId, String name, Integer durationMinutes) {
        this.trainerId = trainerId;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }
}