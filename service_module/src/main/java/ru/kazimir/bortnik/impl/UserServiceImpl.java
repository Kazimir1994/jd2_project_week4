package ru.kazimir.bortnik.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.UserRepository;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.connection.ConnectionDataBaseService;
import ru.kazimir.bortnik.converter.UserConverter;
import ru.kazimir.bortnik.exceptions.ConnectionDataBaseExceptions;
import ru.kazimir.bortnik.exceptions.UserRepositoryException;
import ru.kazimir.bortnik.model.User;
import ru.kazimir.bortnik.model.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesService.NO_CONNECTION;
import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesService.QUERY_FAILED;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final ConnectionDataBaseService connectionDataBaseService;

    @Autowired
    public UserServiceImpl(UserConverter userConverter, UserRepository userRepository, ConnectionDataBaseService connectionDataBaseService) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.connectionDataBaseService = connectionDataBaseService;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> itemDTOList = new ArrayList<>();
        try (Connection connection = connectionDataBaseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<User> userList = userRepository.getUsers(connection);
                for (User user : userList) {
                    itemDTOList.add(userConverter.toDTO(user));
                }
                connection.commit();
                return itemDTOList;
            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        try (Connection connection = connectionDataBaseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userConverter.fromDTO(userDTO);
                System.out.println(user.toString());
                userRepository.add(user, connection);
                connection.commit();
            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }

    @Override
    public UserDTO getByEmail(String email) {
        try (Connection connection = connectionDataBaseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userRepository.getByEmail(email, connection);
                if (user == null) {
                    connection.commit();
                    return null;
                }
                logger.info("From the base was got user {}", user);
                UserDTO userDTO = userConverter.toDTO(user);
                connection.commit();
                return userDTO;
            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }
}
