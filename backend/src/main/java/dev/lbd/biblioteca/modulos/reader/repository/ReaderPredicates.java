package dev.lbd.biblioteca.modulos.reader.repository;

import com.querydsl.core.types.dsl.*;
import dev.lbd.biblioteca.modulos.reader.QReaderEntity;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderParamsDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReaderPredicates {

    public BooleanExpression buildPredicate(ReaderParamsDto filters) {
        QReaderEntity qEntity = QReaderEntity.readerEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.name() != null) {
            StringPath filterPath = qEntity.name;
            predicate = predicate.and(filterPath.like(filters.name() + "%"));
        }
        if (filters.cpf() != null) {
            StringPath filterPath = qEntity.cpf;
            predicate = predicate.and(filterPath.eq(filters.cpf()));
        }
        if (filters.dateBirth() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.dateBirth;
            predicate = predicate.and(filterPath.eq(filters.dateBirth()));
        }
        return predicate;
    }
}
