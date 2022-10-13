package com.programming.technie.springredditclone.service;

import com.programming.technie.springredditclone.dto.PostRequest;
import com.programming.technie.springredditclone.dto.PostResponse;
import com.programming.technie.springredditclone.exception.PostNotFoundException;
import com.programming.technie.springredditclone.exception.SubredditNotFoundException;
import com.programming.technie.springredditclone.exception.UserNotFoundException;
import com.programming.technie.springredditclone.mapper.PostMapper;
import com.programming.technie.springredditclone.model.Post;
import com.programming.technie.springredditclone.model.Subreddit;
import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.repository.PostRepository;
import com.programming.technie.springredditclone.repository.SubredditRepository;
import com.programming.technie.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostService{
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    public void save(PostRequest postRequest){
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit,authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->  new PostNotFoundException("Not found post with id : " + id));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditNotFoundException("Not found subreddit with id :" + id));

        return postRepository.findAllBySubreddit(subreddit).stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Not found user with name :" + username));

        return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());
    }








}