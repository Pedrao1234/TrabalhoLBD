package dev.lbd.biblioteca.modulos.book.dto.request;

import java.time.LocalDateTime;

public record BookParamsDto(
        String title,
        LocalDateTime releaseDate,
        String publisher,
        String summary
) {
}
