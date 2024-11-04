package com.github.juli220620.mapper;

import com.github.juli220620.model.BookDto;
import com.github.juli220620.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookEntityMapper {

    BookEntity dtoToEntity(BookDto dto);

    BookDto entityToDto(BookEntity entity);

    BookDto dtoToDto(BookDto source, @MappingTarget BookDto target);
}
