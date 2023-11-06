package dev.lbd.biblioteca.modulos.bookAuthor.dto.response;

import dev.lbd.biblioteca.modulos.author.AuthorEntity;
import dev.lbd.biblioteca.modulos.book.BookEntity;
import dev.lbd.biblioteca.modulos.bookAuthor.enums.StatusBook;
import dev.lbd.biblioteca.modulos.rent.RentEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookAuthorResponseDto (

        UUID id,
        BookEntity book,
        AuthorEntity author,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate

){
}
