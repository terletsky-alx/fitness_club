package org.fitness_club.statement.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDate registrationDate;
    private Boolean isActive;

    public Client() {}

    public Client(String firstName, String lastName, String phone,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.registrationDate = LocalDate.now();
        this.isActive = true;

    }
}