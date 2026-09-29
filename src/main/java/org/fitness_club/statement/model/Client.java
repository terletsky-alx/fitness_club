package org.fitness_club.statement.model;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;

    public Client() {}

    public Client(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}