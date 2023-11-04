package dev.lbd.biblioteca.modulos.book.repository;

import dev.lbd.biblioteca.modulos.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookPostgresRepository extends JpaRepository<BookEntity, UUID> {
}
