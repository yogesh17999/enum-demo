package com.example.enumdemo.service;

import com.example.enumdemo.dto.FieldsMasterDTO;
import com.example.enumdemo.dto.ResponseDto;
import com.example.enumdemo.entiry.Field;
import com.example.enumdemo.filters.FieldsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FieldsService {

   Field getFieldById(String id);

   List<Field> getAllFields();

   Field createField(Field field);

   Page<FieldsMasterDTO>getFieldsByFilter(Optional<FieldsFilter> fieldsFilter, Pageable pageable);

   ResponseDto getFieldBySorting(int offset, int pageSize);

   ResponseDto getFieldsPagingAndSorting(int offset, int pageSize, String field);

   ResponseDto getListByFilterSortingAndPaging(Optional<FieldsFilter> fieldsFilter, Pageable pageable);

   Long getCount(Optional<FieldsFilter> fieldsFilter);
}
