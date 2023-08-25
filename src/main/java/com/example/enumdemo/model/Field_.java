package com.example.enumdemo.model;

import com.example.enumdemo.entiry.Field;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Field.class)
public class Field_ {

    public static volatile SingularAttribute<Field, String> id;
    public static volatile SingularAttribute<Field, String> fieldName;
    public static volatile SingularAttribute<Field, String> fieldType;
    public static volatile SingularAttribute<Field, String> dataType;

    public static final String ID = "id";
    public static final String FIELDTYPE = "fieldType";
    public static final String FIELDNAME = "fieldName";
    public static final String DATATYPE = "dataType";
}
