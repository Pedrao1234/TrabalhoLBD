package dev.lbd.biblioteca.modulos.reader;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReaderEntity is a Querydsl query type for ReaderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReaderEntity extends EntityPathBase<ReaderEntity> {

    private static final long serialVersionUID = -311149615L;

    public static final QReaderEntity readerEntity = new QReaderEntity("readerEntity");

    public final StringPath cpf = createString("cpf");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dateBirth = createDateTime("dateBirth", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedDate = createDateTime("deletedDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QReaderEntity(String variable) {
        super(ReaderEntity.class, forVariable(variable));
    }

    public QReaderEntity(Path<? extends ReaderEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReaderEntity(PathMetadata metadata) {
        super(ReaderEntity.class, metadata);
    }

}

