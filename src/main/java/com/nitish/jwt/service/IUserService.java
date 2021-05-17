package com.nitish.jwt.service;

import com.nitish.jwt.model.User;

import java.util.Optional;

public interface IUserService {

    public Integer saveUser(User user);
    Optional<User> findByUserName(String username);

}
