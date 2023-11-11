package dev.lbd.biblioteca.modulos.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookEntity is a Querydsl query type for BookEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookEntity extends EntityPathBase<BookEntity> {

    private static final long serialVersionUID = 785424977L;

    public static final QBookEntity bookEntity = new QBookEntity("bookEntity");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedDate = createDateTime("deletedDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final StringPath publisher = createString("publisher");

    public final DateTimePath<java.time.LocalDateTime> releaseDate = createDateTime("releaseDate", java.time.LocalDateTime.class);

    public final StringPath summary = createString("summary");

    public final StringPath title = createString("title");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QBookEntity(String variable) {
        super(BookEntity.class, forVariable(variable));
    }

    public QBookEntity(Path<? extends BookEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookEntity(PathMetadata metadata) {
        super(BookEntity.class, metadata);
    }

}

