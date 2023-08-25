package com.example.enumdemo.repository;

import com.example.enumdemo.entiry.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field,String> {
}
