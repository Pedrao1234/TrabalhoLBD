package dev.lbd.biblioteca.modulos.reader;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.lbd.biblioteca.modulos.rent.RentEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="reader")
@Entity(name="Reader")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE reader SET deleted_date = CURRENT_DATE where id=? and version=?") // check if it is necessary
public class ReaderEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "date_birth")
    private LocalDateTime dateBirth;

    @Column(nullable = false, unique = true)
    @CPF
    String cpf;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @OneToOne(mappedBy = "reader")
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
