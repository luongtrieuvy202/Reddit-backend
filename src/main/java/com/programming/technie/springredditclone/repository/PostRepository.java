package com.programming.technie.springredditclone.repository;

import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.model.Subreddit;
import com.programming.technie.springredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);

}