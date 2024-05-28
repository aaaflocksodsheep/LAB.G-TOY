package com.labgenomics.practice.api.mapper;

import com.labgenomics.practice.api.model.Board;
import com.labgenomics.practice.api.model.BoardComment;
import com.labgenomics.practice.api.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {
    // Board 관련 메서드
    void insertBoard(Board board);
    Board selectBoardById(@Param("boardId") int boardId);

    void updateBoard(Board board);
    void hideBoard(@Param("boardId") int boardId); // 삭제 대신 숨김 처리

    // BoardComment 관련 메서드
    void insertBoardComment(BoardComment boardComment);
    BoardComment selectBoardCommentById(@Param("boardCommentId") int boardCommentId);
    void updateBoardComment(BoardComment boardComment);
    void deleteBoardComment(@Param("boardCommentId") int boardCommentId);

    // User 관련 메서드
    void insertUser(User user);
    User selectUserById(@Param("userId") int userId);
    void updateUser(User user);
    void deleteUser(@Param("userId") int userId);
}