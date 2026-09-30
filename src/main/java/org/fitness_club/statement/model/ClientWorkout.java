package org.fitness_club.statement.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientWorkout {
    private Long id;
    private Long clientId;
    private Long workoutId;
    private LocalDate attendanceDate;
    private Integer rating;

    public ClientWorkout() {}

    public ClientWorkout(Long clientId, Long workoutId) {
        this.clientId = clientId;
        this.workoutId = workoutId;
    }
}