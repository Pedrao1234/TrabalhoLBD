package dev.lbd.biblioteca.modulos.reader.repository;

//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.lbd.biblioteca.modulos.reader.QReaderEntity;
import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
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
public class ReaderRepository {
    private final ReaderPostgresRepository readerPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public ReaderEntity save(ReaderEntity obj) {
        ReaderEntity readerEntity = readerPostgresRepository.save(obj);
        return readerEntity;
    }

    public Page<ReaderEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QReaderEntity qEntity = QReaderEntity.readerEntity;
        List<ReaderEntity> results = queryFactory.selectFrom(qEntity)
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

    public ReaderEntity findById(UUID id) {
        ReaderEntity readerEntity = readerPostgresRepository.findById(id).orElse(null);
        return readerEntity;
    }


    @Transactional
    public ReaderEntity update(ReaderEntity obj) {
        ReaderEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        readerPostgresRepository.deleteById(id);
    }

}

