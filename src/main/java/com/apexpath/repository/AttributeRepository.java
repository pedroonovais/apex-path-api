package com.apexpath.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apexpath.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, UUID>{
}
