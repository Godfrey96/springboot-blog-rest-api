package com.skomane.blog.controller;

import com.skomane.blog.dto.PostDTO;
import com.skomane.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDTO postDTO){
        postService.createNewPost(postDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get-single-post/{id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getSinglePost(id), HttpStatus.OK);
    }

}
