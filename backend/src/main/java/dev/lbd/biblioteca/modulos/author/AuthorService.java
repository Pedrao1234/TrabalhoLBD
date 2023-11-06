package dev.lbd.biblioteca.modulos.author;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.lbd.biblioteca.exceptions.ObjectNotFoundException;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorCreateDto;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorParamsDto;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorUpdateDto;
import dev.lbd.biblioteca.modulos.author.enums.SexAuthor;
import dev.lbd.biblioteca.modulos.author.repository.AuthorPredicates;
import dev.lbd.biblioteca.modulos.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorPredicates authorPredicates;

    public AuthorEntity create(AuthorCreateDto dataRequest) {
        AuthorEntity entity = new AuthorEntity();
        entity.setName(dataRequest.name());
        entity.setBirthDate(dataRequest.birthDate());
        entity.setCpf(dataRequest.cpf());
        entity.setSex(SexAuthor.MAN);
        AuthorEntity savedObj = authorRepository.save(entity);
        return savedObj;
    }

    public Page<AuthorEntity> findAll(Pageable pageable, AuthorParamsDto filters) {
        BooleanExpression predicate = authorPredicates.buildPredicate(filters);
        Page<AuthorEntity> objects = authorRepository.findAll(pageable, predicate);
        return objects;
    }

    public AuthorEntity findById(UUID id) {
        AuthorEntity obj = authorRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public AuthorEntity update(UUID id, AuthorUpdateDto request) {
        AuthorEntity obj = authorRepository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.birthDate() != null) {
            obj.setBirthDate(request.birthDate());
        }
        if (request.cpf() != null) {
            obj.setCpf(request.cpf());
        }
        if (request.sex() != null) {
            obj.setSex(request.sex());
        }
        AuthorEntity updatedObj = authorRepository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) {
        authorRepository.delete(id);
    }

}
