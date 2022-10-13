package com.programming.technie.springredditclone.exception;

import com.programming.technie.springredditclone.model.Post;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message){
        super(message);
    }
}
