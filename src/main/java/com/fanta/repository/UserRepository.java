package com.fanta.repository;

import com.fanta.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {
    private List<User> userList = new ArrayList<>();

    @Override
    public List<User> getAll() {
        return userList;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userList.stream().filter(user -> user.getUserId().equals(id)).findFirst();
    }

    @Override
    public  User update(User user) {
        Optional<User> existingUser = getById(user.getUserId());
        if (existingUser.isPresent()) {
            int index = userList.indexOf(existingUser.get());
            userList.set(index, user);
        } else {
            throw new RuntimeException("Person with ID " + user.getUserId() + " not found");
        }
        return user;
    }

    @Override
    public void add(User user) {
        if ((user.getEmail()).equals(this)) {
            throw new RuntimeException("Person already exists");
        }
        userList.add(user);
    }

    @Override
    public void delete(User user) {
        userList.remove(user);
    }
}
