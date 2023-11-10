package dev.lbd.biblioteca.modulos.bookAuthor.dto.request;

import dev.lbd.biblioteca.modulos.bookAuthor.enums.StatusAuthor;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookAuthorCreateDto(

        UUID bookId,
        UUID authorId,

        StatusAuthor status
) {
}
