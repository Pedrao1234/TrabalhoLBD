package dev.lbd.biblioteca.modulos.book.dto.request;

import dev.lbd.biblioteca.modulos.book.enums.StatusBook;

import java.time.LocalDateTime;

public record BookUpdateDto(
        String title,
        LocalDateTime releaseDate,
        String publisher,
        String summary,

        StatusBook status
) {
}
