package dev.lbd.biblioteca.modulos.rent.repository;

import com.querydsl.core.types.dsl.*;
import dev.lbd.biblioteca.modulos.rent.QRentEntity;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentParamsDto;
import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RentPredicates {

    public BooleanExpression buildPredicate(RentParamsDto filters) {
        QRentEntity qEntity = QRentEntity.rentEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.readerId() != null) {
            predicate = predicate.and(qEntity.reader.id.in(filters.readerId()));
        }
        if (filters.rentDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.rentDate;
            predicate = predicate.and(filterPath.eq(filters.rentDate()));
        }
        if (filters.devolutionDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.devolutionDate;
            predicate = predicate.and(filterPath.eq(filters.devolutionDate()));
        }
        if (filters.status() != null) {
            EnumPath<StatusEnum> filterPath = qEntity.status;
            predicate = predicate.and(filterPath.eq(filters.status()));
        }

        return predicate;
    }
}
