package br.com.kiqreis.apirestfulsb.mapper;

import br.com.kiqreis.apirestfulsb.dtos.BookDto;
import br.com.kiqreis.apirestfulsb.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public abstract Book toBook(BookDto BookDto);
}
