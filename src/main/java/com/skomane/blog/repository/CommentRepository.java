package com.skomane.blog.repository;

import com.skomane.blog.model.Comment;
import com.skomane.blog.model.Post;
import com.skomane.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);

    List<Comment> findByPost(Post post);
}
