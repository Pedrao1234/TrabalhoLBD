package dev.lbd.biblioteca.modulos.rent.dto.request;

import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RentCreateDto(
        UUID readerId,
        List<UUID> bookId,
        LocalDateTime rentDate,
        LocalDateTime devolutionDate
) {
}
