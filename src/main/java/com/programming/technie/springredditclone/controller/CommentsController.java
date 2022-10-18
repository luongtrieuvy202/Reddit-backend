package com.programming.technie.springredditclone.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static  org.springframework.http.ResponseEntity.status;

import java.util.List;

import com.programming.technie.springredditclone.dto.CommentDto;
import com.programming.technie.springredditclone.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@RequestParam("postId") Long postId){
        return status(OK).body(commentService.getCommentsByPost(postId));
    }


    @GetMapping("/by-post/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@RequestParam("userName") String userName){
        return status(OK).body(commentService.getCommentsByUser(userName));
    }


}
