package com.skomane.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
}
