package dev.lbd.biblioteca.modulos.author.dto.response;

import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorResponseDto (

        UUID id,
        String name,
        LocalDateTime birthDate,
        String cpf,
        SexAuthor sex,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate

){
}