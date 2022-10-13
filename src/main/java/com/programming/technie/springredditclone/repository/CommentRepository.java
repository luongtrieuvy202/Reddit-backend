package com.programming.technie.springredditclone.repository;

import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.model.Comment;
import com.programming.technie.springredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long>{
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
