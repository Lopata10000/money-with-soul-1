package com.fanta.service;

import com.fanta.dao.UserDAO;
import com.fanta.entity.User;

import java.util.List;

public class UserService implements Service<User> {
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
        userDAO.save(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
}

