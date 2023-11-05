package dev.lbd.biblioteca.modulos.book.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponseDto (

        UUID id,
        String title,
        LocalDateTime releaseDate,
        String publisher,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate

){
}
