package com.labgenomics.practice.api.service.impl;

import com.labgenomics.practice.api.mapper.TestMapper;
import com.labgenomics.practice.api.model.Board;
import com.labgenomics.practice.api.model.BoardComment;
import com.labgenomics.practice.api.model.User;
import com.labgenomics.practice.api.service.BoardService;
import com.labgenomics.practice.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TestMapper testMapper;

    @Override
    public void insertUser(User user) {
        testMapper.insertUser(user);
    }

    @Override
    public User selectUserById(int userId) {
        return testMapper.selectUserById(userId);
    }

    @Override
    public void updateUser(int userId, User updatedUser) {
        updatedUser.setUserIdx(userId);
        testMapper.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(int userId) {
        testMapper.deleteUser(userId);
    }
}