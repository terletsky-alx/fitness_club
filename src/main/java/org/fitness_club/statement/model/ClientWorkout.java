package org.fitness_club.statement.model;

import lombok.Data;

@Data
public class ClientWorkout {
    private Long id;
    private Long clientId;
    private Long workoutId;

    public ClientWorkout() {}

    public ClientWorkout(Long clientId, Long workoutId) {
        this.clientId = clientId;
        this.workoutId = workoutId;
    }
}