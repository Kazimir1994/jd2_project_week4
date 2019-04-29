package ru.kazimir.bortnik;

import ru.kazimir.bortnik.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {
    List<User> getUsers(Connection connection);

    void add(User user, Connection connection);

    User getByEmail(String email, Connection connection);
}
