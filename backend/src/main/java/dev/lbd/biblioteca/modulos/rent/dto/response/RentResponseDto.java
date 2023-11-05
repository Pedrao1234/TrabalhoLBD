package dev.lbd.biblioteca.modulos.rent.dto.response;

import dev.lbd.biblioteca.modulos.book.BookEntity;
import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RentResponseDto (
        UUID id,
        ReaderEntity reader,
        List<BookEntity> book,
        LocalDateTime rentDate,
        LocalDateTime devolutionDate,
        StatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        LocalDateTime deletedDate
){
}
