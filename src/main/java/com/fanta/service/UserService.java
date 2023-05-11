package com.fanta.service;

import com.fanta.dao.UserDAO;
import com.fanta.entity.User;

import java.util.List;

public class UserService implements ServiceInterface<User> {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    @Override
    public User getById(Long userId) {
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
    public void update(User user) {
        ServiceInterface validatorService = new UserService();
        validatorService.validateAndUpdate(user);
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
    public User updateUser(String firstName, String lastName, String email, String password, String userStatus) {
        User user = new User();
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

