package dev.lbd.biblioteca.modulos.rent.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.lbd.biblioteca.modulos.rent.QRentEntity;
import dev.lbd.biblioteca.modulos.rent.RentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class RentRepository {
    private final RentPostgresRepository rentPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public RentEntity save(RentEntity obj) {
        RentEntity rentEntity = rentPostgresRepository.save(obj);
        return rentEntity;
    }

    public Page<RentEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QRentEntity qEntity = QRentEntity.rentEntity;
        List<RentEntity> results = queryFactory.selectFrom(qEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qEntity)
                .from(qEntity)
                .where(predicate)
                .fetch()
                .stream().count();
        return new PageImpl<>(results,pageable,total);
    }

    public RentEntity findById(UUID id) {
        RentEntity rentEntity = rentPostgresRepository.findById(id).orElse(null);
        return rentEntity;
    }


    @Transactional
    public RentEntity update(RentEntity obj) {
        RentEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        rentPostgresRepository.deleteById(id);
    }

}

