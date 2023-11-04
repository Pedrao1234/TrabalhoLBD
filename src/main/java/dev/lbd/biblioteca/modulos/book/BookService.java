package dev.lbd.biblioteca.modulos.book;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.lbd.biblioteca.exceptions.ObjectNotFoundException;
import dev.lbd.biblioteca.modulos.book.dto.request.BookCreateDto;
import dev.lbd.biblioteca.modulos.book.dto.request.BookParamsDto;
import dev.lbd.biblioteca.modulos.book.dto.request.BookUpdateDto;
import dev.lbd.biblioteca.modulos.book.repository.BookPredicates;
import dev.lbd.biblioteca.modulos.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository readerRepository;
    @Autowired
    private BookPredicates readerPredicates;

    public BookEntity create(BookCreateDto dataRequest) {
        BookEntity entity = new BookEntity();
        entity.setTitle(dataRequest.title());
        entity.setReleaseDate(dataRequest.releaseDate());
        entity.setPublisher(dataRequest.publisher());
        entity.setSummary(dataRequest.summary());
        BookEntity savedObj = readerRepository.save(entity);
        return savedObj;
    }

    public Page<BookEntity> findAll(Pageable pageable, BookParamsDto filters) {
        BooleanExpression predicate = readerPredicates.buildPredicate(filters);
        Page<BookEntity> objects = readerRepository.findAll(pageable, predicate);
        return objects;
    }

    public BookEntity findById(UUID id) {
        BookEntity obj = readerRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public BookEntity update(UUID id, BookUpdateDto request) {
        BookEntity obj = readerRepository.findById(id);
        if (request.title() != null) {
            obj.setTitle(request.title());
        }
        if (request.releaseDate() != null) {
            obj.setReleaseDate(request.releaseDate());
        }
        if (request.publisher() != null) {
            obj.setPublisher(request.publisher());
        }
        if (request.summary() != null) {
            obj.setSummary(request.summary());
        }
        BookEntity updatedObj = readerRepository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) {
        readerRepository.delete(id);
    }

}
