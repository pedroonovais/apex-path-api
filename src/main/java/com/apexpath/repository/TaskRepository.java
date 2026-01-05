package com.apexpath.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apexpath.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
