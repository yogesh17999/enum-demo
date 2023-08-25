package com.example.enumdemo.filters;

import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Data
public class FieldsFilter {

    private String fieldName;

    private String fieldType;

    private String dataType;

    public Optional<String> getFieldName(){return Optional.ofNullable(fieldName);}

    public Optional<String> getFieldType(){return Optional.ofNullable(fieldType);}

    public Optional<String> getDataType(){return Optional.ofNullable(dataType);}




}
