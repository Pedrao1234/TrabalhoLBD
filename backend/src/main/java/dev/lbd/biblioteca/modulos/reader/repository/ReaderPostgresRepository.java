package dev.lbd.biblioteca.modulos.reader.repository;

import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReaderPostgresRepository extends JpaRepository<ReaderEntity, UUID> {
}
