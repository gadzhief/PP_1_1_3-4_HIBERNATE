package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args)  {

        UserDao userDao = new UserDaoHibernateImpl();

        UserService userService = new UserServiceImpl(userDao);

        // 1 Создание таблицы Users
        userService.createUsersTable();
        // 2 Добавление 4 User в таблицу
        userService.saveUser("Maxim", "Krylov", (byte) 30);
        userService.saveUser("Ivan", "Ivanov", (byte) 45);
        userService.saveUser("Alex", "Volkov", (byte) 24);
        userService.saveUser("Nikita", "Below", (byte) 34);
        // 3 Получение всех User из БД и вывод в консоль
        userService.getAllUsers().forEach(System.out::println);
        // 4 Очистка таблицы
        userService.cleanUsersTable();
        // 5 Удаление таблицы
        userService.dropUsersTable();
    }
}
