package ru.itmo.webmail.model.database;

import javafx.util.Pair;
import org.mariadb.jdbc.MariaDbDataSource;
import ru.itmo.webmail.model.domain.EmailConfirmation;
import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties PROPERTIES = new Properties();

        static {
            try {
                PROPERTIES.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load application.properties.", e);
            }

            try {
                MariaDbDataSource dataSource = new MariaDbDataSource();
                dataSource.setUrl(PROPERTIES.getProperty("database.url"));
                dataSource.setUser(PROPERTIES.getProperty("database.user"));
                dataSource.setPassword(PROPERTIES.getProperty("database.password"));
                INSTANCE = dataSource;
            } catch (SQLException e) {
                throw new RuntimeException("Can't initialize DB.", e);
            }

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't get testing connection from DB.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't get testing connection from DB.", e);
            }
        }
    }

    public static Date findCreationTime(User user) {
        return findCreationTime("User", user.getId());
    }

    public static Date findCreationTime(Event event) {
        return findCreationTime("Event", event.getId());
    }

    public static Date findCreationTime(EmailConfirmation emailConfirmation) {
        return findCreationTime("EmailConfirmation", emailConfirmation.getId());
    }

    public static Date findCreationTime(Talk talk) {
        return findCreationTime("Talk", talk.getId());
    }

    private static Date findCreationTime(String tableName, long id) {
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM " + tableName + " WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException(String.format("Can't find %s.creationTime by id.", tableName));
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Can't find %s.creationTime by id.", tableName), e);
        }
    }

    public static ResultSet process(DataSource dataSource, String query, String errorMessage, int queryType, String... params) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    statement.setString(i + 1, params[i]);
                }
                switch (queryType) {
                    case 0: //FIND
                        return statement.executeQuery();
                    case 1: //INSERT
                        statement.executeUpdate();
                        return null;
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }

    public static Pair<ResultSet, ResultSetMetaData> process(DataSource source, String query, String errorMessage, String... params) {
        try (Connection connection = source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    statement.setString(i + 1, params[i]);
                }
                return new Pair<>(statement.executeQuery(), statement.getMetaData());
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }
}
