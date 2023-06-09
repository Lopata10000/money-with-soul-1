package com.fanta.moneywithsoul.service;

import static com.fanta.moneywithsoul.database.DataBaseConfig.password;

import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.enumrole.UserRole;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * The type User service.
 */
public class UserService implements ServiceInterface<User> {
    private final UserDAO userDAO;


    /**
     * Instantiates a new User service.
     */
    public UserService() {
        userDAO = new UserDAO();
    }

    @Override
    public User getById(Long userId) {
        if (userId == null || userId <= 0) {
            showErrorMessage("Недійсний ідентифікатор користувача");
        } else {
            User user = userDAO.findById(userId);
            if (user == null) {
                showErrorMessage("Користувача з таким ідентифікатором не знайдено");
            }
        }
        return userDAO.findById(userId);
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public void save(User user) {

        UserDAO userDAO = new UserDAO();
        boolean emailExists = userDAO.existsByEmail(user.getEmail());
        if (emailExists) {
            showErrorMessage("Користувач з таким email вже існує, використайте іншу.");
            throw new RuntimeException();
        } else {
            validateAndSave(user);
            userDAO.save(user);
            Properties properties = new Properties();
            properties.setProperty("id", String.valueOf(user.getUserId()));

            String filePath = System.getProperty("user.dir") + "/file.properties";

            try (FileOutputStream output = new FileOutputStream(filePath)) {
                properties.store(output, "User Properties");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Long userId, User user) {
        UserDAO userDAO = new UserDAO();
        boolean emailExists = userDAO.existsByEmailUpdate(user.getEmail());
        if (emailExists) {
            showErrorMessage("Користувач з таким email вже існує, використайте іншу.");
            throw new RuntimeException();
        } else {
            validateAndUpdate(userId, user);
            userDAO.update(userId, user);
        }
    }

    @Override
    public void delete(Long userId) {
        if (userId == null || userId <= 0) {
            System.out.println("Недійсний ідентифікатор користувача");
        } else {
            User existingUser = userDAO.findById(userId);
            if (existingUser == null) {
                System.out.println("Користувача з таким ідентифікатором не знайдено");
            } else {
                userDAO.delete(userId);
            }
        }
    }


    /**
     * Update user user.
     *
     * @param userId     the user id
     * @param firstName  the first name
     * @param lastName   the last name
     * @param email      the email
     * @param password   the password
     * @param userStatus the user status
     * @return the user
     */
    public User updateUser(
            Long userId,
            String firstName,
            String lastName,
            String email,
            String password,
            UserRole userStatus) {
        User user = new User();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRegisteredAt();
        user.setUserStatus(userStatus);
        return user;
    }


    /**
     * Save user user.
     *
     * @param firstName  the first name
     * @param lastName   the last name
     * @param email      the email
     * @param password   the password
     * @param userStatus the user status
     * @return the user
     */
    public User saveUser(
            String firstName, String lastName, String email, String password, String userStatus) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRegisteredAt();
        try {
            user.setUserStatus(UserRole.valueOf(userStatus));
        }
        catch (Exception exception)
        {
            showErrorMessage("Не валідний статус користувача(active|admin|inactive)");
        }
        return user;
    }
}
