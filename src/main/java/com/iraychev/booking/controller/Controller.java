package com.iraychev.booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public interface Controller<T> {

    @GetMapping
    public abstract ResponseEntity<List<T>> getAll();

    @GetMapping("/{id}")
    public abstract ResponseEntity<T> getById(@PathVariable UUID id);

    @PostMapping
    public abstract ResponseEntity<T> create(@RequestBody T entity);

    @PutMapping("/{id}")
    public abstract ResponseEntity<T> update(@PathVariable UUID id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<Void> delete(@PathVariable UUID id);
}
