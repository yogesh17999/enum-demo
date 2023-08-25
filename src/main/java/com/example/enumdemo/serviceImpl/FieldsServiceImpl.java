package com.example.enumdemo.serviceImpl;

import com.example.enumdemo.dto.FieldsMasterDTO;
import com.example.enumdemo.dto.ResponseDto;
import com.example.enumdemo.entiry.Field;
import com.example.enumdemo.filters.FieldsFilter;
import com.example.enumdemo.model.Field_;
import com.example.enumdemo.repository.FieldRepository;
import com.example.enumdemo.service.FieldsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FieldsServiceImpl implements FieldsService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Field getFieldById(String id) {
        return fieldRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found by given Id" + id));
    }

    @Override
    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    @Override
    public Field createField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public Page<FieldsMasterDTO> getFieldsByFilter(Optional<FieldsFilter> fieldsFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Field> criteriaQuery = criteriaBuilder.createQuery(Field.class);
        Root<Field> fieldRoot = criteriaQuery.from(Field.class);

        List<Predicate> predicates = new ArrayList<>();

        fieldsFilter.ifPresent(f -> {
            f.getFieldName().ifPresent(fieldName -> predicates.add(criteriaBuilder.equal(fieldRoot.get(Field_.FIELDNAME), fieldName)));
            f.getFieldType().ifPresent(fieldType -> predicates.add(criteriaBuilder.equal(fieldRoot.get(Field_.FIELDTYPE), fieldType)));
            f.getDataType().ifPresent(dataType -> predicates.add(criteriaBuilder.equal(fieldRoot.get(Field_.DATATYPE), dataType)));
        });

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Field> fieldTypedQuery = entityManager.createQuery(criteriaQuery);
        fieldTypedQuery.setFirstResult(pageable.getOffset() > 0 ? (int) pageable.getOffset() : 0);
        fieldTypedQuery.setMaxResults(pageable.getPageSize());
        List<FieldsMasterDTO> fieldsMasterDTOS = new ArrayList<>();
        fieldTypedQuery.getResultList().forEach(
                field -> {
                    FieldsMasterDTO fieldsMasterDTO = new FieldsMasterDTO();
                    fieldsMasterDTO.setFieldId(field.getId());
                    fieldsMasterDTO.setFieldType(field.getFieldType());
                    fieldsMasterDTO.setFieldName(field.getFieldName());
                    fieldsMasterDTO.setDataType(field.getDataType());
                    fieldsMasterDTOS.add(fieldsMasterDTO);
                }
        );
        return new PageImpl<>(fieldsMasterDTOS);
    }

    @Override
    public ResponseDto getFieldBySorting(int offset, int pageSize) {
        Page<Field> fields = fieldRepository.findAll(PageRequest.of(offset, pageSize));
        return ResponseDto.builder().totalCount(fields.getSize()).response(fields).build();
    }

    @Override
    public ResponseDto getFieldsPagingAndSorting(int offset, int pageSize, String field) {
        Page<Field> fields = fieldRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(Sort.Direction.ASC, field)));
        return ResponseDto.builder().totalCount(fields.getSize()).response(fields).build();
    }

    @Override
    public ResponseDto getListByFilterSortingAndPaging(Optional<FieldsFilter> fieldsFilter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Field> cq = cb.createQuery(Field.class);

        //select * from Field
        Root<Field> root = cq.from(Field.class);

        List<Predicate> predicate = new ArrayList<>();

        fieldsFilter.ifPresent(
                filter -> {
                    filter.getFieldName().ifPresent(fieldName -> predicate.add(cb.equal(root.get(Field_.FIELDNAME), fieldName)));
                    filter.getFieldType().ifPresent(fieldType -> predicate.add(cb.equal(root.get(Field_.FIELDTYPE), fieldType)));
                    filter.getDataType().ifPresent(dataType -> predicate.add(cb.equal(root.get(Field_.DATATYPE), dataType)));
                }
        );
        // where fieldName == fieldsFilter.fieldName and fieldType == FieldsFilter.fieldType and dataType == Fields.dataType
        cq.where(cb.and(predicate.toArray(new Predicate[predicate.size()])));
        log.info("sorted: "+pageable.getSort().isSorted());

        // order by pageable.getSort.fieldName DESC
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(
                    s -> {
                        if (s.getDirection().equals("ASC"))
                            cq.orderBy(cb.asc(root.get(s.getProperty())));
                        else
                            cq.orderBy(cb.desc(root.get(s.getProperty())));
                    });
        } else
            cq.orderBy(cb.asc(root.get(Field_.FIELDNAME)));

        // select * from Field where fieldName == fieldsFilter.fieldName and fieldType == FieldsFilter.fieldType and dataType == Fields.dataType order by pageable.getSort.fieldName DESC;
        TypedQuery<Field> typedQuery = entityManager.createQuery(cq);
        typedQuery.setFirstResult(pageable.getPageNumber() > 0 ? (int) pageable.getPageNumber() : 0);
        typedQuery.setMaxResults(pageable.getPageSize());

        List<FieldsMasterDTO> fieldsMasterDTOS = new ArrayList<>();
        typedQuery.getResultList().forEach(
                result -> {
                    FieldsMasterDTO fieldsMasterDTO = new FieldsMasterDTO();
                    fieldsMasterDTO.setFieldName(result.getFieldName());
                    fieldsMasterDTO.setFieldType(result.getFieldType());
                    fieldsMasterDTO.setDataType(result.getDataType());
                    fieldsMasterDTO.setFieldId(result.getId());
                    fieldsMasterDTOS.add(fieldsMasterDTO);
                }
        );
        return ResponseDto.builder().response(fieldsMasterDTOS).totalCount(fieldsMasterDTOS.size()).build();
    }




}
