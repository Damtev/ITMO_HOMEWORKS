package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password, String confirmPassword)
            throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (!user.getEmail().matches("[^@]+@[^@]+")) {
            throw new ValidationException("Invalid email");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

        if (!confirmPassword.equals(password)) {
            throw new ValidationException("Passwords should be equal");
        }
    }

    public void register(User user, String email, String password) {
        user.setEmail(email);
        user.setPasswordSha1(getPasswordSha1(password));
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public int findCount() {
        return userRepository.findCount();
    }

    public User authorize(String login, String password) throws ValidationException {
        User userByLogin = userRepository.findByLogin(login);
        if (userByLogin == null) {
            throw new ValidationException("Unexisting login");
        }
        if (!getPasswordSha1(password).equals(userByLogin.getPasswordSha1())) {
            throw new ValidationException("Wrong password!");
        }
        return userByLogin;
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    private String getPasswordSha1(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password, StandardCharsets.UTF_8).toString();
    }
}
