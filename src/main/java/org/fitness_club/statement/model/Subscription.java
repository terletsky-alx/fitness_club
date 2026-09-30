package org.fitness_club.statement.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Subscription {
    private Long id;
    private Long clientId;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;          // ← новое поле
    private Boolean isUsed;        // ← новое поле

    public Subscription() {}

    // НОВЫЙ конструктор с 5 параметрами (добавлен price)
    public Subscription(Long clientId, String type, LocalDate startDate, LocalDate endDate, Double price) {
        this.clientId = clientId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.isUsed = false;
    }
}