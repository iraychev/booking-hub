package com.iraychev.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public interface Controller<T> {

    @GetMapping
    ResponseEntity<List<T>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<?> create(@RequestBody T entity);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable UUID id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
