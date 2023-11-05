package dev.lbd.biblioteca.modulos.rent;


import dev.lbd.biblioteca.modulos.rent.dto.request.RentCreateDto;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentParamsDto;
import dev.lbd.biblioteca.modulos.rent.dto.request.RentUpdateDto;
import dev.lbd.biblioteca.modulos.rent.dto.response.RentResponseDto;
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

import static dev.lbd.biblioteca.modulos.rent.RentController.ROUTE_READER;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ROUTE_READER)
public class RentController {

    @Autowired
    private RentService service;
    public static final String ROUTE_READER = "/v1/rent";

    public RentResponseDto mapEntityToDto(RentEntity entity) {
        RentResponseDto dto = new RentResponseDto(
                entity.getId(),
                entity.getReader(),
                entity.getBook(),
                entity.getRentDate(),
                entity.getDevolutionDate(),
                entity.getStatus(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Rents", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<RentResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid RentParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<RentEntity> entities = service.findAll(paging, request);
        List<RentResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Rent", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<RentResponseDto> findOne(@PathVariable UUID id) {
        RentEntity entity = service.findById(id);
        RentResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<RentResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a Rent", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rent succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating rent"),
    })
    public ResponseEntity<RentResponseDto> create(@RequestBody @Valid RentCreateDto request) {
        RentEntity entity = service.create(request);
        RentResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Rent", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Rent no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating rent"),
    })
    public ResponseEntity<RentResponseDto> update(@PathVariable UUID id, @Valid @RequestBody RentUpdateDto request) {
        RentEntity entity = service.update(id, request);
        RentResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a Rent", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding rent"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
