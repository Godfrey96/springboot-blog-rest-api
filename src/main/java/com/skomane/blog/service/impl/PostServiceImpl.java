package com.skomane.blog.service.impl;

import com.skomane.blog.dto.PostDTO;
import com.skomane.blog.exception.PostNotFoundException;
import com.skomane.blog.model.Post;
import com.skomane.blog.repository.PostRepository;
import com.skomane.blog.service.AuthService;
import com.skomane.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final AuthService authService;
    private PostRepository postRepository;

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Override
    public void createNewPost(PostDTO postDTO) {
        Post post = mapFromDtoToPost(postDTO);
        postRepository.save(post);
    }

    @Override
    public PostDTO getSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found for " + id));
        return mapFromPostToDto(post);
    }

    private PostDTO mapFromPostToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setUsername(post.getUsername());
        return postDTO;
    }

    private Post mapFromDtoToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User not found"));
        post.setUsername(loggedInUser.getUsername());
        return post;
    }
}
