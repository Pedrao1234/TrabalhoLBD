package dev.lbd.biblioteca.modulos.bookAuthor;


import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorCreateDto;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorParamsDto;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.request.BookAuthorUpdateDto;
import dev.lbd.biblioteca.modulos.bookAuthor.dto.response.BookAuthorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static dev.lbd.biblioteca.modulos.bookAuthor.BookAuthorController.ROUTE_BOOKAUTHOR;

@RestController
@RequestMapping(ROUTE_BOOKAUTHOR)
public class BookAuthorController {

    @Autowired
    private BookAuthorService service;
    public static final String ROUTE_BOOKAUTHOR = "/v1/bookAuthor";

    public BookAuthorResponseDto mapEntityToDto(BookAuthorEntity entity) {
        BookAuthorResponseDto dto = new BookAuthorResponseDto(
                entity.getId(),
                entity.getBook(),
                entity.getAuthor(),
                entity.getStatus(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets BookAuthors", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<BookAuthorResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid BookAuthorParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<BookAuthorEntity> entities = service.findAll(paging, request);
        List<BookAuthorResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one BookAuthor", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<BookAuthorResponseDto> findOne(@PathVariable UUID id) {
        BookAuthorEntity entity = service.findById(id);
        BookAuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<BookAuthorResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a BookAuthor", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "BookAuthor succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "BookAuthor not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating book"),
    })
    public ResponseEntity<BookAuthorResponseDto> create(@RequestBody @Valid BookAuthorCreateDto request) {
        BookAuthorEntity entity = service.create(request);
        BookAuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a BookAuthor", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "BookAuthor no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating book"),
    })
    public ResponseEntity<BookAuthorResponseDto> update(@PathVariable UUID id, @Valid @RequestBody BookAuthorUpdateDto request) {
        BookAuthorEntity entity = service.update(id, request);
        BookAuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a BookAuthor", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "BookAuthor not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding book"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
