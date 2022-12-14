package com.skomane.blog.service;

import com.skomane.blog.dto.CommentDTO;
import com.skomane.blog.model.User;

import java.util.List;

public interface CommentService {
    void addComment(CommentDTO commentDTO);
    void sendCommentNotification(String message, User user);
    List<CommentDTO> getAllCommentsForPost(Long postId);
    List<CommentDTO> getAllCommentsByUsername(String username);
}
