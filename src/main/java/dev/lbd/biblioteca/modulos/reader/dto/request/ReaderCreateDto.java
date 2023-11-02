package dev.lbd.biblioteca.modulos.reader.dto.request;

import java.time.LocalDateTime;

public record ReaderCreateDto(
        String name,
        LocalDateTime dateBirth,
        String cpf
) {
}
