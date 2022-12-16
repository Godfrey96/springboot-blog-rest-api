package com.skomane.blog.controller;

import com.skomane.blog.dto.CommentDTO;
import com.skomane.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO){
        commentService.addComment(commentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-comments-by-post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId){
        return new ResponseEntity<>(commentService.getAllCommentsForPost(postId), HttpStatus.OK);
    }

    @GetMapping("/get-comments-by-user/{username}")
    public ResponseEntity<List<CommentDTO>> getALlCommentsForUser(@PathVariable String username){
        return new ResponseEntity<>(commentService.getAllCommentsByUsername(username), HttpStatus.OK);
    }
}
