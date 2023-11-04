package dev.lbd.biblioteca.modulos.book.dto.response;

import java.time.LocalDateTime;

public record BookResponseDto (

        String title,
        LocalDateTime releaseDate,
        String publisher,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate

){
}
