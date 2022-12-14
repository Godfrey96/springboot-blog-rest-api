package com.skomane.blog.service;

import com.skomane.blog.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    void createNewPost(PostDTO postDTO);
    PostDTO getSinglePost(Long id);
}
