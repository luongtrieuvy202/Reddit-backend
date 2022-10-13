package com.programming.technie.springredditclone.service;

import com.programming.technie.springredditclone.dto.CommentDto;
import com.programming.technie.springredditclone.exception.PostNotFoundException;
import com.programming.technie.springredditclone.exception.UserNotFoundException;
import com.programming.technie.springredditclone.mapper.CommentMapper;
import com.programming.technie.springredditclone.model.Comment;
import com.programming.technie.springredditclone.model.NotificationEmail;
import com.programming.technie.springredditclone.model.Post;
import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.repository.CommentRepository;
import com.programming.technie.springredditclone.repository.PostRepository;
import com.programming.technie.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    private static final String POST_URL = "";
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

//        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
//        sendCommentNotification(message, post.getUser());
    }

    public List<CommentDto> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUser(String userName){
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }



    private void sendCommentNotification(String message, User user){
        mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(),message));
    }
}
