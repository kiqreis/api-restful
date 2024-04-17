package br.com.kiqreis.apirestfulsb.controllers;

import br.com.kiqreis.apirestfulsb.dtos.BookDto;
import br.com.kiqreis.apirestfulsb.models.Book;
import br.com.kiqreis.apirestfulsb.services.BookService;
import br.com.kiqreis.apirestfulsb.utils.BookCreator;
import br.com.kiqreis.apirestfulsb.utils.BookDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.bookUsage()));

    @BeforeEach
    void setUp() {
        BDDMockito.given(bookService.findAll(ArgumentMatchers.any(Pageable.class)))
                .willReturn(bookPage);

        BDDMockito.given(bookService.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(BookCreator.bookUsage());

        BDDMockito.given(bookService.save(ArgumentMatchers.any(BookDto.class)))
                .willReturn(BookCreator.bookUsage());

        BDDMockito.given(bookService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BookDto.class)))
                .willReturn(BookCreator.bookUsageToBeUpdated());

        BDDMockito.willDoNothing().given(bookService).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("findAll should return a page of books when successful")
    void findAllShouldReturnAPageOfPeopleWhenSuccessful() {
        String expectedAuthor = BookCreator.bookUsage().getAuthor();

        Page<Book> bookPage = bookController.findAll(Pageable.ofSize(1)).getBody();

        Assertions.assertThat(bookPage).isNotNull();

        Assertions.assertThat(bookPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(bookPage.toList().getFirst().getAuthor())
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("findById should return a book when successful")
    void findByIdShouldReturnABookWhenSuccessful() {
        UUID expectedId = BookCreator.bookUsage().getId();

        Book book = bookController.findById(UUID.randomUUID()).getBody();

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save should return a book when the save is successful")
    void saveShouldReturnAPersonWhenSuccessful() {
        Book book = bookController.save(BookDtoCreator.bookDtoSave()).getBody();

        Assertions.assertThat(book.getAuthor())
                .isNotBlank()
                .isEqualTo(BookCreator.bookSave().getAuthor());

        Assertions.assertThat(book.getLaunchDate()).isNotNull()
                .isEqualTo(BookCreator.bookSave().getLaunchDate());

        Assertions.assertThat(book.getPrice()).isNotNegative()
                .isEqualTo(BookCreator.bookSave().getPrice());

        Assertions.assertThat(book.getTitle()).isNotBlank()
                .isEqualTo(BookCreator.bookSave().getTitle());

        Assertions.assertThat(book).isNotNull()
                .isEqualTo(BookCreator.bookUsage());
    }

    @Test
    @DisplayName("update should return a book when the update is successful")
    void updateShouldReturnABookWhenSuccessful() {
        Book book = bookController.update(UUID.randomUUID(), BookDtoCreator.BookDtoUsageToBeUpdated()).getBody();

        Assertions.assertThat(book.getAuthor()).isNotBlank()
                .isEqualTo(BookCreator.bookUsageToBeUpdated().getAuthor());

        Assertions.assertThat(book.getTitle()).isNotBlank()
                .isEqualTo(BookCreator.bookUsageToBeUpdated().getTitle());

        Assertions.assertThat(book.getLaunchDate()).isNotNull()
                .isEqualTo(BookCreator.bookUsageToBeUpdated().getLaunchDate());

        Assertions.assertThat(book.getPrice()).isNotNegative()
                .isEqualTo(BookCreator.bookUsageToBeUpdated().getPrice());

        Assertions.assertThat(book).isNotNull()
                .isEqualTo(BookCreator.bookUsageToBeUpdated());

        Assertions.assertThatCode(() -> bookController.update(UUID.randomUUID(), BookDtoCreator.BookDtoUsageToBeUpdated()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete should removes book when successful")
    void shouldRemovesBookWhenSuccessful() {
        ResponseEntity<Void> voidResponseEntity = bookController.deleteById(UUID.randomUUID());

        Assertions.assertThat(voidResponseEntity).isNotNull();

        Assertions.assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(voidResponseEntity.getBody()).isNull();

        Assertions.assertThatCode(() -> bookController.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();
    }
}