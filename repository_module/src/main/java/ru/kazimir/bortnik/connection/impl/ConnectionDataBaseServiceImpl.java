package ru.kazimir.bortnik.connection.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.connection.ConnectionDataBaseService;
import ru.kazimir.bortnik.exceptions.ConnectionDataBaseExceptions;
import ru.kazimir.bortnik.exceptions.DriveDataBaseException;
import ru.kazimir.bortnik.exceptions.InitializationDataBseException;
import ru.kazimir.bortnik.properties.DatabaseProperties;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesRepository.*;


@Service
public class ConnectionDataBaseServiceImpl implements ConnectionDataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionDataBaseServiceImpl.class);
    private DatabaseProperties databaseProperties;

    @Autowired
    public ConnectionDataBaseServiceImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            Class.forName(databaseProperties.getDatabaseDriverName());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DriveDataBaseException(NO_CONNECT_JDBC_DRIVE, e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(databaseProperties.getDatabaseURL(),
                    databaseProperties.getDatabaseUsername(),
                    databaseProperties.getDatabasePassword());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }

    @PostConstruct
    public void databaseInitialization() {
        File configurationFile = new File(getClass().getResource("/" + databaseProperties.getDatabaseConfigName()).getPath());
        if (configurationFile.exists()) {
            List<String> SetOfScripts = returnASetOfScripts(configurationFile);
            try (Connection connection = getConnection()) {
                connection.setAutoCommit(false);
                try (Statement statement = connection.createStatement()) {
                    for (String scripts : SetOfScripts) {
                        statement.execute(scripts);
                        logger.info(scripts);
                    }
                    connection.commit();
                } catch (Exception e) {
                    connection.rollback();
                    logger.error(e.getMessage(), e);
                    throw new InitializationDataBseException(NO_CONFIGURATION_DATABASE, e);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
            }
        } else {
            throw new InitializationDataBseException(NO_CONFIGURATION_FILE);
        }
    }

    private List<String> returnASetOfScripts(File nameFile) {
        StringBuilder dataFile = new StringBuilder();
        List<String> commandList = new ArrayList<>();
        boolean commandStartKey = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile))) {
            String dataLine = bufferedReader.readLine();
            if (dataLine != null) {
                if (dataLine.contains("CREATE TABLE") || dataLine.contains("INSERT INTO") || dataLine.contains("DROP TABLE")) {
                    commandStartKey = true;
                    dataFile.append(dataLine).append('\n');
                }
                while ((dataLine = bufferedReader.readLine()) != null) {
                    if (commandStartKey) {
                        dataFile.append(dataLine);
                        if (dataLine.contains(";")) {
                            commandStartKey = false;
                            commandList.add(dataFile.toString());
                            dataFile.delete(0, dataFile.length());
                        } else {
                            dataFile.append('\n');
                        }
                    } else {
                        if (dataLine.contains("CREATE TABLE") || dataLine.contains("INSERT INTO") || dataLine.contains("DROP TABLE")) {
                            dataFile.append(dataLine);
                            commandStartKey = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitializationDataBseException(NO_CONFIGURATION_FILE);
        }
        return commandList;
    }
}
