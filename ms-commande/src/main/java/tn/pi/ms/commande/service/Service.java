package tn.pi.ms.commande.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T entity);
    T delete(Long id);
}
