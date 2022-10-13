package com.programming.technie.springredditclone.exception;

import com.programming.technie.springredditclone.model.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
