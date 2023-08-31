package com.example.enumdemo.controller;

import com.example.enumdemo.dto.FieldsMasterDTO;
import com.example.enumdemo.dto.ResponseDto;
import com.example.enumdemo.filters.FieldsFilter;
import com.example.enumdemo.mapper.FieldMapper;
import com.example.enumdemo.service.FieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api")
public class FieldsController {

    @Autowired
    private FieldsService fieldsService;

    @Autowired
    private FieldMapper mapper;


    @GetMapping("/{id}")
    public ResponseEntity<FieldsMasterDTO> getFieldsById(@PathVariable String id)
    {
        return new ResponseEntity<>(mapper.toDto(fieldsService.getFieldById(id)), HttpStatus.OK);
    }

    @PostMapping("/create/field")
    public ResponseEntity<FieldsMasterDTO> createField(@RequestBody FieldsMasterDTO fieldDTO)
    {
        return new ResponseEntity<>( mapper.toDto(fieldsService.createField(mapper.toEntity(fieldDTO))),HttpStatus.OK) ;
    }

    @GetMapping
    public ResponseEntity<List<FieldsMasterDTO>> getAll()
    {
        return new ResponseEntity<>(mapper.toListDto(fieldsService.getAllFields()),HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<FieldsMasterDTO>>listAll(Optional<FieldsFilter> fieldsFilter, @PageableDefault(value = 10) Pageable pageable)
    {
        return new ResponseEntity<>(fieldsService.getFieldsByFilter(fieldsFilter,pageable),HttpStatus.OK);
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<ResponseDto> getFieldsMasterList(@PathVariable int offset, @PathVariable int pageSize)
    {
        return new ResponseEntity<>(fieldsService.getFieldBySorting(offset,pageSize),HttpStatus.OK);
    }

    @GetMapping("/get/{offset}/{pageSize}")
    public ResponseEntity<ResponseDto> getFieldsByPagingAndSorting(String fieldName, @PathVariable int offset, @PathVariable int pageSize)
    {
        return new ResponseEntity<>(fieldsService.getFieldsPagingAndSorting(offset, pageSize, fieldName),HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseDto> getData(Optional<FieldsFilter> fieldsFilter, Pageable pageable)
    {
        return new ResponseEntity<>(fieldsService.getListByFilterSortingAndPaging(fieldsFilter,pageable),HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getFieldsCount(Optional<FieldsFilter> fieldsFilter)
    {
        return new ResponseEntity<>(fieldsService.getCount(fieldsFilter),HttpStatus.OK);
    }
}
