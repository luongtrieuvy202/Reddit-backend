package com.programming.technie.springredditclone.mapper;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.technie.springredditclone.dto.PostRequest;
import com.programming.technie.springredditclone.dto.PostResponse;
import com.programming.technie.springredditclone.model.Post;
import com.programming.technie.springredditclone.model.Subreddit;
import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.repository.CommentRepository;
import com.programming.technie.springredditclone.repository.VoteRepository;
import com.programming.technie.springredditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target="createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target ="voteCount", constant = "0")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target="id" , source = "postId")
    @Mapping(target = "subredditName", source = "java(getSubredditName(post))")
    @Mapping(target = "userName", source = "java(getUsername(post))")
    @Mapping(target="duration" , expression = "java(getDuration(post))")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    public abstract PostResponse mapToDto(Post post);
    String getUsername(Post post){
        return post.getUser().getUsername();
    }

    String getSubredditName(Post post){
        return post.getSubreddit().getName();
    }

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }


}
