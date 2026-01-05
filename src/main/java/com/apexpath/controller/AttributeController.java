package com.apexpath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apexpath.model.Attribute;
import com.apexpath.repository.AttributeRepository;

@RestController
@RequestMapping("/attributes")
public class AttributeController {
    
    @Autowired
    private AttributeRepository attributeRepository;

    @GetMapping
    public ResponseEntity<List<Attribute>> findAll() {
        return ResponseEntity.ok(attributeRepository.findAll());
    } 

}
