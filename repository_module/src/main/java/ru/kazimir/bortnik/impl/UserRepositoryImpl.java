package ru.kazimir.bortnik.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.kazimir.bortnik.UserRepository;
import ru.kazimir.bortnik.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.model.Role;
import ru.kazimir.bortnik.model.User;
import ru.kazimir.bortnik.model.enums.RoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesRepository.N0_ADD;
import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesRepository.QUERY_FAILED;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public List<User> getUsers(Connection connection) {
        String sqlQuery = "SELECT  Role.name, Users.username, Users.id FROM Users JOIN Role ON Role.id=Users.role_id;";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(buildingUser(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(String.format(QUERY_FAILED, sqlQuery), e);
        }
        return userList;
    }

    @Override
    public void add(User user, Connection connection) {
        String sqlQuery = "INSERT INTO Users (username,password,email,role_id) VALUES (?,?,?,(SELECT r.id FROM Role r WHERE r.name = ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole().getRoleEnum().name());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format(N0_ADD, user));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(String.format(QUERY_FAILED, sqlQuery), e);
        }
    }

    @Override
    public User getByEmail(String email, Connection connection) {
        String sqlQuery = "SELECT  Role.name, Users.username, Users.id, Users.password FROM Users JOIN Role ON Role.id=Users.role_id WHERE Users.email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildingUserAuthentication(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(String.format(QUERY_FAILED, sqlQuery), e);
        }
        return null;
    }

    private User buildingUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        RoleEnum roleEnum = RoleEnum.valueOf(resultSet.getString("name"));
        Role role = new Role();
        role.setRoleEnum(roleEnum);
        user.setRole(role);
        return user;
    }

    private User buildingUserAuthentication(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        RoleEnum roleEnum = RoleEnum.valueOf(resultSet.getString("name"));
        Role role = new Role();
        role.setRoleEnum(roleEnum);
        user.setRole(role);
        return user;
    }
}
