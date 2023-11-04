package dev.lbd.biblioteca.modulos.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.lbd.biblioteca.modulos.book.QBookEntity;
import dev.lbd.biblioteca.modulos.book.BookEntity;
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
public class BookRepository {
    private final BookPostgresRepository bookPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public BookEntity save(BookEntity obj) {
        BookEntity bookEntity = bookPostgresRepository.save(obj);
        return bookEntity;
    }

    public Page<BookEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QBookEntity qEntity = QBookEntity.bookEntity;
        List<BookEntity> results = queryFactory.selectFrom(qEntity)
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

    public BookEntity findById(UUID id) {
        BookEntity bookEntity = bookPostgresRepository.findById(id).orElse(null);
        return bookEntity;
    }


    @Transactional
    public BookEntity update(BookEntity obj) {
        BookEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        bookPostgresRepository.deleteById(id);
    }

}

