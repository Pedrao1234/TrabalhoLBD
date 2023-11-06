package dev.lbd.biblioteca.modulos.book;


import dev.lbd.biblioteca.modulos.book.dto.request.BookCreateDto;
import dev.lbd.biblioteca.modulos.book.dto.request.BookParamsDto;
import dev.lbd.biblioteca.modulos.book.dto.request.BookUpdateDto;
import dev.lbd.biblioteca.modulos.book.dto.response.BookResponseDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.UUID;

import static dev.lbd.biblioteca.modulos.book.BookController.ROUTE_BOOK;

@RestController
@RequestMapping(ROUTE_BOOK)
public class BookController {

    @Autowired
    private BookService service;
    public static final String ROUTE_BOOK = "/v1/book";

    public BookResponseDto mapEntityToDto(BookEntity entity) {
        BookResponseDto dto = new BookResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getReleaseDate(),
                entity.getPublisher(),
                entity.getSummary(),
                entity.getStatus(),
                entity.getRent(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Books", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<BookResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid BookParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<BookEntity> entities = service.findAll(paging, request);
        List<BookResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Book", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<BookResponseDto> findOne(@PathVariable UUID id) {
        BookEntity entity = service.findById(id);
        BookResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<BookResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a Book", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating book"),
    })
    public ResponseEntity<BookResponseDto> create(@RequestBody @Valid BookCreateDto request) {
        BookEntity entity = service.create(request);
        BookResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Book", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Book no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating book"),
    })
    public ResponseEntity<BookResponseDto> update(@PathVariable UUID id, @Valid @RequestBody BookUpdateDto request) {
        BookEntity entity = service.update(id, request);
        BookResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a Book", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding book"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
