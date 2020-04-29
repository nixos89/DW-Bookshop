package com.nikolas.master_thesis.mapstruct_mappers;

import com.nikolas.master_thesis.api.BookDTO2;
import com.nikolas.master_thesis.core.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {AuthorMSMapper.class, CategoryMSMapper.class})
public interface BookMSMapper {

    BookMSMapper INSTANCE = Mappers.getMapper(BookMSMapper.class);

    Book toBook(BookDTO2 bookDTO);

    @InheritInverseConfiguration
    BookDTO2 fromBook(Book book);

}
