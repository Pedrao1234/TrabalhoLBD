package dev.lbd.biblioteca.modulos.bookAuthor.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.lbd.biblioteca.modulos.bookAuthor.BookAuthorEntity;
import dev.lbd.biblioteca.modulos.bookAuthor.QBookAuthorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class BookAuthorRepository {
    private final BookAuthorPostgresRepository bookAuthorPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public BookAuthorEntity save(BookAuthorEntity obj) {
        BookAuthorEntity bookAuthorEntity = bookAuthorPostgresRepository.save(obj);
        return bookAuthorEntity;
    }

    public Page<BookAuthorEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QBookAuthorEntity qEntity = QBookAuthorEntity.bookAuthorEntity;
        List<BookAuthorEntity> results = queryFactory.selectFrom(qEntity)
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

    public BookAuthorEntity findById(UUID id) {
        BookAuthorEntity bookAuthorEntity = bookAuthorPostgresRepository.findById(id).orElse(null);
        return bookAuthorEntity;
    }


    @Transactional
    public BookAuthorEntity update(BookAuthorEntity obj) {
        BookAuthorEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        bookAuthorPostgresRepository.deleteById(id);
    }

}

