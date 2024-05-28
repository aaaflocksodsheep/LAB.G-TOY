package com.labgenomics.practice.api.service;

import com.labgenomics.practice.api.model.Board;
import com.labgenomics.practice.api.model.BoardComment;
import com.labgenomics.practice.api.model.User;

public interface BoardService {
    // Board 관련 메서드
    void insertBoard(Board board);
    Board selectBoardById(int boardId);
    void updateBoard(Board board);
    void hideBoard(int boardId); // 숨김 처리 메서드 추가

    // BoardComment 관련 메서드
    void insertBoardComment(BoardComment boardComment);
    BoardComment selectBoardCommentById(int boardCommentId);
    void updateBoardComment(BoardComment boardComment);
    void deleteBoardComment(int boardCommentId);

    // User 관련 메서드
    void insertUser(User user);
    User selectUserById(int userId);
    void updateUser(User user);
    void deleteUser(int userId);
}
