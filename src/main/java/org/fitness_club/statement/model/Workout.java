package org.fitness_club.statement.model;

import lombok.Data;

@Data
public class Workout {
    private Long id;
    private Long trainerId;
    private String name;
    private Integer durationMinutes;
    private Integer maxParticipants;   // Новое поле
    private String difficulty;         // Новое поле
    private Integer roomNumber;        // Новое поле

    public Workout() {}

    // ОБНОВЛЕННЫЙ конструктор с 5 параметрами
    public Workout(Long trainerId, String name, Integer durationMinutes, String difficulty, Integer roomNumber) {
        this.trainerId = trainerId;
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.difficulty = difficulty;
        this.roomNumber = roomNumber;
    }
}