//package com.skomane.blog.service.impl;
//
//import com.skomane.blog.dto.CommentDTO;
//import com.skomane.blog.exception.PostNotFoundException;
//import com.skomane.blog.model.Comment;
//import com.skomane.blog.model.NotificationEmail;
//import com.skomane.blog.model.Post;
//import com.skomane.blog.model.User;
//import com.skomane.blog.repository.CommentRepository;
//import com.skomane.blog.repository.PostRepository;
//import com.skomane.blog.repository.UserRepository;
//import com.skomane.blog.service.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CommentServiceImpl implements CommentService {
//
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//    private CommentRepository commentRepository;
//    private AuthService authService;
//    private MailService mailService;
//    private MailContentBuilder mailContentBuilder;
//
//
//    @Override
//    public void addComment(CommentDTO commentDTO) {
//        Post post = postRepository.findById(commentDTO.getId())
//                .orElseThrow(() -> new PostNotFoundException("Post not found for id " + commentDTO.getPostId().toString()));
////        Comment comment = new Comment()
//    }
//
//    @Override
//    public void sendCommentNotification(String message, User user) {
//        mailService.sendMail(new NotificationEmail(user.getUserName() + " Commented on your post", user.getEmail(), message));
//    }
//
//    @Override
//    public List<CommentDTO> getAllCommentsForPost(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(() ->
//                new PostNotFoundException("Post not found for " + postId.toString()));
//        return commentRepository.findByPost(post)
//                .stream()
//                .map;
//    }
//
//    @Override
//    public List<CommentDTO> getAllCommentsByUsername(String username) {
//        return null;
//    }
//}
