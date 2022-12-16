package com.skomane.blog.service.impl;

import com.skomane.blog.dto.CommentDTO;
import com.skomane.blog.exception.PostNotFoundException;
import com.skomane.blog.exception.UsernameNotFoundException;
import com.skomane.blog.mapper.CommentMapper;
import com.skomane.blog.model.Comment;
import com.skomane.blog.model.NotificationEmail;
import com.skomane.blog.model.Post;
import com.skomane.blog.model.User;
import com.skomane.blog.repository.CommentRepository;
import com.skomane.blog.repository.PostRepository;
import com.skomane.blog.repository.UserRepository;
import com.skomane.blog.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;


    @Override
    public void addComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getId())
                .orElseThrow(() -> new PostNotFoundException("Post not found for id " + commentDTO.getPostId().toString()));
        Comment comment = commentMapper.map(commentDTO, post, authService.getCurrentUser().get());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUserName() + " posted a comment on your post.");
        sendCommentNotification(message, post.getUser());
    }

    @Override
    public void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUserName() + " Commented on your post", user.getEmail(), message));
    }

    @Override
    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post not found for " + postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllCommentsByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());

    }
}
