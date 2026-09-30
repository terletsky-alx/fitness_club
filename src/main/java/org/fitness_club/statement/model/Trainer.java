package org.fitness_club.statement.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Trainer {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private LocalDate hireDate;
    private Double hourlyRate;
    private Boolean isActive;

    // Пустой конструктор (нужен для маппинга ResultSet)
    public Trainer() {}

    // НОВЫЙ конструктор с 4 параметрами (hourlyRate добавлен)
    public Trainer(String firstName, String lastName, String specialization, Double hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.hireDate = LocalDate.now();
        this.isActive = true;
    }
}