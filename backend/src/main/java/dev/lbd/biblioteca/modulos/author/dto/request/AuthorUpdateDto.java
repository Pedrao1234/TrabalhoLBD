package dev.lbd.biblioteca.modulos.author.dto.request;

import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;

import java.time.LocalDateTime;

public record AuthorUpdateDto(
        String name,
        LocalDateTime birthDate,
        String cpf,
        SexAuthor sex
) {
}
