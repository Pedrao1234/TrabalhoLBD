package dev.lbd.biblioteca.modulos.reader.dto.request;

import java.time.LocalDateTime;

public record ReaderUpdateDto(
        String name,
        LocalDateTime dateBirth,
        String cpf
) {}
