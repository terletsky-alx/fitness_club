package org.fitness_club.statement.model;

import lombok.Data;

@Data
public class Trainer {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;

    public Trainer() {}

    public Trainer(String firstName, String lastName, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }
}