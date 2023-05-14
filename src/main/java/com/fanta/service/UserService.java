package com.fanta.service;

import com.fanta.dao.UserDAO;
import com.fanta.entity.Budget;
import com.fanta.entity.User;


import java.util.List;

import javafx.fxml.LoadException;

public class UserService implements ServiceInterface<User> {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    @Override
    public User getById(Long userId) {
        if (userId == null || userId <= 0) {
            System.out.println("Недійсний ідентифікатор користувача");
        }
        else {
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
        ServiceInterface validatorService = new UserService();
        validatorService.validateAndSave(user);
        userDAO.save(user);
    }

    @Override
    public void update(Long userId, User user) {
        ServiceInterface validatorService = new UserService();
        validatorService.validateAndUpdate(userId, user);
        userDAO.update(userId, user);
    }

     @Override
    public void delete(Long userId) {
        if (userId == null || userId <= 0) {
            System.out.println("Недійсний ідентифікатор користувача");
        } else {
            User existingUser =  userDAO.findById(userId);
            if (existingUser == null) {
                System.out.println("Користувача з таким ідентифікатором не знайдено");
            } else {
                userDAO.delete(userId);
            }
        }
    }
        public User updateUser1(Long userId, String firstName, String lastName, String email, String password, String userStatus) {
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
    public User createUser(String firstName, String lastName, String email, String password, String userStatus) {
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

