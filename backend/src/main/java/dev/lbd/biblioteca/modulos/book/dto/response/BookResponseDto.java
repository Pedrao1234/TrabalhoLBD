package dev.lbd.biblioteca.modulos.book.dto.response;

import dev.lbd.biblioteca.modulos.book.enums.StatusBook;
import dev.lbd.biblioteca.modulos.rent.RentEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponseDto (

        UUID id,
        String title,
        LocalDateTime releaseDate,
        String publisher,
        String summary,
        StatusBook status,
        RentEntity rent,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate

){
}
