package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.User;
import java.util.List;

public class UserService implements ServiceInterface<User> {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    @Override
    public User getById(Long userId) {
        if (userId == null || userId <= 0) {
            System.out.println("Недійсний ідентифікатор користувача");
        } else {
            User user = userDAO.findById(userId);
            if (user == null) {
                System.out.println("Користувача з таким ідентифікатором не знайдено");
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
        } else {
            validateAndSave(user);
            userDAO.save(user);
        }
    }

    @Override
    public void update(Long userId, User user) {
        UserDAO userDAO = new UserDAO();
        boolean emailExists = userDAO.existsByEmail(user.getEmail());
        if (emailExists) {
            showErrorMessage("Користувач з таким email вже існує, використайте іншу.");
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

    public User updateUser(
            Long userId,
            String firstName,
            String lastName,
            String email,
            String password,
            String userStatus) {
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

    public User saveUser(
            String firstName, String lastName, String email, String password, String userStatus) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRegisteredAt();
        user.setUserStatus(userStatus);
        return user;
    }
}
