package dev.lbd.biblioteca.modulos.author;


import dev.lbd.biblioteca.modulos.author.dto.request.AuthorCreateDto;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorParamsDto;
import dev.lbd.biblioteca.modulos.author.dto.request.AuthorUpdateDto;
import dev.lbd.biblioteca.modulos.author.dto.response.AuthorResponseDto;
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

import static dev.lbd.biblioteca.modulos.author.AuthorController.ROUTE_AUTHOR;

@RestController
@RequestMapping(ROUTE_AUTHOR)
public class AuthorController {

    @Autowired
    private AuthorService service;
    public static final String ROUTE_AUTHOR = "/v1/author";

    public AuthorResponseDto mapEntityToDto(AuthorEntity entity) {
        AuthorResponseDto dto = new AuthorResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getBirthDate(),
                entity.getSex(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Authors", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<AuthorResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid AuthorParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<AuthorEntity> entities = service.findAll(paging, request);
        List<AuthorResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Author", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<AuthorResponseDto> findOne(@PathVariable UUID id) {
        AuthorEntity entity = service.findById(id);
        AuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<AuthorResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates an Author", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating author"),
    })
    public ResponseEntity<AuthorResponseDto> create(@RequestBody @Valid AuthorCreateDto request) {
        AuthorEntity entity = service.create(request);
        AuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Author", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Author no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating author"),
    })
    public ResponseEntity<AuthorResponseDto> update(@PathVariable UUID id, @Valid @RequestBody AuthorUpdateDto request) {
        AuthorEntity entity = service.update(id, request);
        AuthorResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an Author", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding author"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
