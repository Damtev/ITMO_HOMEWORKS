package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.TalkRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

import static ru.itmo.webmail.model.database.DatabaseUtils.findCreationTime;
import static ru.itmo.webmail.model.database.DatabaseUtils.getDataSource;

public class TalkRepositoryImpl implements TalkRepository {
    @Override
    public void save(Talk talk) {
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Talk (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, talk.getSourceUserId());
                statement.setLong(2, talk.getTargetUserId());
                statement.setString(3, talk.getText());
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        talk.setId(generatedIdResultSet.getLong(1));
                        talk.setCreationTime(findCreationTime(talk));
                    } else {
                        throw new RepositoryException("Can't find id of saved Talk");
                    }
                } else {
                    throw new RepositoryException("Can't save Talk");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Talk", e);
        }

    }

    @Override
    public ArrayList<Talk> findBySourceUserId(long sourceUserId) {
        ArrayList<Talk> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Talk WHERE sourceUserId=?")) {
                statement.setLong(1, sourceUserId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(toTalk(statement.getMetaData(), resultSet));
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk by sourceUserId.", e);
        }
    }

    @Override
    public ArrayList<Talk> findByTargetUserId(long targetUserId) {
        ArrayList<Talk> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Talk WHERE targetUserId=?")) {
                statement.setLong(1, targetUserId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(toTalk(statement.getMetaData(), resultSet));
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk by targetUserId.", e);
        }
    }

    @Override
    public ArrayList<Talk> findById(long userId) {
        ArrayList<Talk> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Talk WHERE (sourceUserId=? OR targetUserId=?)")) {
                statement.setLong(1, userId);
                statement.setLong(2, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(toTalk(statement.getMetaData(), resultSet));
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk by userId.", e);
        }
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                talk.setId(resultSet.getLong(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                talk.setCreationTime(resultSet.getTimestamp(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                talk.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                talk.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                talk.setText(resultSet.getString(i));
            } else {
                throw new RepositoryException("Unexpected column 'Talk." + columnName + "'.");
            }
        }
        return talk;
    }

}
