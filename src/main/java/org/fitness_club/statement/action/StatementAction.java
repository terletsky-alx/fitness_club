package org.fitness_club.statement.action;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.dao.*;
import org.fitness_club.statement.model.*;
import org.fitness_club.utils.InputManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatementAction {
    private static final Logger logger = Logger.getLogger(StatementAction.class.getName());

    ClientDao clientDao = new ClientDao();
    TrainerDao trainerDao = new TrainerDao();
    SubscriptionDao subscriptionDao = new SubscriptionDao();
    WorkoutDao workoutDao = new WorkoutDao();
    ClientWorkoutDao clientWorkoutDao = new ClientWorkoutDao();

    // ========== УДАЛЕНИЕ ВСЕЙ ИНФОРМАЦИИ ==========
    public void deleteAllInfo() {
        System.out.print("Подтвердите удаление всей информации (y / Y): ");
        final String choice = InputManager.getNextLine();
        if (!choice.equals("Y") && !choice.equals("y")) {
            System.out.println("Удаление было отменено!");
            return;
        }
        try {
            // ВАЖНО: сначала удаляем из таблиц со связями, потом из основных
            clientWorkoutDao.clearTable();
            subscriptionDao.clearTable();
            workoutDao.clearTable();
            trainerDao.clearTable();
            clientDao.clearTable();
            System.out.println("Информация из таблиц была успешно удалена!");
        } catch (RuntimeException e) {
            logger.severe("Ошибка при удалении: " + e.getMessage());
        }
    }

    // ========== ДОБАВЛЕНИЕ ДЕФОЛТНОЙ ИНФОРМАЦИИ ==========
    public void addDefaultInfo() {
        try {
            if (clientDao.getCount() != 0 || trainerDao.getCount() != 0 ||
                    workoutDao.getCount() != 0 || subscriptionDao.getCount() != 0 ||
                    clientWorkoutDao.getCount() != 0) {
                System.out.println("Таблицы не пусты. Вставка невозможна!");
                return;
            }

            // Клиенты
            List<Client> clients = getDefaultClients();
            for (Client c : clients) clientDao.insert(c);
            clients = clientDao.getAll();

            // Тренеры
            List<Trainer> trainers = getDefaultTrainers();
            for (Trainer t : trainers) trainerDao.insert(t);
            trainers = trainerDao.getAll();

            // Абонементы
            List<Subscription> subs = getDefaultSubscriptions(clients);
            for (Subscription s : subs) subscriptionDao.insert(s);

            // Тренировки
            List<Workout> workouts = getDefaultWorkouts(trainers);
            for (Workout w : workouts) workoutDao.insert(w);
            workouts = workoutDao.getAll();

            // Связи клиент-тренировка
            List<ClientWorkout> cws = getDefaultClientWorkouts(clients, workouts);
            for (ClientWorkout cw : cws) clientWorkoutDao.insert(cw);

            System.out.println("Информация по умолчанию вставлена успешно!");
        } catch (RuntimeException e) {
            logger.severe("Ошибка при добавлении: " + e.getMessage());
        }
    }

    // ========== ПРОСМОТР ВСЕЙ ИНФОРМАЦИИ (7 ЗАДАНИЙ) ==========
    public void getAllInfo() {
        try {
            // --- ЗАДАНИЕ 1: Выборка по всем таблицам ---
            System.out.println("\n========== ЗАДАНИЕ 1: Все таблицы ==========");
            printList("[Клиенты]", clientDao.getAll());
            printList("[Тренеры]", trainerDao.getAll());
            printList("[Абонементы]", subscriptionDao.getAll());
            printList("[Тренировки]", workoutDao.getAll());
            printList("[Связи клиент-тренировка]", clientWorkoutDao.getAll());

            // --- ЗАДАНИЕ 2: Сортировка тренеров по имени и фамилии ---
            System.out.println("\n========== ЗАДАНИЕ 2: Тренеры по алфавиту ==========");
            trainerDao.getAllSortedByName().forEach(System.out::println);

            // --- ЗАДАНИЕ 3: Тренировки длительностью > 60 минут, отсортированные ---
            System.out.println("\n========== ЗАДАНИЕ 3: Тренировки > 60 минут ==========");
            workoutDao.getLongerThan(60).forEach(System.out::println);

            // --- ЗАДАНИЕ 4: Обновить название случайной тренировки у тренера с id=1 ---
            System.out.println("\n========== ЗАДАНИЕ 4: Обновление тренировки ==========");
            workoutDao.updateRandomWorkoutNameOfTrainer(1L, "Обновлённая тренировка");
            System.out.println("После обновления:");
            workoutDao.getAll().forEach(System.out::println);

            // --- ЗАДАНИЕ 5: Тренировки тренеров с фамилией на букву "И" ---
            System.out.println("\n========== ЗАДАНИЕ 5: Тренировки тренеров на 'И' ==========");
            workoutDao.getByTrainerLastNameStartsWith("И").forEach(System.out::println);

            // --- ЗАДАНИЕ 6: Обновить имя клиентов, посещающих тренировки тренера с id=2 ---
            System.out.println("\n========== ЗАДАНИЕ 6: Обновление клиентов тренера id=2 ==========");
            System.out.println("До обновления:");
            clientDao.getAll().forEach(System.out::println);
            clientDao.updateClientsOfTrainer(2L, "ОбновлённоеИмя");
            System.out.println("После обновления:");
            clientDao.getAll().forEach(System.out::println);

            // --- ЗАДАНИЕ 7: Количество тренировок у каждого тренера (подзапрос) ---
            System.out.println("\n========== ЗАДАНИЕ 7: Подсчёт тренировок ==========");
            trainerDao.printTrainersWithWorkoutCount();

        } catch (RuntimeException e) {
            logger.severe("Ошибка при просмотре: " + e.getMessage());
        }
    }

    private <T> void printList(String header, List<T> list) {
        System.out.println("\n" + header);
        if (list.isEmpty()) {
            System.out.println("(пусто)");
        } else {
            list.forEach(System.out::println);
        }
    }

    // ========== ДЕФОЛТНЫЕ ДАННЫЕ ==========
    private List<Client> getDefaultClients() {
        List<Client> list = new ArrayList<>();
        list.add(new Client("Иван", "Петров", "+375291111111"));
        list.add(new Client("Анна", "Сидорова", "+375292222222"));
        list.add(new Client("Олег", "Иванов", "+375293333333"));
        list.add(new Client("Мария", "Козлова", "+375294444444"));
        return list;
    }

    private List<Trainer> getDefaultTrainers() {
        List<Trainer> list = new ArrayList<>();
        list.add(new Trainer("Алексей", "Смирнов", "Силовые тренировки"));
        list.add(new Trainer("Елена", "Иванова", "Йога"));
        list.add(new Trainer("Дмитрий", "Попов", "Кроссфит"));
        return list;
    }

    private List<Subscription> getDefaultSubscriptions(List<Client> clients) {
        List<Subscription> list = new ArrayList<>();
        list.add(new Subscription(clients.get(0).getId(), "Годовой",
                LocalDate.of(2026, 1, 15), LocalDate.of(2027, 1, 15)));
        list.add(new Subscription(clients.get(1).getId(), "Месячный",
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 10, 1)));
        list.add(new Subscription(clients.get(2).getId(), "Полугодовой",
                LocalDate.of(2026, 5, 10), LocalDate.of(2026, 11, 10)));
        return list;
    }

    private List<Workout> getDefaultWorkouts(List<Trainer> trainers) {
        List<Workout> list = new ArrayList<>();
        // Тренер 1 (Смирнов) - силовые
        list.add(new Workout(trainers.get(0).getId(), "Жим лёжа", 45));
        list.add(new Workout(trainers.get(0).getId(), "Приседания со штангой", 60));
        list.add(new Workout(trainers.get(0).getId(), "Становая тяга", 90));
        // Тренер 2 (Иванова) - йога
        list.add(new Workout(trainers.get(1).getId(), "Хатха-йога", 75));
        list.add(new Workout(trainers.get(1).getId(), "Виньяса", 60));
        // Тренер 3 (Попов) - кроссфит
        list.add(new Workout(trainers.get(2).getId(), "WOD интенсив", 50));
        list.add(new Workout(trainers.get(2).getId(), "Кардио-круг", 40));
        return list;
    }

    private List<ClientWorkout> getDefaultClientWorkouts(List<Client> clients, List<Workout> workouts) {
        List<ClientWorkout> list = new ArrayList<>();
        // Клиент 0 (Петров) - на силовые Смирнова
        list.add(new ClientWorkout(clients.get(0).getId(), workouts.get(0).getId()));
        list.add(new ClientWorkout(clients.get(0).getId(), workouts.get(1).getId()));
        // Клиент 1 (Сидорова) - на йогу Ивановой
        list.add(new ClientWorkout(clients.get(1).getId(), workouts.get(3).getId()));
        list.add(new ClientWorkout(clients.get(1).getId(), workouts.get(4).getId()));
        // Клиент 2 (Иванов) - на кроссфит Попова
        list.add(new ClientWorkout(clients.get(2).getId(), workouts.get(5).getId()));
        list.add(new ClientWorkout(clients.get(2).getId(), workouts.get(6).getId()));
        return list;
    }
}