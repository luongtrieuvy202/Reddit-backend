package com.programming.technie.springredditclone.controller;
import com.programming.technie.springredditclone.dto.PostRequest;
import com.programming.technie.springredditclone.dto.PostResponse;
import com.programming.technie.springredditclone.model.Post;
import com.programming.technie.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return  status(OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id){
        return status(OK).body(postService.getPostsBySubreddit(id));
    }


    @GetMapping("/by-username/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String userName){
        return status(OK).body(postService.getPostsByUsername(userName));
    }
}
