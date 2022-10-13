package com.programming.technie.springredditclone.controller;

import com.programming.technie.springredditclone.dto.CommentDto;
import com.programming.technie.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static  org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@RequestParam("postId") Long postId){
        return status(OK).body(commentService.getCommentsByPost(postId));
    }


    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@RequestParam("userName") String userName){
        return status(OK).body(commentService.getCommentsByUser(userName));
    }


}
