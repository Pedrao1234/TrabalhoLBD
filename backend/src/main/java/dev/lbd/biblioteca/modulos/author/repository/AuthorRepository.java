package dev.lbd.biblioteca.modulos.author.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.lbd.biblioteca.modulos.author.QAuthorEntity;
import dev.lbd.biblioteca.modulos.author.AuthorEntity;
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
public class AuthorRepository {
    private final AuthorPostgresRepository authorPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorEntity save(AuthorEntity obj) {
        AuthorEntity authorEntity = authorPostgresRepository.save(obj);
        return authorEntity;
    }

    public Page<AuthorEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QAuthorEntity qEntity = QAuthorEntity.authorEntity;
        List<AuthorEntity> results = queryFactory.selectFrom(qEntity)
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

    public AuthorEntity findById(UUID id) {
        AuthorEntity authorEntity = authorPostgresRepository.findById(id).orElse(null);
        return authorEntity;
    }


    @Transactional
    public AuthorEntity update(AuthorEntity obj) {
        AuthorEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        authorPostgresRepository.deleteById(id);
    }

}

