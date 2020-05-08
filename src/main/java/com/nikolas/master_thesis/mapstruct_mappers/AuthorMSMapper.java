package com.nikolas.master_thesis.mapstruct_mappers;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.core.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface AuthorMSMapper {

    AuthorMSMapper INSTANCE = Mappers.getMapper(AuthorMSMapper.class);

    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorDTO authorDTO);

    AuthorDTO fromAuthor(Author author);

}
