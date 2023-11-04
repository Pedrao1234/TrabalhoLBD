package dev.lbd.biblioteca.modulos.book.repository;

import com.querydsl.core.types.dsl.*;
import dev.lbd.biblioteca.modulos.book.QBookEntity;
import dev.lbd.biblioteca.modulos.book.dto.request.BookParamsDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookPredicates {

    public BooleanExpression buildPredicate(BookParamsDto filters) {
        QBookEntity qEntity = QBookEntity.bookEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.title() != null) {
            StringPath filterPath = qEntity.title;
            predicate = predicate.and(filterPath.eq(filters.title()));
        }
        if (filters.publisher() != null) {
            StringPath filterPath = qEntity.publisher;
            predicate = predicate.and(filterPath.eq(filters.publisher()));
        }
        if (filters.releaseDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.releaseDate;
            predicate = predicate.and(filterPath.eq(filters.releaseDate()));
        }
        if (filters.summary() != null) {
            StringPath filterPath = qEntity.summary;
            predicate = predicate.and(filterPath.eq(filters.summary()));
        }
        return predicate;
    }
}
