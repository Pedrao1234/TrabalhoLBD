package dev.lbd.biblioteca.modulos.reader.dto.request;

import java.time.LocalDateTime;

public record ReaderParamsDto(
        String name,
        String cpf,
        LocalDateTime dateBirth
) {}
