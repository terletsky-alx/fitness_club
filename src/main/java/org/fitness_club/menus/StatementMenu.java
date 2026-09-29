package org.fitness_club.menus;

import org.fitness_club.statement.action.StatementAction;
import org.fitness_club.utils.InputManager;
import org.fitness_club.utils.MenuUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatementMenu {
    private static final Logger logger = Logger.getLogger(StatementMenu.class.getName());

    // Создаем объект бизнес-логики для Statement
    private final StatementAction statementAction = new StatementAction();

    public void print() {
        // Формируем список пунктов меню
        List<String> options = new ArrayList<>();
        options.add("Очистить все таблицы");
        options.add("Просмотреть все записи (выполнить 7 заданий)");
        options.add("Добавить дефолтную информацию");

        // Получаем красиво отформатированный заголовок меню
        final String header = MenuUtils.getHeader("Меню Statement", options);

        // Бесконечный цикл меню — выход только по пункту "0) Выход"
        while (true) {
            System.out.print(header);

            try {
                switch (InputManager.getNextInt()) {
                    case 1: {
                        statementAction.deleteAllInfo();
                        break;
                    }
                    case 2: {
                        statementAction.getAllInfo();
                        break;
                    }
                    case 3: {
                        statementAction.addDefaultInfo();
                        break;
                    }
                    case 0: {
                        return; // Выход из меню Statement в главное меню
                    }
                    default: {
                        System.out.println("Такого варианта нет!");
                        break;
                    }
                }
            } catch (Exception e) {
                logger.severe("Ошибка при обработке меню Statement: " + e.getMessage());
            }
        }
    }
}