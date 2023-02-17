package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Users("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "lastName VARCHAR(30) NOT NULL,"
                + "age TINYINT NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        try (Connection connection = Util.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                connection.setAutoCommit(false);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS Users";
        try (Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(dropTableSQL);
                if (rowsAffected > 0) {
                    System.out.println("Таблица Users была успешно удалена!");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User с именем - "+ name +" добавлен в базу данных ");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String deleteUserByIdSQL = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = Util.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteUserByIdSQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM Users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllUsersSQL)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastName, age));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String deleteAllUsersSQL = "DELETE FROM Users";
        try (Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute(deleteAllUsersSQL);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
