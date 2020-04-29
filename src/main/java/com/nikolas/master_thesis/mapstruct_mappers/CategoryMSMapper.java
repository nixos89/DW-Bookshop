package com.nikolas.master_thesis.mapstruct_mappers;

import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface CategoryMSMapper {

    CategoryMSMapper INSTANCE = Mappers.getMapper(CategoryMSMapper.class);

    CategoryDTO fromCategory(Category category);

    @Mapping(source = "deleted", target="deleted")
    @Mapping(target = "books", ignore = true)
    Category toCategory(CategoryDTO categoryDTO);
}
