package com.fanta.service;

import com.fanta.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {
    Optional<User> getById(Long userId);

    List<User> getAll();

    User add(User user);

    User update(User user);

    void delete(User user);
}
