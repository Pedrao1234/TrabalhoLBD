package dev.lbd.biblioteca.modulos.rent.dto.request;

import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record RentUpdateDto(
        StatusEnum status
) {
}
