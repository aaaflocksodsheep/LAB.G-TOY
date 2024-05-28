package com.labgenomics.practice.api.service;
import com.labgenomics.practice.api.model.User;

public interface UserService {
    void insertUser(User user);
    User selectUserById(int userId);
    void updateUser(int userId, User user);
    void deleteUser(int userId);
}