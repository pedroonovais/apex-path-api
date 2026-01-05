package com.apexpath.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.apexpath.model.Attribute;
import com.apexpath.repository.AttributeRepository;

import jakarta.annotation.PostConstruct;

@Configuration
@DependsOn("flyway")
public class DatabaseSeeder {
    
    @Autowired
    private AttributeRepository attributeRepository;

    @PostConstruct
    public void init() {

        String[] attributeNameList = {"Força","Resistência","Inteligência","Disciplina","Fé","Carisma" };
        List<Attribute> attributeList = new ArrayList<>();

        for (var name : attributeNameList) {
            attributeList.add(Attribute.builder().name(name).build());
        }

        attributeRepository.saveAll(attributeList);
        
    }
}
