package br.com.kiqreis.apirestfulsb.services;

import br.com.kiqreis.apirestfulsb.controllers.BookController;
import br.com.kiqreis.apirestfulsb.dtos.BookDto;
import br.com.kiqreis.apirestfulsb.mapper.BookMapper;
import br.com.kiqreis.apirestfulsb.models.Book;
import br.com.kiqreis.apirestfulsb.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Book save(BookDto bookDto) {
        return bookRepository.save(BookMapper.INSTANCE.toBook(bookDto));
    }

    public Book findById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book id not found"));

        book.add(linkTo(BookController.class).withRel("Book list"));

        return book;
    }

    public Page<Book> findAll(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        if (!bookPage.isEmpty()) {
            for (Book book : bookPage.getContent()) {
                book.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel());
            }
        }

        return bookPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public Book update(UUID id, BookDto bookDto) {
        Book bookUpdate = findById(id);

        Book book = BookMapper.INSTANCE.toBook(bookDto);
        book.setId(bookUpdate.getId());

        return bookRepository.save(book);
    }

    public void deleteById(UUID id) {
        Book book = findById(id);
        bookRepository.deleteById(book.getId());
    }
}
