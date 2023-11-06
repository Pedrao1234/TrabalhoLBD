package dev.lbd.biblioteca.modulos.bookAuthor.repository;

import dev.lbd.biblioteca.modulos.bookAuthor.BookAuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookAuthorPostgresRepository extends JpaRepository<BookAuthorEntity, UUID> {
}
