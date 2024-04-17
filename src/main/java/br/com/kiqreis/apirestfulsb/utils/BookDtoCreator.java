package br.com.kiqreis.apirestfulsb.utils;

import br.com.kiqreis.apirestfulsb.dtos.BookDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookDtoCreator {

    public static BookDto bookDtoSave() {
        return BookDto.builder()
                .author("Euclid")
                .launchDate(LocalDateTime.of(-300, 1, 1, 0, 0))
                .price(BigDecimal.valueOf(1000))
                .title("Euclid's Elements")
                .build();
    }

    public static BookDto BookDtoUsageToBeUpdated() {
        return BookDto.builder()
                .author(BookCreator.bookUsageToBeUpdated().getAuthor())
                .launchDate(BookCreator.bookUsageToBeUpdated().getLaunchDate())
                .price(BookCreator.bookUsageToBeUpdated().getPrice())
                .title(BookCreator.bookUsageToBeUpdated().getTitle())
                .build();
    }
}
