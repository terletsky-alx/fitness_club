package org.fitness_club.prepared_statement.model;

import lombok.Data;
import org.fitness_club.utils.InputManager;

@Data
public class Equipment {
    private Long id;
    private String name;
    private String type;
    private String manufacturer;
    private Integer purchaseYear;
    private Double purchasePrice;
    private Boolean isWorking;

    public Equipment() {}


    public Equipment(String name, String type, String manufacturer, Integer purchaseYear, Double purchasePrice) {
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.purchaseYear = purchaseYear;
        this.purchasePrice = purchasePrice;
        this.isWorking = true;
    }


    public static Equipment getFromInput() {
        System.out.print("Введи название оборудования: ");
        String name = InputManager.getNextLine();

        System.out.print("Введи тип (Кардио / Силовое / Для растяжки / Функциональное / Другое): ");
        String type = InputManager.getNextLine();

        System.out.print("Введи производителя (-, чтобы пропустить): ");
        String manufacturer = InputManager.getNextLineWithSkip();

        System.out.print("Введи год покупки (1990 - 2026): ");
        int year = InputManager.getNextIntInRange(1990, 2026);

        System.out.print("Введи цену покупки: ");
        double price = InputManager.getNextDouble();

        return new Equipment(name, type, manufacturer, year, price);
    }
}