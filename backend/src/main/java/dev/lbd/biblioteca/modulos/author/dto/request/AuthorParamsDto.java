package dev.lbd.biblioteca.modulos.author.dto.request;

import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;

import java.time.LocalDateTime;

public record AuthorParamsDto(
        String name,
        LocalDateTime birthDate,
        SexAuthor sex

) {
}
