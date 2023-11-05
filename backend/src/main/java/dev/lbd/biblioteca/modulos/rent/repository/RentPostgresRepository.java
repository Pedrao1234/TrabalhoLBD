package dev.lbd.biblioteca.modulos.rent.repository;

import dev.lbd.biblioteca.modulos.rent.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentPostgresRepository extends JpaRepository<RentEntity, UUID> {
}
