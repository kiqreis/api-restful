package br.com.kiqreis.apirestfulsb.utils;

import br.com.kiqreis.apirestfulsb.models.Book;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookCreator {

    private static final UUID id = UUID.randomUUID();

    public static Book bookSave() {
        return Book.builder()
                .author("Euclid")
                .launchDate(LocalDateTime.of(-300, 1, 1, 0, 0))
                .price(BigDecimal.valueOf(1000))
                .title("Euclid's Elements")
                .build();
    }

    public static Book bookUsage() {
        return Book.builder()
                .id(id)
                .author("Euclid")
                .launchDate(LocalDateTime.of(-300, 1, 1, 0, 0))
                .price(BigDecimal.valueOf(1000))
                .title("Euclid's Elements")
                .build();
    }

    public static Book bookUsageToBeUpdated() {
        return Book.builder()
                .id(id)
                .author("Euclides")
                .launchDate(LocalDateTime.of(-300, 1, 1, 0, 0))
                .price(BigDecimal.valueOf(1000))
                .title("Os elementos")
                .build();
    }
}
