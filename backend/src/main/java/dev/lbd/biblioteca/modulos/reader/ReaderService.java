package dev.lbd.biblioteca.modulos.reader;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.lbd.biblioteca.exceptions.ObjectNotFoundException;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderCreateDto;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderParamsDto;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderUpdateDto;
import dev.lbd.biblioteca.modulos.reader.repository.ReaderPredicates;
import dev.lbd.biblioteca.modulos.reader.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private ReaderPredicates readerPredicates;

    public ReaderEntity create(ReaderCreateDto dataRequest) {
        ReaderEntity entity = new ReaderEntity();
        entity.setName(dataRequest.name());
        entity.setCpf(dataRequest.cpf());
        entity.setDateBirth(dataRequest.dateBirth());
        ReaderEntity savedObj = readerRepository.save(entity);
        return savedObj;
    }

    public Page<ReaderEntity> findAll(Pageable pageable, ReaderParamsDto filters) {
        BooleanExpression predicate = readerPredicates.buildPredicate(filters);
        Page<ReaderEntity> objects = readerRepository.findAll(pageable, predicate);
        return objects;
    }

    public ReaderEntity findById(UUID id) {
        ReaderEntity obj = readerRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public ReaderEntity update(UUID id, ReaderUpdateDto request) {
        ReaderEntity obj = readerRepository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.dateBirth() != null) {
            obj.setDateBirth(request.dateBirth());
        }
        if (request.cpf() != null) {
            obj.setCpf(request.cpf());
        }
        ReaderEntity updatedObj = readerRepository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) {
        readerRepository.delete(id);
    }

}
