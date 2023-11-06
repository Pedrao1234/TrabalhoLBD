package dev.lbd.biblioteca.modulos.author.repository;

import com.querydsl.core.types.dsl.*;
import dev.lbd.biblioteca.modulos.author.QAuthorEntity;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorParamsDto;
import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthorPredicates {

    public BooleanExpression buildPredicate(AuthorParamsDto filters) {
        QAuthorEntity qEntity = QAuthorEntity.authorEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.name() != null) {
            StringPath filterPath = qEntity.name;
            predicate = predicate.and(filterPath.like(filters.name() + "%"));
        }
        if (filters.birthDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.birthDate;
            predicate = predicate.and(filterPath.eq(filters.birthDate()));
        }
        if (filters.sex() != null) {
            EnumPath<SexAuthor> filterPath = qEntity.sex;
            predicate = predicate.and(filterPath.eq(filters.sex()));
        }
        return predicate;
    }
}
