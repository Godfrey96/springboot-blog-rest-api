package com.skomane.blog.mapper;

import com.skomane.blog.dto.PostDTO;
import com.skomane.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "title", source = "postRequestDTO.title")
    @Mapping(target = "content", source = "postRequestDTO.content")
    @Mapping(target = "username", source = "postRequestDTO.username")
    public abstract Post mapFromPostToDto(PostDTO postRequestDTO);
}
