package dev.lbd.biblioteca.modulos.rent;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.lbd.biblioteca.exceptions.ObjectNotFoundException;
import dev.lbd.biblioteca.modulos.book.BookEntity;
import dev.lbd.biblioteca.modulos.book.enums.StatusBook;
import dev.lbd.biblioteca.modulos.book.repository.BookRepository;
import dev.lbd.biblioteca.modulos.reader.ReaderEntity;
import dev.lbd.biblioteca.modulos.reader.repository.ReaderRepository;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentCreateDto;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentParamsDto;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentUpdateDto;
import dev.lbd.biblioteca.modulos.rent.enums.StatusEnum;
import dev.lbd.biblioteca.modulos.rent.repository.RentPredicates;
import dev.lbd.biblioteca.modulos.rent.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private RentPredicates rentPredicates;

    public RentEntity create(RentCreateDto dataRequest) {
        RentEntity entity = new RentEntity();

        List<BookEntity> books = new ArrayList<>();
        for (UUID bookId : dataRequest.bookId()) {
            BookEntity optionalBook = bookRepository.findById(bookId);
            if (optionalBook == null){
                throw new ObjectNotFoundException("Book not found");
            }
            if (optionalBook.getStatus() == StatusBook.UNAVAILABLE){
                throw new RuntimeException("Book not Available");
            }
            optionalBook.setStatus(StatusBook.UNAVAILABLE);
            optionalBook.setRent(entity);
            books.add(optionalBook);
        }
        entity.setBook(books);
        ReaderEntity reader = readerRepository.findById(dataRequest.readerId());
        if (reader == null){
            throw new ObjectNotFoundException("Reader not found");
        }
        if (reader.getRent() != null){
            throw new RuntimeException("Reader has an open Rent process");
        }
        entity.setReader(reader);
        entity.setRentDate(dataRequest.rentDate());
        entity.setDevolutionDate(dataRequest.devolutionDate());
        entity.setStatus(StatusEnum.OCURRING);

        RentEntity savedObj = rentRepository.save(entity);
        return savedObj;
    }

    public Page<RentEntity> findAll(Pageable pageable, RentParamsDto filters) {
        BooleanExpression predicate = rentPredicates.buildPredicate(filters);
        Page<RentEntity> objects = rentRepository.findAll(pageable, predicate);
        return objects;
    }

    public RentEntity findById(UUID id) {
        RentEntity obj = rentRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public RentEntity update(UUID id, RentUpdateDto request) {
        RentEntity obj = rentRepository.findById(id);

        if (request.status() != null) {
            obj.setStatus(request.status());
        }
        if (request.status() == StatusEnum.FINISHED){
            obj.setReader(null);
            for (BookEntity book : obj.getBook()){
                book.setStatus(StatusBook.AVAILABLE);
                bookRepository.update(book);
            }
        }
        RentEntity updatedObj = rentRepository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) {
        RentEntity obj = rentRepository.findById(id);
        if (obj.getStatus() != StatusEnum.FINISHED){
            throw new RuntimeException("Not possible to proceed with deleting because Rent Status is not FINISHED");
        }
        obj.setReader(null);
        for (BookEntity book : obj.getBook()){
            book.setStatus(StatusBook.AVAILABLE);
            book.setRent(null);
            bookRepository.update(book);
        }
        rentRepository.delete(id);
    }

}
