package org.fitness_club.menus;

import org.fitness_club.prepared_statement.action.PreparedStatementAction;
import org.fitness_club.utils.InputManager;
import org.fitness_club.utils.MenuUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PreparedStatementMenu {
    private static final Logger logger = Logger.getLogger(PreparedStatementMenu.class.getName());

    private final PreparedStatementAction preparedStatementAction = new PreparedStatementAction();

    public void print() {
        // Формируем список пунктов меню
        List<String> options = new ArrayList<>();
        options.add("Добавить оборудование");
        options.add("Показать всё оборудование");
        options.add("Найти оборудование по типу");
        options.add("Удалить оборудование");

        // Получаем красиво отформатированный заголовок меню
        final String header = MenuUtils.getHeader("Меню PreparedStatement", options);

        // Бесконечный цикл меню — выход только по пункту "0) Выход"
        while (true) {
            System.out.print(header);

            try {
                switch (InputManager.getNextInt()) {
                    case 1: {
                        preparedStatementAction.addEquipment();
                        break;
                    }
                    case 2: {
                        preparedStatementAction.showAllEquipment();
                        break;
                    }
                    case 3: {
                        preparedStatementAction.findEquipmentByType();
                        break;
                    }
                    case 4: {
                        preparedStatementAction.deleteEquipment();
                        break;
                    }
                    case 0: {
                        return; // Выход из меню
                    }
                    default: {
                        System.out.println("Такого варианта нет!");
                        break;
                    }
                }
            } catch (Exception e) {
                logger.severe("Ошибка при обработке меню PreparedStatement: " + e.getMessage());
            }
        }
    }
}