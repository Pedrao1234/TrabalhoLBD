package dev.lbd.biblioteca.modulos.author.repository;

import dev.lbd.biblioteca.modulos.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorPostgresRepository extends JpaRepository<AuthorEntity, UUID> {
}
