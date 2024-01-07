package tn.pi.ms.stock.service;

import tn.pi.ms.stock.entity.Produit;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> findAll();
    Optional<T> findById(Long id);


    Optional<Produit> findByRef(String ref);

    T save(T entity);
    T delete(Long id);
}
