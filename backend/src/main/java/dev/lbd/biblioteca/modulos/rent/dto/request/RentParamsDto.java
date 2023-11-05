package dev.lbd.biblioteca.modulos.rent.dto.request;

import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record RentParamsDto(
        UUID readerId,
        UUID bookId,
        LocalDateTime rentDate,
        LocalDateTime devolutionDate,
        StatusEnum status
) {
}
