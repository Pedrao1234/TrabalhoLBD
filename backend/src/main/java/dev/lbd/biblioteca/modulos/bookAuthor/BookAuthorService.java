package dev.lbd.biblioteca.modulos.bookAuthor;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.lbd.biblioteca.exceptions.ObjectNotFoundException;
import dev.lbd.biblioteca.modulos.author.AuthorEntity;
import dev.lbd.biblioteca.modulos.author.repository.AuthorRepository;
import dev.lbd.biblioteca.modulos.book.BookEntity;
import dev.lbd.biblioteca.modulos.book.repository.BookRepository;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorCreateDto;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorParamsDto;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorUpdateDto;
import dev.lbd.biblioteca.modulos.bookAuthor.repository.BookAuthorPredicates;
import dev.lbd.biblioteca.modulos.bookAuthor.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookAuthorService {
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookAuthorPredicates bookAuthorPredicates;

    public BookAuthorEntity create(BookAuthorCreateDto dataRequest) {
        BookAuthorEntity entity = new BookAuthorEntity();
        BookEntity book = bookRepository.findById(dataRequest.bookId());
        if (book == null){
            throw new ObjectNotFoundException("Book not found");
        }
        AuthorEntity author = authorRepository.findById(dataRequest.authorId());
        if (author == null){
            throw new ObjectNotFoundException("Author not found");
        }
        entity.setBook(book);
        entity.setAuthor(author);
        BookAuthorEntity savedObj = bookAuthorRepository.save(entity);
        return savedObj;
    }

    public Page<BookAuthorEntity> findAll(Pageable pageable, BookAuthorParamsDto filters) {
        BooleanExpression predicate = bookAuthorPredicates.buildPredicate(filters);
        Page<BookAuthorEntity> objects = bookAuthorRepository.findAll(pageable, predicate);
        return objects;
    }

    public BookAuthorEntity findById(UUID id) {
        BookAuthorEntity obj = bookAuthorRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public BookAuthorEntity update(UUID id, BookAuthorUpdateDto request) {
        BookAuthorEntity obj = bookAuthorRepository.findById(id);
        BookEntity book = bookRepository.findById(request.bookId());

        if (book != null){
            obj.setBook(book);
        }
        AuthorEntity author = authorRepository.findById(request.authorId());
        if (author != null){
            obj.setAuthor(author);
        }

        BookAuthorEntity updatedObj = bookAuthorRepository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) {
        bookAuthorRepository.delete(id);
    }

}
