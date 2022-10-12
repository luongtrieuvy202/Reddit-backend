package com.programming.technie.springredditclone.repository;

import com.programming.technie.springredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(Subreddit subredditName);
}
