package br.com.kiqreis.apirestfulsb.repositories;

import br.com.kiqreis.apirestfulsb.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
