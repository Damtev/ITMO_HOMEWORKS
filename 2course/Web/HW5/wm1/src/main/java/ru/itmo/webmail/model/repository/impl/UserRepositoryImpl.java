package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public User find(long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE id=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=?")) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE email=?")) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by email.", e);
        }
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE (login=? OR email=?) AND passwordSha=?")) {
                statement.setString(1, loginOrEmail);
                statement.setString(2, loginOrEmail);
                statement.setString(3, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id and passwordSha.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User ORDER BY id")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = toUser(statement.getMetaData(), resultSet);
                        if (user.isConfirmed()) {
                            users.add(user);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all users.", e);
        }
        return users;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                user.setId(resultSet.getLong(i));
            } else if ("login".equalsIgnoreCase(columnName)) {
                user.setLogin(resultSet.getString(i));
            } else if ("passwordSha".equalsIgnoreCase(columnName)) {
                // No operations.
            } else if ("email".equalsIgnoreCase(columnName)) {
                user.setEmail(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                user.setCreationTime(resultSet.getTimestamp(i));
            } else if ("confirmed".equalsIgnoreCase(columnName)) {
                user.setConfirmed(resultSet.getBoolean(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return user;
    }

    @Override
    public void save(User user, String email, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO User (login, email, passwordSha, creationTime) VALUES (?, ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getEmail());
                statement.setString(3, passwordSha);
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        user.setId(generatedIdResultSet.getLong(1));
                        user.setCreationTime(DatabaseUtils.findCreationTime(user));
                    } else {
                        throw new RepositoryException("Can't find id of saved User.");
                    }
                } else {
                    throw new RepositoryException("Can't save User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public void confirm(long userId) {
        User user = find(userId);
        if (user == null) {
            throw new RepositoryException("Can't find User by id.");
        }
        user.setConfirmed(true);
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE User SET confirmed=true where id=?",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, user.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't confirm User.", e);
        }
    }
}
