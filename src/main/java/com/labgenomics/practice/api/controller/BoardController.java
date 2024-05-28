package com.labgenomics.practice.api.controller;

import com.labgenomics.practice.api.model.Board;
import com.labgenomics.practice.api.model.BoardComment;
import com.labgenomics.practice.api.model.User;
import com.labgenomics.practice.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.labgenomics.practice.api.service.UserService;


@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    // 게시글 작성
    @PostMapping("/board")
    public void createBoard(@RequestBody Board board) {
        boardService.insertBoard(board);
    }

    // 게시글 조회
    @GetMapping("/board/{boardId}")
    public Board getBoardById(@PathVariable int boardId) {
        return boardService.selectBoardById(boardId);
    }

    // 게시글 수정
    @PutMapping("/board/{boardId}")
    public void updateBoard(@PathVariable int boardId, @RequestBody Board board) {
        board.setBoardId(boardId);
        boardService.updateBoard(board);
    }

    // 게시글 삭제 (숨김 처리)
    @DeleteMapping("/board/{boardId}")
    public void deleteBoard(@PathVariable int boardId) {
        boardService.hideBoard(boardId);
    }

    // 댓글 작성
    @PostMapping("/board/{boardId}/comment")
    public void createBoardComment(@PathVariable int boardId, @RequestBody BoardComment comment) {
        comment.setBoardId(boardId);
        boardService.insertBoardComment(comment);
    }

    // 사용자 등록
    @PostMapping("/user")
    public void createUser(@RequestBody User user) {
        userService.insertUser(user);
    }

    // 사용자 조회
    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.selectUserById(userId);
    }

    // 사용자 수정
    @PutMapping("/user/{userId}")
    public void updateUser(@PathVariable int userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }

    // 사용자 삭제
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }
}