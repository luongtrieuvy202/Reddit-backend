package com.programming.technie.springredditclone.service;

import com.programming.technie.springredditclone.dto.VoteDto;
import com.programming.technie.springredditclone.exception.PostNotFoundException;
import com.programming.technie.springredditclone.exception.SpringRedditException;
import com.programming.technie.springredditclone.model.Post;
import com.programming.technie.springredditclone.model.Vote;
import com.programming.technie.springredditclone.repository.PostRepository;
import com.programming.technie.springredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static  com.programming.technie.springredditclone.model.VoteType.UPVOTE;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException(voteDto.getPostId().toString()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already" + voteDto.getVoteType() + " this post");
        }

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }


        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }


    private Vote mapToVote(VoteDto voteDto, Post post){
      return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
    }
}
