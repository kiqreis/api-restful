package br.com.kiqreis.apirestfulsb.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String author;

    private LocalDateTime launchDate;
    private BigDecimal price;

    @NotBlank
    private String title;

    public BookDto(String author, LocalDateTime launchDate, BigDecimal price, String title) {
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
        this.title = title;
    }

    public static BookDtoBuilder builder() {
        return new BookDtoBuilder();
    }

    public static class BookDtoBuilder {

        private String author;
        private LocalDateTime launchDate;
        private BigDecimal price;
        private String title;

        public BookDtoBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookDtoBuilder launchDate(LocalDateTime launchDate) {
            this.launchDate = launchDate;
            return this;
        }

        public BookDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BookDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDto build() {
            return new BookDto(author, launchDate, price, title);
        }
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
}
