package org.fitness_club.prepared_statement.action;

import org.fitness_club.prepared_statement.dao.EquipmentDao;
import org.fitness_club.prepared_statement.model.Equipment;
import org.fitness_club.utils.InputManager;

import java.util.List;

public class PreparedStatementAction {
    EquipmentDao equipmentDao = new EquipmentDao();

    // Операция 1: Добавить оборудование
    public void addEquipment() {
        Equipment eq = Equipment.getFromInput();
        equipmentDao.insert(eq);
        System.out.println("Оборудование добавлено успешно!");
    }

    // Операция 2: Показать всё оборудование
    public void showAllEquipment() {
        List<Equipment> list = equipmentDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Список оборудования пуст!");
            return;
        }
        System.out.println("\n[Всё оборудование]");
        list.forEach(System.out::println);
    }

    // Операция 3: Найти оборудование по типу
    public void findEquipmentByType() {
        System.out.print("Введи тип оборудования (кардио / силовое / другое): ");
        String type = InputManager.getNextLine();
        List<Equipment> list = equipmentDao.getByType(type);
        if (list.isEmpty()) {
            System.out.println("Оборудование с типом '" + type + "' не найдено!");
            return;
        }
        System.out.println("\n[Оборудование типа '" + type + "']");
        list.forEach(System.out::println);
    }

    // Операция 4: Удалить оборудование по id
    public void deleteEquipment() {
        System.out.print("Введи id оборудования для удаления: ");
        Long id = InputManager.getNextLong();
        equipmentDao.delete(id);
        System.out.println("Оборудование с id=" + id + " удалено!");
    }
}