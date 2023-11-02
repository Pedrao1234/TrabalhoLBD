package dev.lbd.biblioteca.modulos.reader.dto.response;

import java.time.LocalDateTime;

public record ReaderResponseDto(
    String name,
    LocalDateTime dateBirth,
    String cpf,
    LocalDateTime createdAt,
    LocalDateTime lastModifiedAt
){}
