package dev.lbd.biblioteca.modulos.author;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;
import dev.lbd.biblioteca.modulos.author.AuthorEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="author")
@Entity(name="Author")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE author SET deleted_date = CURRENT_DATE where id=? and version=?") // check if it is necessary
public class AuthorEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "cpf", nullable = false)
    String cpf;

    @Column(name = "sex", nullable = false)
    @Enumerated
    SexAuthor sex; 

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    @JsonBackReference
    private AuthorEntity author;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

}
