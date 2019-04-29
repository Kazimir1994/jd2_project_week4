package ru.kazimir.bortnik.exceptions.messageexception;

public class ErrorMessagesRepository {
    private ErrorMessagesRepository() {
    }

    public static final String QUERY_FAILED = "Query: %s - failed";
    public static final String NO_CONNECT_JDBC_DRIVE = "Failed to connect jdbc driver";
    public static final String NO_CONNECTION = "Failed to get connection data base";
    public static final String NO_CONFIGURATION_FILE = "File for setting tables in database not found";
    public static final String NO_CONFIGURATION_DATABASE = "There were problems with initializing the database from the configuration file";
    public static final String N0_ADD = "Problems adding:%s";

}
