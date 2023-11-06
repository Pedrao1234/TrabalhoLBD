package dev.lbd.biblioteca.modulos.bookAuthor.repository;

import com.querydsl.core.types.dsl.*;
import dev.lbd.biblioteca.modulos.bookAuthor.QBookAuthorEntity;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorParamsDto;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorPredicates {

    public BooleanExpression buildPredicate(BookAuthorParamsDto filters) {
        QBookAuthorEntity qEntity = QBookAuthorEntity.bookAuthorEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.authorId() != null){
            predicate = predicate.and(qEntity.author.id.in(filters.authorId()));
        }
        if (filters.bookId() != null){
            predicate = predicate.and(qEntity.book.id.in(filters.bookId()));
        }
        return predicate;
    }
}
