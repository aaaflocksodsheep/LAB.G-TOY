package com.labgenomics.practice.api.service.impl;

import com.labgenomics.practice.api.mapper.TestMapper;
import com.labgenomics.practice.api.model.Board;
import com.labgenomics.practice.api.model.BoardComment;
import com.labgenomics.practice.api.model.User;
import com.labgenomics.practice.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final TestMapper testMapper;

    // Board 관련 메서드
    @Override
    public void insertBoard(Board board) {
        testMapper.insertBoard(board);
    }

    @Override
    public Board selectBoardById(int boardId) {
        return testMapper.selectBoardById(boardId);
    }

    @Override
    public void updateBoard(Board board) {
        testMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardId) {
        testMapper.deleteBoard(boardId);
    }

    // BoardComment 관련 메서드
    @Override
    public void insertBoardComment(BoardComment boardComment) {
        testMapper.insertBoardComment(boardComment);
    }

    @Override
    public BoardComment selectBoardCommentById(int boardCommentId) {
        return testMapper.selectBoardCommentById(boardCommentId);
    }

    @Override
    public void updateBoardComment(BoardComment boardComment) {
        testMapper.updateBoardComment(boardComment);
    }

    @Override
    public void deleteBoardComment(int boardCommentId) {
        testMapper.deleteBoardComment(boardCommentId);
    }

    // User 관련 메서드
    @Override
    public void insertUser(User user) {
        testMapper.insertUser(user);
    }

    @Override
    public User selectUserById(int userId) {
        return testMapper.selectUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        testMapper.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        testMapper.deleteUser(userId);
    }
}