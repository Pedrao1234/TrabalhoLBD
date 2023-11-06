package dev.lbd.biblioteca.modulos.bookAuthor.dto.request;

import dev.lbd.biblioteca.modulos.bookAuthor.enums.StatusBook;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookAuthorUpdateDto(
        UUID bookId,
        UUID authorId
) {
}
