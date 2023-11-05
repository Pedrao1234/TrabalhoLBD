package dev.lbd.biblioteca.modulos.rent.dto.response;

import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record RentResponseDto (

        UUID readerId,
        UUID bookId,
        LocalDateTime rentDate,
        LocalDateTime devolutionDate,
        StatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate
){
}
