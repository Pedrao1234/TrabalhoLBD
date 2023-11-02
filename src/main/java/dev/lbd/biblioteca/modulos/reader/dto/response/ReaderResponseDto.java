package dev.lbd.biblioteca.modulos.reader.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReaderResponseDto(

    UUID id,
    String name,
    LocalDateTime dateBirth,
    String cpf,
    LocalDateTime createdAt,
    LocalDateTime lastModifiedAt,
    LocalDateTime deletedDate

){}
