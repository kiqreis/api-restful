package br.com.kiqreis.apirestfulsb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book extends RepresentationModel<Person> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String author;

    @Column(name = "launch_date", nullable = false)
    private LocalDateTime launchDate;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal price;

    @NotBlank
    @Column(nullable = false)
    private String title;

    public Book() {
    }

    public Book(UUID id, String author, LocalDateTime launchDate, BigDecimal price, String title) {
        this.id = id;
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
        this.title = title;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {

        private UUID id;
        private String author;
        private LocalDateTime launchDate;
        private BigDecimal price;
        private String title;

        public BookBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder launchDate(LocalDateTime launchDate) {
            this.launchDate = launchDate;
            return this;
        }

        public BookBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Book build() {
            return new Book(id, author, launchDate, price, title);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", launch_date=" + launchDate +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
