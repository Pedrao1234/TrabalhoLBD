package dev.lbd.biblioteca.modulos.rent;

import dev.lbd.biblioteca.modulos.book.BookEntity;
import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="rent")
@Entity(name="Rent")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE rent SET deleted_date = CURRENT_DATE where id=? and version=?") // check if it is necessary
public class RentEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

//    @Column(name = "reader_id", nullable = false)
//    private ReaderEntity reader;
//
//    @Column(name = "book_id", nullable = false)
//    private BookEntity book;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column(name = "devolution_date")
    private LocalDateTime devolutionDate;

    @Column(name = "status", nullable = false)
    @Enumerated
    private StatusEnum status;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

}
