package dev.lbd.biblioteca.modulos.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
import dev.lbd.biblioteca.modulos.rent.RentEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="book")
@Entity(name="Book")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE book SET deleted_date = CURRENT_DATE where id=? and version=?") // check if it is necessary
public class BookEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "realease_date")
    private LocalDateTime releaseDate;

    @Column(name = "publisher", nullable = false)
    String publisher;

    @Column(name = "summary", nullable = false)
    String summary;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @ManyToOne
    @JsonBackReference
    private RentEntity rent;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

}
