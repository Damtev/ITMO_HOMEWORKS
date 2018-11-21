package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

public class EventRepositoryImpl implements EventRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Event event) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Event (userId, type, creationTime) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, event.getUserId());
                statement.setString(2, event.getType().name());
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        event.setId(generatedIdResultSet.getLong(1));
                        event.setCreationTime(DatabaseUtils.findCreationTime(event));
                    } else {
                        throw new RepositoryException("Can't find Event.");
                    }
                } else {
                    throw new RepositoryException("Can't save Event.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Event.", e);
        }
    }
}
