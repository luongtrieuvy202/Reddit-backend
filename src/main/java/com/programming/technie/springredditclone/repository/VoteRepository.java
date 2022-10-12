package com.programming.technie.springredditclone.repository;

import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.model.Vote;
import com.programming.technie.springredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends  JpaRepository<Vote,Long>{
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post,User user);
}
