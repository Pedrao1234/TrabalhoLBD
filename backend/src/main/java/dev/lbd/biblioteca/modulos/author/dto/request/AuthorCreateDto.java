package dev.lbd.biblioteca.modulos.author.dto.request;

import java.time.LocalDateTime;

import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;

public record AuthorCreateDto(
        String name,
        LocalDateTime birthDate,
        String cpf,
        SexAuthor sex
) {
}
