package dev.lbd.biblioteca.modulos.reader;


import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderCreateDto;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderParamsDto;
import dev.lbd.biblioteca.modulos.reader.dto.request.ReaderUpdateDto;
import dev.lbd.biblioteca.modulos.reader.dto.response.ReaderResponseDto;
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

import static dev.lbd.biblioteca.modulos.reader.ReaderController.ROUTE_READER;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ROUTE_READER)
public class ReaderController {

    @Autowired
    private ReaderService service;
    public static final String ROUTE_READER = "/v1/reader";

    public ReaderResponseDto mapEntityToDto(ReaderEntity entity) {
        ReaderResponseDto dto = new ReaderResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDateBirth(),
                entity.getCpf(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Readers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<ReaderResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid ReaderParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<ReaderEntity> entities = service.findAll(paging, request);
        List<ReaderResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Reader", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<ReaderResponseDto> findOne(@PathVariable UUID id) {
        ReaderEntity entity = service.findById(id);
        ReaderResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<ReaderResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a Reader", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reader succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Reader not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating reader"),
    })
    public ResponseEntity<ReaderResponseDto> create(@RequestBody @Valid ReaderCreateDto request) {
        ReaderEntity entity = service.create(request);
        ReaderResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Reader", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Reade no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<ReaderResponseDto> update(@PathVariable UUID id, @Valid @RequestBody ReaderUpdateDto request) {
        ReaderEntity entity = service.update(id, request);
        ReaderResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a Reader", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Reader not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding reader"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
