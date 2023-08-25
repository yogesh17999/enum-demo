package com.example.enumdemo.mapper;

import com.example.enumdemo.dto.FieldsMasterDTO;
import com.example.enumdemo.entiry.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FieldMapper {
    @Mapping(source = "id",target = "fieldId")
    FieldsMasterDTO toDto(Field field);

    @Mapping(target = "id",ignore = true)
    Field toEntity(FieldsMasterDTO fieldsMasterDTO);

    List<FieldsMasterDTO> toListDto(List<Field> fields);

}
