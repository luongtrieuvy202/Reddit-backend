package com.programming.technie.springredditclone.controller;


import com.programming.technie.springredditclone.dto.SubredditDto;
import com.programming.technie.springredditclone.repository.SubredditRepository;
import com.programming.technie.springredditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import javax.persistence.SqlResultSetMapping;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {
    private final SubredditService subredditService;
    
    @GetMapping
    public List<SubredditDto> getAllSubreddits(){
        return subredditService.getAll();
    }
    
    @GetMapping("/{id}")
    public SubredditDto getSubreddit(@PathVariable Long id){
        return subredditService.getSubreddit(id);
    }
    
    
    @PostMapping
    public SubredditDto create(@RequestBody @Valid SubredditDto subredditDto){
        return subredditService.save(subredditDto);
    }
}
